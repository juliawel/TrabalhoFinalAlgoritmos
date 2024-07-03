package model;

import java.util.Arrays;

public class TagCounter {
    private TagNode[] tags;
    private int size;

    public TagCounter() {
        this.tags = new TagNode[100];
        this.size = 0;
    }

    public void addTag(HtmlTag tag) {
        for (int i = 0; i < size; i++) {
            if (tags[i].getTagName().equals(tag.getName())) {
                tags[i].increment();
                return;
            }
        }
        if (size == tags.length) {
            resize();
        }
        tags[size++] = new TagNode(tag.getName());
    }

    private void resize() {
        tags = Arrays.copyOf(tags, tags.length * 2);
    }

    public TagNode[] getSortedTags() {
        TagNode[] result = Arrays.copyOf(tags, size);
        Arrays.sort(result, (a, b) -> a.getTagName().compareTo(b.getTagName()));
        return result;
    }
}
