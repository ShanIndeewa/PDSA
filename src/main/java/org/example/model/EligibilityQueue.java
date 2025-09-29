package org.example.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EligibilityQueue {
    private Queue<Student> eligibilityQueue;
    private List<Student> eligibleStudents;

    public EligibilityQueue() {
        this.eligibilityQueue = new LinkedList<>();
        this.eligibleStudents = new ArrayList<>();
    }

    /**
     * Add student to eligibility queue
     */
    public boolean addToQueue(Student student) {
        if (student != null && student.isEligible()) {
            eligibilityQueue.offer(student);
            if (!eligibleStudents.contains(student)) {
                eligibleStudents.add(student);
            }
            return true;
        }
        return false;
    }

    /**
     * Remove and return the next student from queue
     */
    public Student removeFromQueue() {
        Student student = eligibilityQueue.poll();
        if (student != null) {
            eligibleStudents.remove(student);
        }
        return student;
    }

    /**
     * Remove specific student from queue
     */
    public boolean removeStudent(Student student) {
        boolean removedFromQueue = eligibilityQueue.remove(student);
        boolean removedFromList = eligibleStudents.remove(student);
        return removedFromQueue || removedFromList;
    }

    /**
     * Remove student by ID
     */
    public boolean removeStudentById(String studentId) {
        Student toRemove = null;
        for (Student student : eligibilityQueue) {
            if (student.getStudentId().equals(studentId)) {
                toRemove = student;
                break;
            }
        }
        if (toRemove != null) {
            return removeStudent(toRemove);
        }
        return false;
    }

    /**
     * Peek at the next student without removing
     */
    public Student peekNext() {
        return eligibilityQueue.peek();
    }

    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return eligibilityQueue.isEmpty();
    }

    /**
     * Get queue size
     */
    public int getQueueSize() {
        return eligibilityQueue.size();
    }

    /**
     * Get all students in queue as list
     */
    public List<Student> getAllStudentsInQueue() {
        return new ArrayList<>(eligibilityQueue);
    }

    /**
     * Get all eligible students (including those not in queue)
     */
    public List<Student> getAllEligibleStudents() {
        return new ArrayList<>(eligibleStudents);
    }

    /**
     * Clear the entire queue
     */
    public void clearQueue() {
        eligibilityQueue.clear();
        eligibleStudents.clear();
    }

    /**
     * Check if student is in queue
     */
    public boolean containsStudent(Student student) {
        return eligibilityQueue.contains(student);
    }

    /**
     * Check if student ID is in queue
     */
    public boolean containsStudentId(String studentId) {
        for (Student student : eligibilityQueue) {
            if (student.getStudentId().equals(studentId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get student position in queue (1-based)
     */
    public int getStudentPosition(String studentId) {
        int position = 1;
        for (Student student : eligibilityQueue) {
            if (student.getStudentId().equals(studentId)) {
                return position;
            }
            position++;
        }
        return -1; // Not found
    }

    /**
     * Refresh queue with new eligible students
     */
    public void refreshQueue(List<Student> allStudents) {
        clearQueue();
        for (Student student : allStudents) {
            if (student.isEligible()) {
                addToQueue(student);
            }
        }
    }

    @Override
    public String toString() {
        return "EligibilityQueue{" +
                "queueSize=" + eligibilityQueue.size() +
                ", eligibleStudentsCount=" + eligibleStudents.size() +
                '}';
    }
}
