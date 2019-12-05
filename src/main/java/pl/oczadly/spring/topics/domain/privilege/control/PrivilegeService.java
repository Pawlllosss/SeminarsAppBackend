package pl.oczadly.spring.topics.domain.privilege.control;

import pl.oczadly.spring.topics.domain.privilege.entity.Privilege;

public interface PrivilegeService {

    Privilege createPrivilege(String name);
}
