package main;

public class Stack<T> {

    private class Node {
        T data;
        Node next;
        Node(T val) { data = val; next = null; }
    }


    private Node top;

    public Stack() {
        top = null;
    }

    public void push(T val) {
        Node newNode = new Node(val);
        newNode.next = top;
        top = newNode;
    }


    public T pop() {
        if (empty()) return null;
        T val = top.data;
        top = top.next;
        return val;
    }


    public boolean empty() {
        return top == null;
    }

}
