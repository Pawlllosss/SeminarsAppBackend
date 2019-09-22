package pl.oczadly.spring.topics.privilege.control;

import pl.oczadly.spring.topics.privilege.entity.Privilege;

public interface PrivilegeService {

    Privilege createPrivilege(String name);
}
