package model;

public class Pilha {
    No top;

    public void push(TagHtml tag) {
        No newNode = new No(tag, top);
        top = newNode;
    }

    public TagHtml pop() {
        if (top == null) return null;
        TagHtml tag = top.tag;
        top = top.next;
        return tag;
    }

    public TagHtml peek() {
        if (top == null) return null;
        return top.tag;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
