package space.outin.reservation_application.users;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository users;

    @Autowired
    private AuthSession authSession;

    @PostMapping("/create")
    public User create(@RequestBody @Valid User u) throws UserAlreadyExistsException, AuthenticationException {
        // Validation
        u.setId(null);
        verifyEmailIsUnique(u.getEmail());

        // Authentication
        authSession.logoutSession();

        return users.save(u);
    }

    @PostMapping("/update/{id}")
    public User update(@RequestParam("id") Integer id, @RequestBody @Valid User u) throws UserAlreadyExistsException {
        u.setId(id);
        verifyEmailIsUnique(u.getEmail());
        return users.save(u);
    }

    @PostMapping("/delete")
    public void delete() throws AuthenticationException {
        authSession.verifyAuthOrThrow();
        authSession.logoutSession();
        // TODO: delete associated reservations/restaurants
        users.deleteById(authSession.getUserId().get());
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Integer id) {
        // TODO: This is just for testing
        return users.getOne(id);
    }

    public void verifyEmailIsUnique(String email) throws UserAlreadyExistsException {
        Optional<User> existingUser = users.findOneByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
    }

    public static class UserAlreadyExistsException extends Exception {
        private static final long serialVersionUID = 6203487367668009424L;

        public UserAlreadyExistsException() {
            super("User already exists for the given email address.");
        }
    }
}