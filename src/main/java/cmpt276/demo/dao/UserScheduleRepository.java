package cmpt276.demo.dao;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserSchedule;
import cmpt276.demo.models.Week;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Integer> {

    UserSchedule findByUserAndWeek(User user,Week week);

}
