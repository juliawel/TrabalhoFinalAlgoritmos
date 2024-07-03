package model;

public class TagNo {
    private final String tagName;
    private int count;

    public TagNo(String tagName) {
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
