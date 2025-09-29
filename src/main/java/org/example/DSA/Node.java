package org.example.DSA;

import org.example.model.Student;

public class Node {
    Student data;
    Node next;

    Node(Student data) {
        this.data = data;
        this.next = null;
    }
}
