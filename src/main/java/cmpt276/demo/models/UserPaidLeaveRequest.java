package cmpt276.demo.models;
import jakarta.persistence.*;

@Entity
@Table(name="paidleaverequest")
public class UserPaidLeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid; //request id

    String requesterName;
    String typePLeave;
    String startDate;
    String endDate;
    String reasonForPLeave;

    @ManyToOne //Many to one relation of user's id and their leaving note
    @JoinColumn(name = "user_id")
    private User user;

    public UserPaidLeaveRequest() {

    }

    public UserPaidLeaveRequest(String requesterName, String typePLeave, String startDate, String endDate, String reasonForPLeave) {
        this.requesterName = requesterName;
        this.typePLeave = typePLeave;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reasonForPLeave = reasonForPLeave;
    }

    public UserPaidLeaveRequest(User requestedUser) {
        this.user = requestedUser;
    }
    
    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public String getTypePLeave() {
        return typePLeave;
    }

    public void setTypeLeave(String typePLeave) {
        this.typePLeave = typePLeave;
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
        return reasonForPLeave;
    }

    public void setReason(String reasonForPLeave) {
        this.reasonForPLeave = reasonForPLeave;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
