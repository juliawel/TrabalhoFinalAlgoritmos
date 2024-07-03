package model;

import java.util.*;

public class ContarTags {
    private Map<String, Integer> tagCounts;

    public ContarTags() {
        tagCounts = new HashMap<>();
    }

    public void addTag(TagHtml tag) {
        String tagName = tag.getName();
        tagCounts.put(tagName, tagCounts.getOrDefault(tagName, 0) + 1);
    }

    public String[] getSortedTags() {
        String[] tags = tagCounts.keySet().toArray(new String[0]);
        Arrays.sort(tags);
        return tags;
    }

    public int getCount(String tagName) {
        return tagCounts.getOrDefault(tagName, 0);
    }
}
