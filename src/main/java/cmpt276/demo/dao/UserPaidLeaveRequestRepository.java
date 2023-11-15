package cmpt276.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserPaidLeaveRequest;

public interface UserPaidLeaveRequestRepository extends JpaRepository<UserPaidLeaveRequest, Integer>{
    List<UserPaidLeaveRequest> findByTypePLeave(String typePLeave);
    List<UserPaidLeaveRequest> findByRequesterName(String requesterName);
    List<UserPaidLeaveRequest> findByUser(User user);
    List<UserPaidLeaveRequest> findByStartDate(String startDate);
    List<UserPaidLeaveRequest> findByEndDate(String endDate);
}
