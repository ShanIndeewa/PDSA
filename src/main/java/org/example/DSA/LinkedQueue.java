package org.example.DSA;

import org.example.model.Student;

public class LinkedQueue {
    private Node front, rear;
    private int size;

    public LinkedQueue() {
        this.front = this.rear = null;
        this.size = 0;
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return front == null;
    }

    // Return size
    public int getSize() {
        return size;
    }

    // Enqueue (add at rear)
    public void enqueue(Student data) {
        Node newNode = new Node(data);

        if (rear == null) {  // If queue is empty
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }

        size++;
        System.out.println(data + " enqueued");
    }

    // Dequeue (remove from front)
    public void dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty! Cannot dequeue.");
            return;
        }

        Student item = front.data;
        front = front.next;

        if (front == null) { // If queue becomes empty
            rear = null;
        }

        size--;
        System.out.println(item + " dequeued");
    }

    // Peek (front element)
    public Student peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty! Nothing to peek.");
            return null;
        }
        return front.data;
    }

    // Get all students as array for table display
    public Student[] getAllStudents() {
        if (isEmpty()) {
            return new Student[0];
        }

        Student[] students = new Student[size];
        Node current = front;
        int index = 0;

        while (current != null && index < size) {
            students[index] = current.data;
            current = current.next;
            index++;
        }

        return students;
    }

    // Display elements - Fixed version
    public Student display(Student item) {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return null;
        }

        // If item is null, return the first student
        if (item == null) {
            return front.data;
        }

        // Find the current student and return the next one
        Node current = front;
        while (current != null) {
            if (current.data.getStudentId().equals(item.getStudentId())) {
                // Found the current student, return the next one
                if (current.next != null) {
                    return current.next.data;
                } else {
                    // No more students in queue
                    return null;
                }
            }
            current = current.next;
        }

        // Student not found in queue
        return null;
    }
}
