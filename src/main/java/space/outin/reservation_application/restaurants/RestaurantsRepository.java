package space.outin.reservation_application.restaurants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import space.outin.reservation_application.restaurants.Restaurant;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurant, Integer> {
    
}