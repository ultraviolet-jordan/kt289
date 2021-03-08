package com.kt289.util.aggregation;

public class Queue {

    private final CacheableNode head;
    private CacheableNode current;

    public Queue() {
        head = new CacheableNode();
        head.next = head;
        head.previous = head;
    }

    public void push(CacheableNode node) {
        if (node.previous != null) {
            node.removeCacheable();
        }
        node.previous = head.previous;
        node.next = head;
        node.previous.next = node;
        node.next.previous = node;
    }

    public CacheableNode pop() {
        CacheableNode next = head.next;
        if (next == head) {
            return null;
        }

        next.removeCacheable();
        return next;
    }

    public CacheableNode peek() {
        CacheableNode next = head.next;
        if (next == head) {
            current = null;
            return null;
        }

        current = next.next;
        return next;
    }

    public CacheableNode getNext() {
        CacheableNode current = this.current;
        if (current == head) {
            this.current = null;
            return null;
        }

        this.current = current.next;
        return current;
    }

    public int getSize() {
        int count = 0;
        for (CacheableNode node = head.next; node != head; node = node.next) {
            count++;
        }
        return count;
    }
}
