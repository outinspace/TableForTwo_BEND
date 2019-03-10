package space.outin.reservation_application.reservations;

import java.util.Date;

import lombok.Data;

@Data
public class AuthDetails {
    private String userName;
    private String password;
    private String sessionCookie;
    private Date sessionExpiration;
}