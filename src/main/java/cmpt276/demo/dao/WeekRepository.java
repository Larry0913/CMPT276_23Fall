package cmpt276.demo.dao;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.Week;

public interface WeekRepository extends JpaRepository<Week, Integer>{
    // List<Week> findByUser(String user);

}
