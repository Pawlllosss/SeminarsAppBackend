package pl.oczadly.spring.topics.user.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.oczadly.spring.topics.role.Role;
import pl.oczadly.spring.topics.user.management.entity.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAuthenticationDetails implements UserDetails {

    private Long id;
    private boolean isEnabled = true;
    private boolean isAccountNonLocked = true;
    private boolean isAccountNonExpired = true;
    private boolean isTokenValid = true;
    private Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    public static Builder builder() {
        return new Builder();
    }

    public UserAuthenticationDetails(Builder builder) {
        this.id = builder.id;
        this.authorities = builder.authorities;
        this.email = builder.email;
        this.password = builder.password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isTokenValid;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public static class Builder {
        private Long id;
        private Collection<? extends GrantedAuthority> authorities;
        private String email;
        private String password;

        public UserAuthenticationDetails build() {
            return new UserAuthenticationDetails(this);
        }

        public Builder fromUser(User user) {
            Set<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(Role::getPrivileges)
                    .flatMap(Set::stream)
                    .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                    .collect(Collectors.toSet());

            this.authorities = roles;
            this.id = user.getId();
            this.email = user.getEmail();
            this.password = user.getPassword();

            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }
    }
}
