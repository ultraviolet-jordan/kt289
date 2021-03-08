package com.kt289.util.aggregation;

public class Cache {

    private final CacheableNode empty;
    private final Queue retrieved;
    private final int size;
    private final HashTable hashTable;

    private int available;

    public Cache(int length) {
        this.empty = new CacheableNode();
        this.retrieved = new Queue();
        this.size = length;
        this.available = length;
        this.hashTable = new HashTable(1024);
    }

    public CacheableNode get(long key) {
        CacheableNode node = (CacheableNode) hashTable.get(key);
        if (node != null) {
            retrieved.push(node);
        }
        return node;
    }

    public void put(long key, CacheableNode node) {
        if (available == 0) {
            CacheableNode oldest = retrieved.pop();
            oldest.remove();
            oldest.removeCacheable();
            if (oldest == empty) {
                CacheableNode secondOldest = retrieved.pop();
                secondOldest.remove();
                secondOldest.removeCacheable();
            }
        } else {
            available--;
        }
        hashTable.put(node, key);
        retrieved.push(node);
    }

    public void clear() {
        do {
            CacheableNode node = retrieved.pop();
            if (node == null) {
                available = size;
                return;
            }

            node.remove();
            node.removeCacheable();
        } while (true);
    }
}
