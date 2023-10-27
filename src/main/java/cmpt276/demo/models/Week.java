package cmpt276.demo.models;

// import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "weeks")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wid;

    // @OneToMany(mappedBy = "week")
    // private List<UserSchedule> userSchedules;

    @Column(name = "week_name")
    private String weekName;

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }
    
    public Week() {
        // Default constructor
    }

    public Week(String weekName) {
        this.weekName = weekName;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    // public List<UserSchedule> getUserSchedules() {
    //     return userSchedules;
    // }

    // public void setUserSchedules(List<UserSchedule> userSchedules) {
    //     this.userSchedules = userSchedules;
    // }
}
