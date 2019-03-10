package space.outin.reservation_application.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Scope("session")
@Data
public class AuthSession {

    @Autowired
    private UsersRepository usersRepository;

    private Optional<Integer> userId = Optional.empty();

    public void loginSession(String userName, String password) throws AuthenticationException {
        Optional<User> userResult = usersRepository.findOneByUserName(userName);
        User currentUser = userResult.orElseThrow(
                () -> new AuthenticationException(AuthenticationException.INVALID_CREDENTIALS));
        
        if (!password.equals(currentUser.getAuthDetails().getPassword())) {
            throw new AuthenticationException(AuthenticationException.INVALID_CREDENTIALS);
        }
        userId = Optional.of(currentUser.getId());
    }

    public void logoutSession() {
        userId = Optional.empty();
    }

    public void verifyAuthOrThrow() throws AuthenticationException {
            userId.orElseThrow(
                () -> new AuthenticationException("Session invalid. Please re-authenticate."));
    }

    public static class AuthenticationException extends Exception {
        private static final long serialVersionUID = 5287762282118902333L;

        public static String INVALID_CREDENTIALS = "Incorrect username or password.";

        public AuthenticationException(String e) {
            super(e);
        }
    }
}