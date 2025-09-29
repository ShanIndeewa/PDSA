package org.example.model;

public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String course;
    private double totalFees;
    private double paidAmount;
    private boolean isEligible;
    private String paymentDate;

    public Student() {}

    public Student(String studentId, String firstName, String lastName, String email,
                   String phone, String course, double totalFees, double paidAmount) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.totalFees = totalFees;
        this.paidAmount = paidAmount;
        this.isEligible = paidAmount >= totalFees * 0.6; // 60% payment required for eligibility
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return firstName + " " + lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public double getTotalFees() { return totalFees; }
    public void setTotalFees(double totalFees) { this.totalFees = totalFees; }

    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
        this.isEligible = paidAmount >= totalFees * 0.6;
    }

    public boolean isEligible() { return isEligible; }
    public void setEligible(boolean eligible) { this.isEligible = eligible; }

    public double getRemainingBalance() { return totalFees - paidAmount; }

    public double getPaymentPercentage() {
        return totalFees > 0 ? (paidAmount / totalFees) * 100 : 0;
    }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", course='" + course + '\'' +
                ", eligible=" + isEligible +
                '}';
    }
}
