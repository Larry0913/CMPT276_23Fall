package cmpt276.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.UserSchedule;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Integer> {

}
