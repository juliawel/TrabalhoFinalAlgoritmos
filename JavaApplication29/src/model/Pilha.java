package model;

public class Pilha {
    private No top;

    public void push(TagHtml tag) {
        top = new No(tag, top);
    }

    public TagHtml pop() {
        if (top == null) return null;
        TagHtml tag = top.tag;
        top = top.next;
        return tag;
    }

    public TagHtml peek() {
        return top == null ? null : top.tag;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
