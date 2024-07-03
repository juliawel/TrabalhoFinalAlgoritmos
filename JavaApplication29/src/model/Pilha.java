package model;

import java.util.EmptyStackException;

public class Pilha {
    private TagHtml[] elements;
    private int top;

    public Pilha(int capacity) {
        elements = new TagHtml[capacity];
        top = -1;
    }

    public void push(TagHtml tag) {
        elements[++top] = tag;
    }

    public TagHtml pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements[top--];
    }

    public TagHtml peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
