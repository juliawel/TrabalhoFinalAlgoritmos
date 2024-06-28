package model;

public class Stack {
    Node top;

    public void push(HtmlTag tag) {
        Node newNode = new Node(tag, top);
        top = newNode;
    }

    public HtmlTag pop() {
        if (top == null) return null;
        HtmlTag tag = top.tag;
        top = top.next;
        return tag;
    }

    public HtmlTag peek() {
        if (top == null) return null;
        return top.tag;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
