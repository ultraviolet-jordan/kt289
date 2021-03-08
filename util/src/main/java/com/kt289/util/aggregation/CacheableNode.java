package com.kt289.util.aggregation;

public class CacheableNode extends Node {

    public CacheableNode next;
    public CacheableNode previous;

    public void removeCacheable() {
        if (previous == null) {
            return;
        }

        previous.next = next;
        next.previous = previous;
        next = null;
        previous = null;
    }
}
