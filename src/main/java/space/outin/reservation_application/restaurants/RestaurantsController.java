package space.outin.reservation_application.restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.restaurants.Restaurant;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantsRepository restaurantsRepository;

    @PostMapping("/save")
    public Restaurant save(@RequestBody Restaurant r) {
        return restaurantsRepository.save(r);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        restaurantsRepository.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public Restaurant get(@PathVariable Integer id) {
        return restaurantsRepository.getOne(id);
    }
}