package model;

public class Stack {
    private Node top;

    public void push(HtmlTag tag) {
        top = new Node(tag, top);
    }

    public HtmlTag pop() {
        if (top == null) return null;
        HtmlTag tag = top.tag;
        top = top.next;
        return tag;
    }

    public HtmlTag peek() {
        return top == null ? null : top.tag;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
