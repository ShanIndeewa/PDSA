package org.example.service;

import org.example.DSA.LinkedQueue;
import org.example.model.Student;

import java.util.List;

public class StudentService {
    public static LinkedQueue queue;

    public StudentService() {
        this.queue = new LinkedQueue();
    }

    public void addStudent(Student student) {
        queue.enqueue(student);
    }

    public void removeStudent() {
        queue.dequeue();
    }

    public Student getStudent(Student student) {
        return queue.display(student);
    }

    // New method to get all students for table display
    public Student[] getAllStudents() {
        return queue.getAllStudents();
    }

    // Get queue size
    public int getQueueSize() {
        return queue.getSize();
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public List<Student> searchStudents(String searchText) {
        return  null;
    }
}
