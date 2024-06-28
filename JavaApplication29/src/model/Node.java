package model;

public class Node {
    HtmlTag tag;
    Node next;

    public Node(HtmlTag tag, Node next) {
        this.tag = tag;
        this.next = next;
    }
}
