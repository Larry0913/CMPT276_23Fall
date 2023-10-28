package cmpt276.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUsername(String username);

    List<User> findByUsernameAndPassword(String username, String password);
}