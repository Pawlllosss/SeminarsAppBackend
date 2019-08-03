package pl.oczadly.spring.topics.role;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.oczadly.spring.topics.privilege.Privilege;
import pl.oczadly.spring.topics.user.entity.User;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(generator = "role_generator")
    @SequenceGenerator(
            name = "role_generator",
            sequenceName = "role_sequence"
    )
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    )
    private Set<Privilege> privileges;

    public Role(@NotBlank String name, Set<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }
}
