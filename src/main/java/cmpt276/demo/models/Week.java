package cmpt276.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "weeks")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String days;

    public Week() {
        // Default constructor
    }

    public Week(User user, String days) {
        this.user = user;
        this.days = days;
    }

    public Long getId() {
        return wid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
