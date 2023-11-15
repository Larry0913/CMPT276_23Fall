package cmpt276.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserSchedule;
import cmpt276.demo.models.Payroll;

import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Integer> {
    List<Payroll> findByUser(User user);
    Payroll findByUserSchedule(UserSchedule userSchedule);
}

