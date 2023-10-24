package cmpt276.demo.dao;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    // List<User> findByUsername(String username);
    // List<User> findByUsernameAndPassword(String username, String password);
}
