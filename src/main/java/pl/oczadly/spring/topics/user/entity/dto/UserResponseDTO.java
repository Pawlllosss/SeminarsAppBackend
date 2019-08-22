package pl.oczadly.spring.topics.user.entity.dto;

import javax.validation.constraints.NotBlank;

public class UserResponseDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String nickname;

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
}
