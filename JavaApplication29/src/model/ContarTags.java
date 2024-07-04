package model;

public class ContarTags {
    TagNo[] tags;
    int size;

    public ContarTags() {
        tags = new TagNo[100];
        size = 0;
    }

    public void addTag(TagHtml tag) {
        for (int i = 0; i < size; i++) {
            if (tags[i].tagName.equals(tag.name)) {
                tags[i].increment();
                return;
            }
        }
        if (size == tags.length) {
            resize();
        }
        tags[size++] = new TagNo(tag.name);
    }

    private void resize() {
        TagNo[] newTags = new TagNo[tags.length * 2];
        System.arraycopy(tags, 0, newTags, 0, tags.length);
        tags = newTags;
    }

    public TagNo[] getSortedTags() {
        TagNo[] result = new TagNo[size];
        System.arraycopy(tags, 0, result, 0, size);
        java.util.Arrays.sort(result, (a, b) -> a.tagName.compareTo(b.tagName));
        return result;
    }
}
