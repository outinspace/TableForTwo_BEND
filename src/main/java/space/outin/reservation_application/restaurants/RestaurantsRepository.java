package space.outin.reservation_application.restaurants;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import space.outin.reservation_application.restaurants.Restaurant;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findAllByPublished(boolean published);
    List<Restaurant> findAllByName(String name);
}