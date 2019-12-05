package pl.oczadly.spring.topics.domain.user.authentication.entity;

import javax.validation.constraints.NotBlank;

public class UserAuthenticationResponseDTO {

    @NotBlank
    private String token;

    @NotBlank
    private String tokenType = "Bearer";

    public UserAuthenticationResponseDTO(@NotBlank String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
