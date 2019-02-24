package space.outin.reservation_application.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.users.User;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository users;

    @PostMapping("/save")
    public User save(@RequestBody User u) {
        return users.save(u);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        users.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Integer id) {
        return users.getOne(id);
    }
}