package model;

import java.util.Arrays;

public class ContarTags {
    private TagNo[] tags;
    private int size;

    public ContarTags() {
        this.tags = new TagNo[100];
        this.size = 0;
    }

    public void addTag(TagHtml tag) {
        for (int i = 0; i < size; i++) {
            if (tags[i].getTagName().equals(tag.getName())) {
                tags[i].increment();
                return;
            }
        }
        if (size == tags.length) {
            resize();
        }
        tags[size++] = new TagNo(tag.getName());
    }

    private void resize() {
        tags = Arrays.copyOf(tags, tags.length * 2);
    }

    public TagNo[] getSortedTags() {
        TagNo[] result = Arrays.copyOf(tags, size);
        Arrays.sort(result, (a, b) -> a.getTagName().compareTo(b.getTagName()));
        return result;
    }
}
