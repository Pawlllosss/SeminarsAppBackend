package pl.oczadly.spring.topics.privilege;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.oczadly.spring.topics.role.UserRole;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "privileges")
public class Privilege {

    @Id
    @GeneratedValue(generator = "privilege_generator")
    @SequenceGenerator(
            name = "privilege_generator",
            sequenceName = "privilege_sequence"
    )
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<UserRole> roles;

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

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
