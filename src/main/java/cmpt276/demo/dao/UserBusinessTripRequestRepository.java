package cmpt276.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpt276.demo.models.User;
import cmpt276.demo.models.UserBusinessTripRequest;

public interface UserBusinessTripRequestRepository extends JpaRepository<UserBusinessTripRequest, Integer> {
    List<UserBusinessTripRequest> findByTripLocation(String tripLocation);
    List<UserBusinessTripRequest> findByRequesterName(String requesterName);
    List<UserBusinessTripRequest> findByUser(User user);
    List<UserBusinessTripRequest> findByTripBudget(String tripBudget);
    List<UserBusinessTripRequest> findByStartDate(String startDate);
    List<UserBusinessTripRequest> findByEndDate(String endDate);
}
