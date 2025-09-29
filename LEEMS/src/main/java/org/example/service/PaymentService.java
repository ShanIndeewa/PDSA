package org.example.service;

import org.example.model.Student;
import org.example.DSA.PaymentSearch;
import java.util.List;
import java.util.ArrayList;

public class PaymentService {
    // For demonstration, use a mock list of students
    private List<Student> students;

    public PaymentService() {
        students = new ArrayList<>();
        // Add some sample students
        students.add(new Student("S001", "John", "Doe", "john.doe@email.com", "0771234567", "Software Engineering", 15000.0, 1000.0));
        students.add(new Student("S002", "Jane", "Smith", "jane.smith@email.com", "0779876543", "Information Technology", 14000.0, 700.0));
        students.add(new Student("S003", "Alex", "Brown", "alex.brown@email.com", "0775556666", "Data Science", 17000.0, 0.0));
    }

    /**
     * Search for students by ID or name using PaymentSearch
     */
    public List<Student> searchStudents(String query) {
        return PaymentSearch.search(students, query);
    }

    // Optionally, expose all students
    public List<Student> getAllStudents() {
        return students;
    }
}
