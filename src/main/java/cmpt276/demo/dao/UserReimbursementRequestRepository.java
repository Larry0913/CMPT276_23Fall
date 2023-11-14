package cmpt276.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserReimbursementRequest;

public interface UserReimbursementRequestRepository extends JpaRepository<UserReimbursementRequest, Integer> {
    List<UserReimbursementRequest> findByTypeExpense(String typeExpense);
    List<UserReimbursementRequest> findByRequesterName(String requesterName);
    List<UserReimbursementRequest> findByUser(User user);
    List<UserReimbursementRequest> findByReimbursementAmount(String startDate);
}
