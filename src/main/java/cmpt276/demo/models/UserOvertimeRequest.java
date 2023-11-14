package cmpt276.demo.models;
import jakarta.persistence.*;

@Entity
@Table(name="overtimerequest")
public class UserOvertimeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid; //request id

    String requesterName;
    String overtimeDate;
    String startTime;
    String endTime;
    String reasonForOvertime;

    @ManyToOne //Many to one relation of user's id and their leaving note
    @JoinColumn(name = "user_id")
    private User user;

    public UserOvertimeRequest() {

    }

    public UserOvertimeRequest(String requesterName, String overtimeDate, String startTime, String endTime, String reasonForOvertime) {
        this.requesterName = requesterName;
        this.overtimeDate = overtimeDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reasonForOvertime = reasonForOvertime;
    }

    public UserOvertimeRequest(User requestedUser) {
        this.user = requestedUser;
    }
    
    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public String getOvertimeDate() {
        return overtimeDate;
    }

    public void setOvertimeDate(String overtimeDate) {
        this.overtimeDate = overtimeDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reasonForOvertime;
    }

    public void setReason(String reasonForOvertime) {
        this.reasonForOvertime = reasonForOvertime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
