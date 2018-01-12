package restaurant.rating.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import restaurant.rating.security.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"password", "email", "votes", "isAdmin"})
@NoArgsConstructor
@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity {

    @Column
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 64)
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("date DESC")
    protected List<Vote> votes;

    public User(Integer id, String name, String email, String password, boolean isAdmin) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isAdmin());
    }

    public Set<Role> getRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(isAdmin() ? Role.ROLE_ADMIN : Role.ROLE_USER);
        return roles;
    }

}
