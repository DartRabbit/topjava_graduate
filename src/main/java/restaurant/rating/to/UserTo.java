package restaurant.rating.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import restaurant.rating.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
public class UserTo implements Serializable{
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private Integer id;

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

    public UserTo(User user){
        this.id=user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
