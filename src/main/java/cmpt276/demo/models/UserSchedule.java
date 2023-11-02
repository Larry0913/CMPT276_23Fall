package cmpt276.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_week_association", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "week_id" }) })
public class UserSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "week_id")
    private Week week;

    @OneToOne(mappedBy = "userSchedule", cascade = CascadeType.ALL)
    private Payroll payroll;

    private String days;

    @Column(name = "tot_hours")
    private Integer totHours = 0;

    @Column(name = "late_days")
    private Integer lateDays = 0;

    public UserSchedule() {
        // Default constructor
    }

    public UserSchedule(User user, Week week, String days) {
        this.user = user;
        this.week = week;
        this.days = days;
    }

    public int calculateTotalHours() {
        int workingDays = 0;

        for (int i = 0; i < days.length(); i++) {
            if (days.charAt(i) != '-') {
                workingDays++;
            }
        }
        int totHours = workingDays * 8;

        if (lateDays >= 2) {
            totHours = totHours - (lateDays * 2);
        }

        return totHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getTotHours() {
        return totHours;
    }

    public void setTotHours(int totHours) {
        this.totHours = totHours;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

}