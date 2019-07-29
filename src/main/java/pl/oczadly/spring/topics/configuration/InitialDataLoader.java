package pl.oczadly.spring.topics.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.privilege.Privilege;
import pl.oczadly.spring.topics.privilege.PrivilegeRepository;
import pl.oczadly.spring.topics.role.Role;
import pl.oczadly.spring.topics.role.RoleRepository;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean isDataAlreadyLoaded = false;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;

    public InitialDataLoader(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(isDataAlreadyLoaded) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFoundOrReturnExisting("READ");

        Privilege crudCoursePrivelege = createPrivilegeIfNotFoundOrReturnExisting(crudAllPrivileges("COURSES"));
        Privilege crudTopicPrivelege = createPrivilegeIfNotFoundOrReturnExisting(crudAllPrivileges("TOPICS"));
        Privilege crudUsersPrivilege = createPrivilegeIfNotFoundOrReturnExisting(crudAllPrivileges("USERS"));

        Set<Privilege> userPrivileges = Set.of(readPrivilege);
        Set<Privilege> adminPrivileges = Set.of(readPrivilege, crudCoursePrivelege, crudTopicPrivelege, crudUsersPrivilege);

        Role adminRole = createUserRoleIfNotFoundOrReturnExisting("ADMIN", adminPrivileges);
        createUserRoleIfNotFoundOrReturnExisting("USER", userPrivileges);

        User admin = new User();

        admin.setEmail("admin@admin.com");
        admin.setPassword("admin");
        admin.setRoles(Set.of(adminRole));
        admin.setEnabled(true);

        userRepository.save(admin);

        isDataAlreadyLoaded = true;
    }

    private String crudAllPrivileges(String partOfPrivileges) {
        final String crudAll = "CRUD_ALL";

        return crudAll + "_" + partOfPrivileges;
    }

    private Privilege createPrivilegeIfNotFoundOrReturnExisting(String name) {
        Optional<Privilege> privilege = privilegeRepository.findOptionalByName(name);
        return privilege.orElseGet(() -> persistPrivilege(name));
    }

    private Privilege persistPrivilege(String name) {
        Privilege privilegeToSave = new Privilege(name);
        return privilegeRepository.save(privilegeToSave);
    }

    private Role createUserRoleIfNotFoundOrReturnExisting(String roleName, Set<Privilege> privileges) {
        Optional<Role> role = roleRepository.findOptionalByName(roleName);
        return role.orElseGet(() -> persistRole(roleName, privileges));
    }

    private Role persistRole(String roleName, Set<Privilege> privileges) {
        Role roleToPersist = new Role(roleName, privileges);
        return roleRepository.save(roleToPersist);
    }
}
