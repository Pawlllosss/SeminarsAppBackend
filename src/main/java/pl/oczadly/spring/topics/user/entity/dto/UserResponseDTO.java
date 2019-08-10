package pl.oczadly.spring.topics.user.entity.dto;

import javax.validation.constraints.NotBlank;

public class UserResponseDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String nickName;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}
