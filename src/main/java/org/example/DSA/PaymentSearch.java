package org.example.DSA;

import org.example.model.Student;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class PaymentSearch {
    /**
     * Search for students directly in LinkedStudentList using optimized binary search
     */
    public static Student[] searchInLinkedList(LinkedStudentList studentList, String query) {
        if (query == null || query.trim().isEmpty()) return new Student[0];

        String q = query.trim().toLowerCase();
        List<Student> results = new ArrayList<>();

        // Search by ID using binary search on linked list
        searchByIdInLinkedList(studentList, q, results);

        // Search by name using binary search on linked list
        searchByNameInLinkedList(studentList, q, results);

        return results.toArray(new Student[0]);
    }

    /**
     * Binary search for student ID directly in LinkedStudentList
     */
    private static void searchByIdInLinkedList(LinkedStudentList studentList, String searchVal, List<Student> results) {
        if (studentList.isEmpty()) return;

        int size = studentList.size();
        int lowIndex = 0;
        int highIndex = size - 1;

        while (lowIndex <= highIndex) {
            int midIndex = lowIndex + (highIndex - lowIndex) / 2;
            Student midStudent = studentList.get(midIndex);
            String midId = midStudent.getStudentId().toLowerCase();

            // Check for partial match (contains)
            if (midId.contains(searchVal)) {
                // Found a match, add if not already present
                if (!containsStudentInList(results, midStudent)) {
                    results.add(midStudent);
                }

                // Search left and right for other matches in linked list
                searchLeftInLinkedList(studentList, midIndex - 1, searchVal, results, true);
                searchRightInLinkedList(studentList, midIndex + 1, searchVal, results, true);
                break;
            } else if (midId.compareTo(searchVal) < 0) {
                lowIndex = midIndex + 1;
            } else {
                highIndex = midIndex - 1;
            }
        }
    }

    /**
     * Binary search for student name directly in LinkedStudentList
     */
    private static void searchByNameInLinkedList(LinkedStudentList studentList, String searchVal, List<Student> results) {
        if (studentList.isEmpty()) return;

        int size = studentList.size();
        int lowIndex = 0;
        int highIndex = size - 1;

        while (lowIndex <= highIndex) {
            int midIndex = lowIndex + (highIndex - lowIndex) / 2;
            Student midStudent = studentList.get(midIndex);
            String midName = midStudent.getFullName().toLowerCase();

            // Check for partial match (contains)
            if (midName.contains(searchVal)) {
                // Found a match, add if not already present
                if (!containsStudentInList(results, midStudent)) {
                    results.add(midStudent);
                }

                // Search left and right for other matches in linked list
                searchLeftInLinkedList(studentList, midIndex - 1, searchVal, results, false);
                searchRightInLinkedList(studentList, midIndex + 1, searchVal, results, false);
                break;
            } else if (midName.compareTo(searchVal) < 0) {
                lowIndex = midIndex + 1;
            } else {
                highIndex = midIndex - 1;
            }
        }
    }

    /**
     * Search left side in LinkedStudentList for additional matches
     */
    private static void searchLeftInLinkedList(LinkedStudentList studentList, int index, String searchVal, List<Student> results, boolean isIdSearch) {
        while (index >= 0) {
            Student student = studentList.get(index);
            String compareValue = isIdSearch ? student.getStudentId().toLowerCase() : student.getFullName().toLowerCase();

            if (compareValue.contains(searchVal)) {
                if (!containsStudentInList(results, student)) {
                    results.add(student);
                }
                index--;
            } else {
                break;
            }
        }
    }

    /**
     * Search right side in LinkedStudentList for additional matches
     */
    private static void searchRightInLinkedList(LinkedStudentList studentList, int index, String searchVal, List<Student> results, boolean isIdSearch) {
        int size = studentList.size();
        while (index < size) {
            Student student = studentList.get(index);
            String compareValue = isIdSearch ? student.getStudentId().toLowerCase() : student.getFullName().toLowerCase();

            if (compareValue.contains(searchVal)) {
                if (!containsStudentInList(results, student)) {
                    results.add(student);
                }
                index++;
            } else {
                break;
            }
        }
    }

    /**
     * Check if student already exists in the result list
     */
    private static boolean containsStudentInList(List<Student> results, Student student) {
        for (Student s : results) {
            if (s.getStudentId().equals(student.getStudentId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Search for students by ID or name using binary search (case-insensitive, partial match)
     */
    public static Student[] search(List<Student> students, String query) {
        if (query == null || query.trim().isEmpty()) return new Student[0];

        String q = query.trim().toLowerCase();

        // Convert list to array for binary search operations
        Student[] studentsArray = students.toArray(new Student[0]);

        // Create arrays and sort by student ID for binary search
        Student[] sortedById = Arrays.copyOf(studentsArray, studentsArray.length);
        Arrays.sort(sortedById, Comparator.comparing(s -> s.getStudentId().toLowerCase()));

        // Create result array with maximum possible size
        Student[] tempResult = new Student[studentsArray.length];
        int resultCount = 0;

        // Binary search for student ID matches
        resultCount = binarySearchById(sortedById, q, tempResult, resultCount);

        // Create arrays and sort by full name for binary search
        Student[] sortedByName = Arrays.copyOf(studentsArray, studentsArray.length);
        Arrays.sort(sortedByName, Comparator.comparing(s -> s.getFullName().toLowerCase()));

        // Binary search for name matches (avoid duplicates)
        resultCount = binarySearchByName(sortedByName, q, tempResult, resultCount);

        // Return array with exact size
        return Arrays.copyOf(tempResult, resultCount);
    }

    /**
     * Binary search implementation for student ID
     */
    private static int binarySearchById(Student[] sortedStudents, String searchVal, Student[] result, int resultCount) {
        int lowIndex = 0;
        int highIndex = sortedStudents.length - 1;

        while (lowIndex <= highIndex) {
            int midIndex = lowIndex + (highIndex - lowIndex) / 2;
            Student midStudent = sortedStudents[midIndex];
            String midId = midStudent.getStudentId().toLowerCase();

            if (midId.contains(searchVal)) {
                // Found a match, add to result if not already present
                if (!containsStudent(result, resultCount, midStudent)) {
                    result[resultCount] = midStudent;
                    resultCount++;
                }

                // Search left and right for other matches
                resultCount = searchLeft(sortedStudents, midIndex - 1, searchVal, result, resultCount, true);
                resultCount = searchRight(sortedStudents, midIndex + 1, searchVal, result, resultCount, true);
                break;
            } else if (midId.compareTo(searchVal) < 0) {
                lowIndex = midIndex + 1;
            } else {
                highIndex = midIndex - 1;
            }
        }
        return resultCount;
    }

    /**
     * Binary search implementation for student name
     */
    private static int binarySearchByName(Student[] sortedStudents, String searchVal, Student[] result, int resultCount) {
        int lowIndex = 0;
        int highIndex = sortedStudents.length - 1;

        while (lowIndex <= highIndex) {
            int midIndex = lowIndex + (highIndex - lowIndex) / 2;
            Student midStudent = sortedStudents[midIndex];
            String midName = midStudent.getFullName().toLowerCase();

            if (midName.contains(searchVal)) {
                // Found a match, add to result if not already present
                if (!containsStudent(result, resultCount, midStudent)) {
                    result[resultCount] = midStudent;
                    resultCount++;
                }

                // Search left and right for other matches
                resultCount = searchLeft(sortedStudents, midIndex - 1, searchVal, result, resultCount, false);
                resultCount = searchRight(sortedStudents, midIndex + 1, searchVal, result, resultCount, false);
                break;
            } else if (midName.compareTo(searchVal) < 0) {
                lowIndex = midIndex + 1;
            } else {
                highIndex = midIndex - 1;
            }
        }
        return resultCount;
    }

    /**
     * Search left side for additional matches
     */
    private static int searchLeft(Student[] sortedStudents, int index, String searchVal, Student[] result, int resultCount, boolean isIdSearch) {
        while (index >= 0) {
            Student student = sortedStudents[index];
            String compareValue = isIdSearch ? student.getStudentId().toLowerCase() : student.getFullName().toLowerCase();

            if (compareValue.contains(searchVal)) {
                if (!containsStudent(result, resultCount, student)) {
                    result[resultCount] = student;
                    resultCount++;
                }
                index--;
            } else {
                break;
            }
        }
        return resultCount;
    }

    /**
     * Search right side for additional matches
     */
    private static int searchRight(Student[] sortedStudents, int index, String searchVal, Student[] result, int resultCount, boolean isIdSearch) {
        while (index < sortedStudents.length) {
            Student student = sortedStudents[index];
            String compareValue = isIdSearch ? student.getStudentId().toLowerCase() : student.getFullName().toLowerCase();

            if (compareValue.contains(searchVal)) {
                if (!containsStudent(result, resultCount, student)) {
                    result[resultCount] = student;
                    resultCount++;
                }
                index++;
            } else {
                break;
            }
        }
        return resultCount;
    }

    /**
     * Check if student already exists in the result array
     */
    private static boolean containsStudent(Student[] result, int resultCount, Student student) {
        for (int i = 0; i < resultCount; i++) {
            if (result[i] != null && result[i].getStudentId().equals(student.getStudentId())) {
                return true;
            }
        }
        return false;
    }
}
