package cmpt276.demo.models;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name="payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payrollId;

    // private Week week;
    // private User user;
    private double hoursWorked;
    private BigDecimal grossIncome;
    private BigDecimal taxesCPT;
    private BigDecimal netIncome;
    // private static final double DEFAULT_HOURS_WORKED = 40.0;
    private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.077);

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private UserSchedule userSchedule;

    @ManyToOne
    @JoinColumn(name = "week_id")
    private Week week;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    

    // default
    public Payroll() {
        // this.hoursWorked = DEFAULT_HOURS_WORKED;
        // this.grossIncome = BigDecimal.ZERO;
        // this.taxesCPT = BigDecimal.ZERO;
        // this.netIncome = BigDecimal.ZERO;
    }

    // Args Constructor
    public Payroll(UserSchedule userSchedule) {
        if (userSchedule != null) {
            this.week = userSchedule.getWeek();
            this.user = userSchedule.getUser();
            this.hoursWorked = userSchedule.getTotHours();
            this.grossIncome = userSchedule.getUser().getHourlySalary().multiply(BigDecimal.valueOf(this.hoursWorked));
            this.taxesCPT = this.grossIncome.multiply(TAX_RATE);
            this.netIncome = this.grossIncome.subtract(this.taxesCPT);
        } else {
            // Set default values or handle the null case differently
            
            this.hoursWorked = 0;
            this.grossIncome = BigDecimal.ZERO;
            this.taxesCPT = BigDecimal.ZERO;
            this.netIncome = BigDecimal.ZERO;
           
        }
    }
    

    // Getters and Setters for each attribute

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public Week getWeek() {
        return week;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public BigDecimal getGrossIncome() {
        return grossIncome;
    }

    public BigDecimal getTaxesCPT() {
        return taxesCPT;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public User getUser() {
        return user;
    }
    

}


