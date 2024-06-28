package model;
public class TagNode {
    String tagName;
    int count;

    public TagNode(String tagName) {
        this.tagName = tagName;
        this.count = 1;
    }

    public void increment() {
        this.count++;
    }
}
