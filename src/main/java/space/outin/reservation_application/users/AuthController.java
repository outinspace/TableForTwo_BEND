package space.outin.reservation_application.users;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;
import space.outin.reservation_application.users.transfer.AuthDetails;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthSession authSession;

    @PostMapping("/login")
    public User login(@RequestBody @Valid AuthDetails authDetails) throws AuthenticationException {
        return authSession.loginSession(authDetails.getEmail(), authDetails.getPassword());
    }

    @GetMapping("/logout")
    public void logout() {
        authSession.logoutSession();
    }

    @GetMapping("/hydrate")
    public User hydrateSessionWithCurrentUser() throws AuthenticationException {
        authSession.verifyAuthOrThrow();
        return authSession.fetchCurrentUser();
    }
}