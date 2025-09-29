package org.example.dao;

import org.example.model.Student;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentDAO {

    // Sample data array for demonstration purposes
    private static Student[] studentsArray = {
        new Student("S001", "John", "Doe", "john.doe@email.com", "0771234567", "Computer Science", 15000.0, 12000.0),
        new Student("S002", "Jane", "Smith", "jane.smith@email.com", "0779876543", "Information Technology", 14000.0, 10500.0),
        new Student("S003", "Mike", "Johnson", "mike.johnson@email.com", "0775551234", "Software Engineering", 16000.0, 8000.0),
        new Student("S004", "Sarah", "Williams", "sarah.williams@email.com", "0772223333", "Data Science", 17000.0, 15300.0),
        new Student("S005", "David", "Brown", "david.brown@email.com", "0778889999", "Cybersecurity", 15500.0, 9300.0),
        new Student("S006", "Emily", "Davis", "emily.davis@email.com", "0774445555", "Computer Science", 15000.0, 9750.0),
        new Student("S007", "Alex", "Wilson", "alex.wilson@email.com", "0776667777", "Information Technology", 14000.0, 11200.0),
        new Student("S008", "Lisa", "Taylor", "lisa.taylor@email.com", "0773334444", "Software Engineering", 16000.0, 12800.0),
        new Student("S009", "Chris", "Anderson", "chris.anderson@email.com", "0777778888", "Data Science", 17000.0, 6800.0),
        new Student("S010", "Amy", "Thomas", "amy.thomas@email.com", "0771112222", "Cybersecurity", 15500.0, 13950.0)
    };

    /**
     * Get all students as an array
     */
    public Student[] getAllStudentsArray() {
        return Arrays.copyOf(studentsArray, studentsArray.length);
    }

    /**
     * Get all students as a list
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(Arrays.asList(studentsArray));
    }

    /**
     * Get eligible students only
     */
    public List<Student> getEligibleStudents() {
        List<Student> eligibleStudents = new ArrayList<>();
        for (Student student : studentsArray) {
            if (student.isEligible()) {
                eligibleStudents.add(student);
            }
        }
        return eligibleStudents;
    }

    /**
     * Get ineligible students only
     */
    public List<Student> getIneligibleStudents() {
        List<Student> ineligibleStudents = new ArrayList<>();
        for (Student student : studentsArray) {
            if (!student.isEligible()) {
                ineligibleStudents.add(student);
            }
        }
        return ineligibleStudents;
    }

    /**
     * Find student by ID
     */
    public Student findStudentById(String studentId) {
        for (Student student : studentsArray) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Find students by course
     */
    public List<Student> findStudentsByCourse(String course) {
        List<Student> courseStudents = new ArrayList<>();
        for (Student student : studentsArray) {
            if (student.getCourse().equalsIgnoreCase(course)) {
                courseStudents.add(student);
            }
        }
        return courseStudents;
    }

    /**
     * Add a new student (for demonstration - adds to a copy of the array)
     */
    public boolean addStudent(Student student) {
        // In a real application, this would add to database
        // For demo purposes, we'll just return true
        System.out.println("Student added: " + student.toString());
        return true;
    }

    /**
     * Update student information
     */
    public boolean updateStudent(Student student) {
        for (int i = 0; i < studentsArray.length; i++) {
            if (studentsArray[i].getStudentId().equals(student.getStudentId())) {
                studentsArray[i] = student;
                System.out.println("Student updated: " + student.toString());
                return true;
            }
        }
        return false;
    }

    /**
     * Delete student by ID
     */
    public boolean deleteStudent(String studentId) {
        // In a real application, this would delete from database
        // For demo purposes, we'll just return true if student exists
        Student student = findStudentById(studentId);
        if (student != null) {
            System.out.println("Student deleted: " + student.toString());
            return true;
        }
        return false;
    }

    /**
     * Get total number of students
     */
    public int getTotalStudentsCount() {
        return studentsArray.length;
    }

    /**
     * Get number of eligible students
     */
    public int getEligibleStudentsCount() {
        return getEligibleStudents().size();
    }

    /**
     * Get number of ineligible students
     */
    public int getIneligibleStudentsCount() {
        return getIneligibleStudents().size();
    }
}
