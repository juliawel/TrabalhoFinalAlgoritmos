package model;

public class TagNo {
    String tagName;
    int count;

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

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
}
