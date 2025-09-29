package org.example.DSA;

import org.example.model.Student;

public class DoublyNode {
    Student data;
    DoublyNode next;
    DoublyNode prev;

    DoublyNode(Student data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
