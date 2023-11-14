package cmpt276.demo.models;
import jakarta.persistence.*;

@Entity
@Table(name="businesstriprequest")
public class UserBusinessTripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid; //request id

    String requesterName;
    String tripLocation;
    String startDate;
    String endDate;
    String tripBudget;
    String reasonForTrip;

    @ManyToOne //Many to one relation of user's id and their leaving note
    @JoinColumn(name = "user_id")
    private User user;

    public UserBusinessTripRequest() {

    }

    public UserBusinessTripRequest(String requesterName, String tripLocation, String startDate, String endDate, String tripBudget, String reasonForTrip) {
        this.requesterName = requesterName;
        this.tripLocation = tripLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripBudget = tripBudget;
        this.reasonForTrip = reasonForTrip;
    }

    public UserBusinessTripRequest(User requestedUser) {
        this.user = requestedUser;
    }
    
    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public String getTripLocation() {
        return tripLocation;
    }

    public void setTripLocation(String tripLocation) {
        this.tripLocation = tripLocation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTripBudget(String tripBudget) {
        return tripBudget;
    }

    public void setTripBudget(String tripBudget) {
        this.tripBudget = tripBudget;
    }

    public String getReason() {
        return reasonForTrip;
    }

    public void setReason(String reasonForTrip) {
        this.reasonForTrip = reasonForTrip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
