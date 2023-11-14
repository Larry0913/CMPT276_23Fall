package cmpt276.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserOtherRequest;

public interface UserOtherRequestRepository extends JpaRepository<UserOtherRequest, Integer> {
    List<UserOtherRequest> findByRequesterName(String requesterName);
    List<UserOtherRequest> findByUser(User user);
}