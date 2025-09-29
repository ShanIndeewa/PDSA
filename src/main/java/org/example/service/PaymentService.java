package org.example.service;

import org.example.model.Student;
import org.example.DSA.PaymentSearch;
import org.example.DSA.LinkedStudentList;
import java.util.List;
import java.util.ArrayList;

public class PaymentService {
    // Static LinkedStudentList for managing students
    private static final LinkedStudentList staticStudentList = new LinkedStudentList();

    // Static initialization block to populate the static linked list
    static {
        initializeStaticStudentList();
    }

    /**
     * Initialize the static student linked list with sample data
     */
    private static void initializeStaticStudentList() {
        staticStudentList.addLast(new Student("SL001", "Michael", "Johnson", "michael.j@email.com", "0771111111", "Computer Science", 16000.0, 9600.0));
        staticStudentList.addLast(new Student("SL002", "Sarah", "Williams", "sarah.w@email.com", "0772222222", "Software Engineering", 15000.0, 12000.0));
        staticStudentList.addLast(new Student("SL003", "David", "Wilson", "david.w@email.com", "0773333333", "Information Technology", 14000.0, 8400.0));
        staticStudentList.addLast(new Student("SL004", "Emily", "Davis", "emily.d@email.com", "0774444444", "Data Science", 17000.0, 10200.0));
        staticStudentList.addLast(new Student("SL005", "James", "Miller", "james.m@email.com", "0775555555", "Cybersecurity", 18000.0, 5400.0));
    }

    /**
     * Add a student to the linked list at the beginning
     */
    public static void addStudentFirst(Student student) {
        staticStudentList.addFirst(student);
    }

    /**
     * Add a student to the linked list at the end
     */
    public static void addStudentLast(Student student) {
        staticStudentList.addLast(student);
    }

    /**
     * Add a student to the linked list at specific index
     */
    public static void addStudentAtIndex(int index, Student student) {
        staticStudentList.add(index, student);
    }

    /**
     * Remove student from linked list by index
     */
    public static Student removeStudentByIndex(int index) {
        return staticStudentList.remove(index);
    }

    /**
     * Remove student from linked list by student ID
     */
    public static boolean removeStudentById(String studentId) {
        return staticStudentList.removeByStudentId(studentId);
    }

    /**
     * Get student from linked list by index
     */
    public static Student getStudentByIndex(int index) {
        return staticStudentList.get(index);
    }

    /**
     * Find student in linked list by student ID
     */
    public static Student findStudentById(String studentId) {
        return staticStudentList.findByStudentId(studentId);
    }

    /**
     * Check if linked list contains a student with given ID
     */
    public static boolean containsStudent(String studentId) {
        return staticStudentList.contains(studentId);
    }

    /**
     * Get the index of a student in linked list by student ID
     */
    public static int getStudentIndex(String studentId) {
        return staticStudentList.indexOf(studentId);
    }

    /**
     * Get the size of the linked list
     */
    public static int getStudentListSize() {
        return staticStudentList.size();
    }

    /**
     * Check if the linked list is empty
     */
    public static boolean isStudentListEmpty() {
        return staticStudentList.isEmpty();
    }

    /**
     * Clear all students from the linked list
     */
    public static void clearStudentList() {
        staticStudentList.clear();
    }

    /**
     * Display all students in the linked list (forward)
     */
    public static void displayStudentList() {
        staticStudentList.display();
    }

    /**
     * Display all students in reverse order
     */
    public static void displayStudentListReverse() {
        staticStudentList.displayReverse();
    }

    /**
     * Get all students from linked list as an array
     */
    public static Student[] getStudentsAsArray() {
        return staticStudentList.toArray();
    }

    /**
     * Get all students from linked list as a List
     */
    public static List<Student> getAllStudents() {
        Student[] studentsArray = staticStudentList.toArray();
        List<Student> studentList = new ArrayList<>();
        for (Student student : studentsArray) {
            studentList.add(student);
        }
        return studentList;
    }

    /**
     * Search students in linked list using customized binary search for LinkedStudentList
     */
    public static Student[] searchStudents(String query) {
        // Use the customized binary search that works directly with LinkedStudentList
        return PaymentSearch.searchInLinkedList(staticStudentList, query);
    }

    /**
     * Update student information in linked list
     */
    public static boolean updateStudent(String studentId, Student updatedStudent) {
        int index = staticStudentList.indexOf(studentId);
        if (index != -1) {
            staticStudentList.remove(index);
            staticStudentList.add(index, updatedStudent);
            return true;
        }
        return false;
    }

    /**
     * Get students with payment eligibility status from linked list
     */
    public static List<Student> getEligibleStudents() {
        List<Student> eligibleStudents = new ArrayList<>();
        Student[] allStudents = staticStudentList.toArray();

        for (Student student : allStudents) {
            if (student.isEligible()) {
                eligibleStudents.add(student);
            }
        }
        return eligibleStudents;
    }

    /**
     * Get students with insufficient payment from linked list
     */
    public static List<Student> getIneligibleStudents() {
        List<Student> ineligibleStudents = new ArrayList<>();
        Student[] allStudents = staticStudentList.toArray();

        for (Student student : allStudents) {
            if (!student.isEligible()) {
                ineligibleStudents.add(student);
            }
        }
        return ineligibleStudents;
    }

    /**
     * Get linked list details as string
     */
    public static String getStudentListDetails() {
        return staticStudentList.toString();
    }

    /**
     * Calculate total fees for all students
     */
    public static double calculateTotalFees() {
        double total = 0.0;
        Student[] allStudents = staticStudentList.toArray();

        for (Student student : allStudents) {
            total += student.getTotalFees();
        }
        return total;
    }

    /**
     * Calculate total paid amount for all students
     */
    public static double calculateTotalPaidAmount() {
        double total = 0.0;
        Student[] allStudents = staticStudentList.toArray();

        for (Student student : allStudents) {
            total += student.getPaidAmount();
        }
        return total;
    }

    /**
     * Get students by course
     */
    public static List<Student> getStudentsByCourse(String course) {
        List<Student> courseStudents = new ArrayList<>();
        Student[] allStudents = staticStudentList.toArray();

        for (Student student : allStudents) {
            if (student.getCourse().equalsIgnoreCase(course)) {
                courseStudents.add(student);
            }
        }
        return courseStudents;
    }

    /**
     * Count eligible students
     */
    public static int countEligibleStudents() {
        return getEligibleStudents().size();
    }

    /**
     * Count ineligible students
     */
    public static int countIneligibleStudents() {
        return getIneligibleStudents().size();
    }

    /**
     * Get payment amount for a specific student
     */
    public static double getPaymentAmount(String studentId) {
        Student student = staticStudentList.findByStudentId(studentId);
        return student != null ? student.getPaidAmount() : 0.0;
    }

    /**
     * Update student payment amount
     */
    public static boolean updateStudentPayment(String studentId, double newPaymentAmount) {
        Student student = staticStudentList.findByStudentId(studentId);
        if (student != null) {
            // Create updated student with new payment amount
            Student updatedStudent = new Student(
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getCourse(),
                student.getTotalFees(),
                newPaymentAmount
            );

            // Update in the linked list
            return updateStudent(studentId, updatedStudent);
        }
        return false;
    }
}
