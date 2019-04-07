package space.outin.reservation_application.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class AuthSession {

    @Autowired
    private UsersRepository usersRepository;

    private Optional<Integer> userId = Optional.empty();

    public User loginSession(String email, String password) throws AuthenticationException {
        Optional<User> userResult = usersRepository.findOneByEmail(email);
        User currentUser = userResult.orElseThrow(
                () -> new AuthenticationException(AuthenticationException.INVALID_CREDENTIALS));
        
        if (!password.equals(currentUser.getPassword())) {
            throw new AuthenticationException(AuthenticationException.INVALID_CREDENTIALS);
        }
        userId = Optional.of(currentUser.getId());
        return currentUser;
    }

    public void logoutSession() {
        userId = Optional.empty();
    }

    public void verifyAuthOrThrow() throws AuthenticationException {
        userId.orElseThrow(
            () -> new AuthenticationException(AuthenticationException.INVALID_SESSION));
    }

    public User fetchCurrentUser() {
        return usersRepository.findById(userId.get()).get();
    }

    public static class AuthenticationException extends Exception {
        private static final long serialVersionUID = 5287762282118902333L;

        public static String INVALID_CREDENTIALS = "Incorrect email or password.";
        public static String INVALID_SESSION = "Session invalid. Please re-authenticate.";

        public AuthenticationException(String e) {
            super(e);
        }
    }
}