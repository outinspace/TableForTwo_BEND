package space.outin.reservation_application.users;

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
    public User create(@RequestBody User u) {
        // TODO: Validate user object
        return users.save(u);
    }

    @PostMapping("/update/{id}")
    public User update(@RequestParam("id") Integer id, @RequestBody User u) {
        // TODO: Validate user object
        // TODO: Auth
        u.setId(id);
        return users.save(u);
    }

    @PostMapping("/delete")
    public void delete() throws AuthenticationException {
        authSession.verifyAuthOrThrow();
        authSession.logoutSession();
        users.deleteById(authSession.getUserId().get());
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Integer id) {
        // TODO: This is just for testing
        return users.getOne(id);
    }
}