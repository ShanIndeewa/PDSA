package org.example.DSA;

import org.example.model.Student;
import java.util.ArrayList;
import java.util.List;

public class PaymentSearch {
    /**
     * Search for students by ID or name (case-insensitive, partial match)
     */
    public static List<Student> search(List<Student> students, String query) {
        List<Student> result = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return result;
        String q = query.trim().toLowerCase();
        for (Student s : students) {
            if (s.getStudentId() != null && s.getStudentId().toLowerCase().contains(q)) {
                result.add(s);
            } else if (s.getFullName() != null && s.getFullName().toLowerCase().contains(q)) {
                result.add(s);
            }
        }
        return result;
    }
}

