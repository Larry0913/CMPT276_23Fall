package cmpt276.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_schedules")
public class UserSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "week1_id")
    private Week week1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "week2_id")
    private Week week2;

    public UserSchedule() {
        // Default constructor
    }

    public UserSchedule(User user, Week week1, Week week2) {
        this.user = user;
        this.week1 = week1;
        this.week2 = week2;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Week getWeek1() {
        return week1;
    }

    public void setWeek1(Week week1) {
        this.week1 = week1;
    }

    public Week getWeek2() {
        return week2;
    }

    public void setWeek2(Week week2) {
        this.week2 = week2;
    }
}
