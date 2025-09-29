package org.example.dao;

import org.example.model.Payment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentDAO {

    // Sample payment data array for demonstration purposes
    private static Payment[] paymentsArray = {
        new Payment("P001", "S001", 5000.0, LocalDateTime.of(2024, 1, 15, 10, 30), "Credit Card", "Semester 1 Fee", "Completed"),
        new Payment("P002", "S001", 4000.0, LocalDateTime.of(2024, 3, 20, 14, 45), "Bank Transfer", "Semester 2 Fee", "Completed"),
        new Payment("P003", "S001", 3000.0, LocalDateTime.of(2024, 5, 10, 9, 15), "Cash", "Remaining Balance", "Completed"),
        new Payment("P004", "S002", 6000.0, LocalDateTime.of(2024, 1, 18, 11, 0), "Credit Card", "Initial Payment", "Completed"),
        new Payment("P005", "S002", 4500.0, LocalDateTime.of(2024, 4, 5, 16, 20), "Debit Card", "Mid-term Payment", "Completed"),
        new Payment("P006", "S003", 4000.0, LocalDateTime.of(2024, 2, 1, 13, 30), "Bank Transfer", "First Installment", "Completed"),
        new Payment("P007", "S003", 4000.0, LocalDateTime.of(2024, 6, 15, 10, 45), "Cash", "Second Installment", "Completed"),
        new Payment("P008", "S004", 8000.0, LocalDateTime.of(2024, 1, 10, 12, 0), "Credit Card", "Semester 1 & 2", "Completed"),
        new Payment("P009", "S004", 7300.0, LocalDateTime.of(2024, 5, 25, 15, 30), "Bank Transfer", "Final Payment", "Completed"),
        new Payment("P010", "S005", 5000.0, LocalDateTime.of(2024, 2, 14, 14, 15), "Debit Card", "Partial Payment", "Completed"),
        new Payment("P011", "S005", 4300.0, LocalDateTime.of(2024, 7, 8, 11, 45), "Cash", "Additional Payment", "Completed"),
        new Payment("P012", "S006", 3500.0, LocalDateTime.of(2024, 1, 25, 9, 30), "Credit Card", "Initial Fee", "Completed"),
        new Payment("P013", "S006", 6250.0, LocalDateTime.of(2024, 4, 12, 16, 0), "Bank Transfer", "Major Payment", "Completed"),
        new Payment("P014", "S007", 5600.0, LocalDateTime.of(2024, 2, 8, 13, 15), "Debit Card", "Semester Payment", "Completed"),
        new Payment("P015", "S007", 5600.0, LocalDateTime.of(2024, 6, 20, 10, 30), "Credit Card", "Final Semester", "Completed"),
        new Payment("P016", "S008", 7000.0, LocalDateTime.of(2024, 1, 30, 12, 45), "Bank Transfer", "Bulk Payment", "Completed"),
        new Payment("P017", "S008", 5800.0, LocalDateTime.of(2024, 5, 5, 14, 20), "Cash", "Remaining Amount", "Completed"),
        new Payment("P018", "S009", 3400.0, LocalDateTime.of(2024, 3, 15, 11, 30), "Credit Card", "Partial Payment", "Completed"),
        new Payment("P019", "S009", 3400.0, LocalDateTime.of(2024, 7, 1, 15, 45), "Debit Card", "Second Payment", "Completed"),
        new Payment("P020", "S010", 9000.0, LocalDateTime.of(2024, 1, 20, 10, 0), "Bank Transfer", "Major Payment", "Completed"),
        new Payment("P021", "S010", 4950.0, LocalDateTime.of(2024, 4, 28, 13, 30), "Credit Card", "Final Payment", "Completed")
    };

    /**
     * Get all payments as an array
     */
    public Payment[] getAllPaymentsArray() {
        return Arrays.copyOf(paymentsArray, paymentsArray.length);
    }

    /**
     * Get all payments as a list
     */
    public List<Payment> getAllPayments() {
        return new ArrayList<>(Arrays.asList(paymentsArray));
    }

    /**
     * Get payments by student ID
     */
    public List<Payment> getPaymentsByStudentId(String studentId) {
        List<Payment> studentPayments = new ArrayList<>();
        for (Payment payment : paymentsArray) {
            if (payment.getStudentId().equals(studentId)) {
                studentPayments.add(payment);
            }
        }
        return studentPayments;
    }

    /**
     * Get payments by status
     */
    public List<Payment> getPaymentsByStatus(String status) {
        List<Payment> statusPayments = new ArrayList<>();
        for (Payment payment : paymentsArray) {
            if (payment.getStatus().equalsIgnoreCase(status)) {
                statusPayments.add(payment);
            }
        }
        return statusPayments;
    }

    /**
     * Get payments by payment method
     */
    public List<Payment> getPaymentsByMethod(String method) {
        List<Payment> methodPayments = new ArrayList<>();
        for (Payment payment : paymentsArray) {
            if (payment.getPaymentMethod().equalsIgnoreCase(method)) {
                methodPayments.add(payment);
            }
        }
        return methodPayments;
    }

    /**
     * Find payment by ID
     */
    public Payment findPaymentById(String paymentId) {
        for (Payment payment : paymentsArray) {
            if (payment.getPaymentId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    /**
     * Get total payment amount for a student
     */
    public double getTotalPaymentByStudentId(String studentId) {
        double total = 0.0;
        for (Payment payment : paymentsArray) {
            if (payment.getStudentId().equals(studentId) &&
                payment.getStatus().equalsIgnoreCase("Completed")) {
                total += payment.getAmount();
            }
        }
        return total;
    }

    /**
     * Get payments within a date range
     */
    public List<Payment> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Payment> dateRangePayments = new ArrayList<>();
        for (Payment payment : paymentsArray) {
            LocalDateTime paymentDate = payment.getPaymentDate();
            if (paymentDate.isAfter(startDate) && paymentDate.isBefore(endDate)) {
                dateRangePayments.add(payment);
            }
        }
        return dateRangePayments;
    }

    /**
     * Add a new payment
     */
    public boolean addPayment(Payment payment) {
        // In a real application, this would add to database
        // For demo purposes, we'll just return true
        System.out.println("Payment added: " + payment.toString());
        return true;
    }

    /**
     * Update payment information
     */
    public boolean updatePayment(Payment payment) {
        for (int i = 0; i < paymentsArray.length; i++) {
            if (paymentsArray[i].getPaymentId().equals(payment.getPaymentId())) {
                paymentsArray[i] = payment;
                System.out.println("Payment updated: " + payment.toString());
                return true;
            }
        }
        return false;
    }

    /**
     * Delete payment by ID
     */
    public boolean deletePayment(String paymentId) {
        // In a real application, this would delete from database
        // For demo purposes, we'll just return true if payment exists
        Payment payment = findPaymentById(paymentId);
        if (payment != null) {
            System.out.println("Payment deleted: " + payment.toString());
            return true;
        }
        return false;
    }

    /**
     * Get total number of payments
     */
    public int getTotalPaymentsCount() {
        return paymentsArray.length;
    }

    /**
     * Get total amount of all payments
     */
    public double getTotalPaymentAmount() {
        double total = 0.0;
        for (Payment payment : paymentsArray) {
            if (payment.getStatus().equalsIgnoreCase("Completed")) {
                total += payment.getAmount();
            }
        }
        return total;
    }

    /**
     * Get count of payments by student ID
     */
    public int getPaymentCountByStudentId(String studentId) {
        return getPaymentsByStudentId(studentId).size();
    }
}
