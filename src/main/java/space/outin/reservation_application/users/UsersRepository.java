package space.outin.reservation_application.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import space.outin.reservation_application.users.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    
    public Optional<User> findOneByUserName(String userName);
}