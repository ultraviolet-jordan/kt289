package com.kt289.util.aggregation;

class HashTable {

    private int size;
    private Node[] entries;

    public HashTable(int size) {
        this.size = size;
        this.entries = new Node[size];
        for (int index = 0; index < size; index++) {
            Node entry = entries[index] = new Node();
            entry.nextNode = entry;
            entry.previousNode = entry;
        }
    }

    public Node get(long key) {
        Node current = entries[(int) (key & size - 1)];
        for (Node next = current.nextNode; next != current; next = next.nextNode) {
            if (next.nodeId == key) {
                return next;
            }
        }
        return null;
    }

    public void put(Node node, long l) {
        if (node.previousNode != null) {
            node.remove();
        }
        Node current = entries[(int) (l & size - 1)];
        node.previousNode = current.previousNode;
        node.nextNode = current;
        node.previousNode.nextNode = node;
        node.nextNode.previousNode = node;
        node.nodeId = l;
    }
}
