package cmpt276.demo.models;
import jakarta.persistence.*;

@Entity
@Table(name="otherrequest")
public class UserOtherRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid; //request id

    String requesterName;
    String otherDetail;

    @ManyToOne //Many to one relation of user's id and their leaving note
    @JoinColumn(name = "user_id")
    private User user;

    public UserOtherRequest() {

    }

    public UserOtherRequest(String requesterName, String otherDetail) {
        this.requesterName = requesterName;
        this.otherDetail = otherDetail;
    }

    public UserOtherRequest(User requestedUser) {
        this.user = requestedUser;
    }
    
    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getOtherDetail() {
        return otherDetail;
    }

    public void setReason(String otherDetail) {
        this.otherDetail = otherDetail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
