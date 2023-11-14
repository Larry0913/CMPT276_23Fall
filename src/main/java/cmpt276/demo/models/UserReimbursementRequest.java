package cmpt276.demo.models;
import jakarta.persistence.*;

@Entity
@Table(name="leaverequest")
public class UserReimbursementRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid; //request id

    String requesterName;
    String typeExpense;
    String reimbursementAmount;
    String reasonForReimbursement;

    @ManyToOne //Many to one relation of user's id and their leaving note
    @JoinColumn(name = "user_id")
    private User user;

    public UserReimbursementRequest() {

    }

    public UserReimbursementRequest(String requesterName, String typeExpense, String reimbursementAmount, String reasonForReimbursement) {
        this.requesterName = requesterName;
        this.typeExpense = typeExpense;
        this.reimbursementAmount = reimbursementAmount;
        this.reasonForReimbursement = reasonForReimbursement;
    }

    public UserReimbursementRequest(User requestedUser) {
        this.user = requestedUser;
    }
    
    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public String getTypeExpense() {
        return typeExpense;
    }

    public void setTypeExpense(String typeExpense) {
        this.typeExpense = typeExpense;
    }

    public String reimbursementAmount() {
        return reimbursementAmount;
    }

    public void setReimbursementAmount(String reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
    }

    public String getReason() {
        return reasonForReimbursement;
    }

    public void setReason(String reasonForReimbursement) {
        this.reasonForReimbursement = reasonForReimbursement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
