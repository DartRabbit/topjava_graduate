package restaurant.rating.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rating.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
public class UserTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @Email
    @NotBlank
    private String email;

    @Getter
    @Setter
    @Size(min = 5, max = 32, message = "length must between 5 and 32 characters")
    private String password;

    public UserTo(User user) {
        super(user.getId());
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public UserTo(Integer id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
