package com.kt289.util.aggregation;

public class LinkedList {

    private final Node tail;
    private Node current;

    public LinkedList() {
        this.tail = new Node();
        this.tail.nextNode = tail;
        this.tail.previousNode = tail;
    }

    public void pushBack(Node node) {
        if (node.previousNode != null) {
            node.remove();
        }
        node.previousNode = tail.previousNode;
        node.nextNode = tail;
        node.previousNode.nextNode = node;
        node.nextNode.previousNode = node;
    }

    public void pushFront(Node node) {
        if (node.previousNode != null) {
            node.remove();
        }
        node.previousNode = tail;
        node.nextNode = tail.nextNode;
        node.previousNode.nextNode = node;
        node.nextNode.previousNode = node;
    }

    public Node pop() {
        Node next = tail.nextNode;
        if (next == tail) {
            return null;
        }

        next.remove();
        return next;
    }

    public Node peekFront() {
        Node next = tail.nextNode;
        if (next == tail) {
            current = null;
            return null;
        }

        current = next.nextNode;
        return next;
    }

    public Node peekBack() {
        Node previous = tail.previousNode;
        if (previous == tail) {
            current = null;
            return null;
        }

        current = previous.previousNode;
        return previous;
    }

    public Node getNext() {
        Node current = this.current;
        if (current == tail) {
            this.current = null;
            return null;
        }

        this.current = current.nextNode;
        return current;
    }

    public Node getPrevious() {
        Node current = this.current;
        if (current == tail) {
            this.current = null;
            return null;
        }

        this.current = current.previousNode;
        return current;
    }

    public void clear() {
        if (tail.nextNode == tail) {
            return;
        }

        do {
            Node next = tail.nextNode;
            if (next == tail) {
                return;
            }
            next.remove();
        } while (true);
    }
}
