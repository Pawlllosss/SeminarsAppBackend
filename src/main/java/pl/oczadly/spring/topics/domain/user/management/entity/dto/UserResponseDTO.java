package pl.oczadly.spring.topics.domain.user.management.entity.dto;

import pl.oczadly.spring.topics.domain.privilege.entity.PrivilegeDTO;
import pl.oczadly.spring.topics.domain.role.entity.dto.RoleDTO;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserResponseDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String nickname;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private Set<RoleDTO> roles;

    @NotBlank
    private Set<PrivilegeDTO> privileges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public Set<PrivilegeDTO> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<PrivilegeDTO> privileges) {
        this.privileges = privileges;
    }
}
