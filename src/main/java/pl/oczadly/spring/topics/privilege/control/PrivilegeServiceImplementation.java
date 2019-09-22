package pl.oczadly.spring.topics.privilege.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.oczadly.spring.topics.privilege.entity.Privilege;

@Service
@Transactional
public class PrivilegeServiceImplementation implements PrivilegeService {

    private PrivilegeRepository privilegeRepository;

    @Override
    public Privilege createPrivilege(String name) {
        Privilege privilege = new Privilege(name);
        return privilegeRepository.save(privilege);
    }

    @Autowired
    public void setPrivilegeRepository(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }
}
