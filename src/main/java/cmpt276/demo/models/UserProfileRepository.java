package cmpt276.demo.models;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    // List<User> findByUsername(String username);
    // List<User> findByUsernameAndPassword(String username, String password);
}
