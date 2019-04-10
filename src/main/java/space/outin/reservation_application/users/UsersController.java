package space.outin.reservation_application.users;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.RestaurantsRepository;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;
import space.outin.reservation_application.users.UserService.UserException;
import space.outin.reservation_application.users.transfer.DeleteConfirmation;
import space.outin.reservation_application.users.transfer.UserChanges;

@RestController
@RequestMapping("/users")
public class UsersController {

    private @Autowired UsersRepository users;
    private @Autowired RestaurantsRepository restaurantsRepository;
    private @Autowired AuthSession authSession;
    private @Autowired UserService userService;

    @PostMapping("/create")
    public User create(@RequestBody @Valid User u, @RequestParam(required = false) boolean isOwner)
            throws UserAlreadyExistsException, AuthenticationException {
        u.setId(null);
        verifyEmailIsUnique(null, u.getEmail());
        authSession.logoutSession();
        if (isOwner) {
            Restaurant r = new Restaurant();
            u.setRestaurant(restaurantsRepository.save(r));
        }

        users.save(u);
        return authSession.loginSession(u.getEmail(), u.getPassword());
    }

    @PostMapping("/update")
    public User update(@RequestBody @Valid UserChanges changes)
            throws UserAlreadyExistsException, AuthenticationException {
        authSession.verifyAuthOrThrow();
        User user = authSession.fetchCurrentUser();

        if (changes.getEmail().isPresent()) {
            String newEmail = changes.getEmail().get();
            if (!user.getEmail().equals(newEmail)) {
                verifyEmailIsUnique(user.getId(), newEmail);
            }
        }
        user.applyChanges(changes);
        return users.save(user);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DeleteConfirmation passwordCheck) throws AuthenticationException, UserException {
        authSession.verifyAuthOrThrow();
        User currentUser = authSession.fetchCurrentUser();
        if (!currentUser.getPassword().equals(passwordCheck.getPassword())) {
            throw new AuthenticationException(AuthenticationException.INVALID_CREDENTIALS);
        }
        authSession.logoutSession();
        userService.deleteUser(currentUser);
    }

    public void verifyEmailIsUnique(Integer id, String email) throws UserAlreadyExistsException {
        Optional<User> existingUser = users.findOneByEmail(email);
        if (existingUser.isPresent() && existingUser.get().getId() != id) {
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