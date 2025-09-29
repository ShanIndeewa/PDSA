package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private String paymentId;
    private String studentId;
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String description;
    private String status;

    public Payment() {}

    public Payment(String paymentId, String studentId, double amount,
                   LocalDateTime paymentDate, String paymentMethod,
                   String description, String status) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFormattedDate() {
        return paymentDate != null ? paymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
    }

    public String getFormattedAmount() {
        return String.format("$%.2f", amount);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", status='" + status + '\'' +
                '}';
    }
}
