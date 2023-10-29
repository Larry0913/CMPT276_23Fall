package cmpt276.demo.models;
import jakarta.persistence.*;

@Entity
@Table(name="leaverequest")
public class UserLeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid; //request id

    String requesterName;
    String typeLeave;
    String startDate;
    String endDate;
    String reasonForLeave;

    @ManyToOne //Many to one relation of user's id and their leaving note
    @JoinColumn(name = "user_id")
    private User user;

    public UserLeaveRequest() {

    }

    public UserLeaveRequest(String requesterName, String typeLeave, String startDate, String endDate, String reasonForLeave) {
        this.requesterName = requesterName;
        this.typeLeave = typeLeave;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reasonForLeave = reasonForLeave;
    }

    public UserLeaveRequest(User requestedUser) {
        this.user = requestedUser;
    }
    
    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public String getTypeLeave() {
        return typeLeave;
    }

    public void setTypeLeave(String typeLeave) {
        this.typeLeave = typeLeave;
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

    public String getReason() {
        return reasonForLeave;
    }

    public void setReason(String reasonForLeave) {
        this.reasonForLeave = reasonForLeave;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
