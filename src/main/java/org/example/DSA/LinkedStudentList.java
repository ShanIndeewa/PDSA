package org.example.DSA;

import org.example.model.Student;

public class LinkedStudentList {
    private DoublyNode head;
    private DoublyNode tail;
    private int size;

    public LinkedStudentList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Add a student to the beginning of the list
     */
    public void addFirst(Student student) {
        DoublyNode newNode = new DoublyNode(student);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * Add a student to the end of the list
     */
    public void addLast(Student student) {
        DoublyNode newNode = new DoublyNode(student);

        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Add a student at a specific index
     */
    public void add(int index, Student student) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            addFirst(student);
            return;
        }

        if (index == size) {
            addLast(student);
            return;
        }

        DoublyNode newNode = new DoublyNode(student);
        DoublyNode current = getNodeAt(index);

        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    /**
     * Remove and return the first student
     */
    public Student removeFirst() {
        if (head == null) {
            return null;
        }

        Student removedStudent = head.data;

        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return removedStudent;
    }

    /**
     * Remove and return the last student
     */
    public Student removeLast() {
        if (tail == null) {
            return null;
        }

        Student removedStudent = tail.data;

        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return removedStudent;
    }

    /**
     * Remove student at specific index
     */
    public Student remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        DoublyNode current = getNodeAt(index);
        Student removedStudent = current.data;

        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return removedStudent;
    }

    /**
     * Remove student by student ID
     */
    public boolean removeByStudentId(String studentId) {
        DoublyNode current = head;

        while (current != null) {
            if (current.data.getStudentId().equals(studentId)) {
                if (current == head && current == tail) {
                    head = tail = null;
                } else if (current == head) {
                    head = head.next;
                    head.prev = null;
                } else if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Get student at specific index (optimized for doubly linked list)
     */
    public Student get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        return getNodeAt(index).data;
    }

    /**
     * Helper method to get node at specific index (optimized traversal)
     */
    private DoublyNode getNodeAt(int index) {
        DoublyNode current;

        // Optimize traversal by choosing direction based on index position
        if (index < size / 2) {
            // Traverse from head
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            // Traverse from tail
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    /**
     * Find student by student ID
     */
    public Student findByStudentId(String studentId) {
        DoublyNode current = head;
        while (current != null) {
            if (current.data.getStudentId().equals(studentId)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Check if the list contains a student with given ID
     */
    public boolean contains(String studentId) {
        return findByStudentId(studentId) != null;
    }

    /**
     * Get the index of student by student ID
     */
    public int indexOf(String studentId) {
        DoublyNode current = head;
        int index = 0;

        while (current != null) {
            if (current.data.getStudentId().equals(studentId)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Get the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Check if the list is empty
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clear all students from the list
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Convert the linked list to an array
     */
    public Student[] toArray() {
        Student[] array = new Student[size];
        DoublyNode current = head;
        int index = 0;

        while (current != null) {
            array[index] = current.data;
            current = current.next;
            index++;
        }
        return array;
    }

    /**
     * Display all students in the list (forward direction)
     */
    public void display() {
        DoublyNode current = head;
        System.out.print("LinkedStudentList (Forward): ");

        while (current != null) {
            System.out.print(current.data.getStudentId() + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }

    /**
     * Display all students in reverse order
     */
    public void displayReverse() {
        DoublyNode current = tail;
        System.out.print("LinkedStudentList (Reverse): ");

        while (current != null) {
            System.out.print(current.data.getStudentId() + " <-> ");
            current = current.prev;
        }
        System.out.println("null");
    }

    /**
     * Get detailed string representation of all students
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DoublyLinkedStudentList [size=").append(size).append("]\n");

        DoublyNode current = head;
        int index = 0;

        while (current != null) {
            sb.append("[").append(index).append("] ")
              .append(current.data.getStudentId()).append(" - ")
              .append(current.data.getFullName()).append("\n");
            current = current.next;
            index++;
        }
        return sb.toString();
    }
}
