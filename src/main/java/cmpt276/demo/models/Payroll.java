package cmpt276.demo.models;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name="payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payrollId;

    private String week;
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
    @JoinColumn(name="user_id")
    private User user;

    // default
    public Payroll() {
        // this.hoursWorked = DEFAULT_HOURS_WORKED;
        // this.grossIncome = BigDecimal.ZERO;
        // this.taxesCPT = BigDecimal.ZERO;
        // this.netIncome = BigDecimal.ZERO;
    }

    // Args Constructor
    public Payroll(String week, double hoursWorked, BigDecimal grossIncome, BigDecimal taxesCPT, BigDecimal netIncome, User user) {
        this.week = week;
        this.hoursWorked = hoursWorked;
        this.grossIncome = grossIncome;
        this.taxesCPT = taxesCPT;
        this.netIncome = netIncome;
        this.user = user;
    }

    // Getters and Setters for each attribute

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursFromSchedule() {
    if (this.userSchedule != null) {
        this.hoursWorked = this.userSchedule.getTotHours();
    }
    }

    public BigDecimal getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(BigDecimal grossIncome) {
        this.grossIncome = grossIncome;
    }

    public BigDecimal getTaxesCPT() {
        return taxesCPT;
    }

    public void setTaxesCPT(BigDecimal taxesCPT) {
        this.taxesCPT = taxesCPT;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void computePayroll(BigDecimal hourlySalary) {
        if (this.hoursWorked == 0 && this.userSchedule != null) {
            setHoursFromSchedule();
        }

        this.grossIncome = hourlySalary.multiply(BigDecimal.valueOf(this.hoursWorked));
        this.taxesCPT = this.grossIncome.multiply(TAX_RATE);
        this.netIncome = this.grossIncome.subtract(this.taxesCPT);
    }

}


