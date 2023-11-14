package cmpt276.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserOvertimeRequest;

public interface UserOvertimeRequestRepository extends JpaRepository<UserOvertimeRequest, Integer> {
    List<UserOvertimeRequest> findByOvertimeDate(String overtimeDate);
    List<UserOvertimeRequest> findByRequesterName(String requesterName);
    List<UserOvertimeRequest> findByUser(User user);
    List<UserOvertimeRequest> findByStartTime(String startTime);
    List<UserOvertimeRequest> findByEndTime(String endTime);
}
