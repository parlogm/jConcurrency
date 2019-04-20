package ro.utm.jc.repo;

import ro.utm.jc.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findOneByUserId(String userId);
    Optional<Users> findOneByUserIdAndPassword(String userId, String password);
}

