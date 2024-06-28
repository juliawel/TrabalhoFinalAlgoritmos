package model;

public class TagNode {
    private String tagName;
    private int count;

    public TagNode(String tagName) {
        this.tagName = tagName;
        this.count = 1;
    }

    public void increment() {
        this.count++;
    }

    public String getTagName() {
        return tagName;
    }

    public int getCount() {
        return count;
    }
    
}
