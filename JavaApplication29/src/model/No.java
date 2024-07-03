package model;

public class No {
    TagHtml tag;
    No next;

    public No(TagHtml tag, No next) {
        this.tag = tag;
        this.next = next;
    }
}
