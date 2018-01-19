package restaurant.rating.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import restaurant.rating.model.User;
import restaurant.rating.to.UserTo;
import restaurant.rating.util.UserUtil;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{
    private static final long serialVersionUID = 1L;
    private final UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(),user.getPassword(), true, true ,true, true, getRoles(user));
        this.userTo = UserUtil.asTo(user);
    }

    private static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static int id() {
        return get().userTo.getId();
    }

    public static Set<Role> getRoles(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(user.isAdmin() ? Role.ROLE_ADMIN : Role.ROLE_USER);
        return roles;
    }
}