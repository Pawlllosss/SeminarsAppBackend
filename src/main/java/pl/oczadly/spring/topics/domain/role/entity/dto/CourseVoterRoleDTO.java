package pl.oczadly.spring.topics.domain.role.entity.dto;

import javax.validation.constraints.NotBlank;

public class CourseVoterRoleDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private Long roleId;

    @NotBlank
    private String roleName;

    @NotBlank
    private String courseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
