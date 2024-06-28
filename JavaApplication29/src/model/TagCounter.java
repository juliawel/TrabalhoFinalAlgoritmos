package model;

public class TagCounter {
    TagNode[] tags;
    int size;

    public TagCounter() {
        tags = new TagNode[100];
        size = 0;
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
        TagNode[] newTags = new TagNode[tags.length * 2];
        System.arraycopy(tags, 0, newTags, 0, tags.length);
        tags = newTags;
    }

    public TagNode[] getSortedTags() {
        TagNode[] result = new TagNode[size];
        System.arraycopy(tags, 0, result, 0, size);
        // Ordenar por nome
        java.util.Arrays.sort(result, (a, b) -> a.getTagName().compareTo(b.getTagName()));
        return result;
    }
}
