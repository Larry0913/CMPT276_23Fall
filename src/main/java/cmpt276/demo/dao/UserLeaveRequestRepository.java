package cmpt276.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.UserLeaveRequest;

public interface UserLeaveRequestRepository extends JpaRepository<UserLeaveRequest, Integer> {
    List<UserLeaveRequest> findByLeaveType(String leaveType);
    List<UserLeaveRequest> findByStartDate(String startDate);
    List<UserLeaveRequest> findByEndDate(String endDate);
}