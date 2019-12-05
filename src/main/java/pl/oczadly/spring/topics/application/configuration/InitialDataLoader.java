package pl.oczadly.spring.topics.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.domain.privilege.entity.Privilege;
import pl.oczadly.spring.topics.domain.privilege.control.PrivilegeRepository;
import pl.oczadly.spring.topics.domain.role.entity.Role;
import pl.oczadly.spring.topics.domain.role.control.RoleRepository;
import pl.oczadly.spring.topics.domain.user.management.entity.User;
import pl.oczadly.spring.topics.domain.user.management.control.UserRepository;

import java.util.Optional;
import java.util.Set;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean isDataAlreadyLoaded = false;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(isDataAlreadyLoaded) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFoundOrReturnExisting("READ");

        Privilege crudCoursePrivilege = createPrivilegeIfNotFoundOrReturnExisting(crudAllPrivileges("COURSES"));
        Privilege crudTopicPrivilege = createPrivilegeIfNotFoundOrReturnExisting(crudAllPrivileges("TOPICS"));
        Privilege crudSeminarsPrivilege = createPrivilegeIfNotFoundOrReturnExisting(crudAllPrivileges("SEMINARS"));
        Privilege crudUsersPrivilege = createPrivilegeIfNotFoundOrReturnExisting(crudAllPrivileges("USERS"));
        Privilege roleMaintainerPrivilege = createPrivilegeIfNotFoundOrReturnExisting("ROLE_MAINTAINER");

        Set<Privilege> userPrivileges = Set.of(readPrivilege);
        Set<Privilege> adminPrivileges = Set.of(readPrivilege, crudCoursePrivilege, crudTopicPrivilege,
                crudSeminarsPrivilege, crudUsersPrivilege, roleMaintainerPrivilege);

        Role adminRole = createUserRoleIfNotFoundOrReturnExisting("ADMIN", adminPrivileges);
        createUserRoleIfNotFoundOrReturnExisting("USER", userPrivileges);

        createUserIfNotFoundOrReturnExisting("admin@admin.com", "admin", Set.of(adminRole));
        isDataAlreadyLoaded = true;
    }

    private Privilege createPrivilegeIfNotFoundOrReturnExisting(String name) {
        Optional<Privilege> privilege = privilegeRepository.findOptionalByName(name);
        return privilege.orElseGet(() -> persistPrivilege(name));
    }

    private Privilege persistPrivilege(String name) {
        Privilege privilegeToSave = new Privilege(name);
        return privilegeRepository.save(privilegeToSave);
    }

    private String crudAllPrivileges(String partOfPrivileges) {
        final String crudAll = "CRUD_ALL";

        return crudAll + "_" + partOfPrivileges;
    }

    private Role createUserRoleIfNotFoundOrReturnExisting(String roleName, Set<Privilege> privileges) {
        Optional<Role> role = roleRepository.findOptionalByName(roleName);
        return role.orElseGet(() -> persistRole(roleName, privileges));
    }

    private Role persistRole(String roleName, Set<Privilege> privileges) {
        Role roleToPersist = new Role(roleName, privileges);
        return roleRepository.save(roleToPersist);
    }

    private User createUserIfNotFoundOrReturnExisting(String email, String password, Set<Role> userRoles) {
        Optional<User> user = userRepository.findOptionalByEmail(email);
        return user.orElseGet(() -> persistUser(email, password, userRoles));
    }

    private User persistUser(String email, String password, Set<Role> userRoles) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(password);

        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setNickname("admin");
        user.setRoles(userRoles);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPrivilegeRepository(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
