package space.outin.reservation_application.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthSession authSession;

    @PostMapping("/login")
    public User login(@RequestBody AuthDetails authDetails) throws AuthenticationException {
        return authSession.loginSession(authDetails.getUsername(), authDetails.getPassword());
    }

    @GetMapping("/logout")
    public void logout() {
        authSession.logoutSession();
    }

    @Data
    public static class AuthDetails {
        private String username;
        private String password;
    }
}