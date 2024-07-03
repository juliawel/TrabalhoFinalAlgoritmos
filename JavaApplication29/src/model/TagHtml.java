package model;

public class TagHtml {
    private String name;
    private boolean isOpeningTag;
    private boolean isSingletonTag;

    public TagHtml(String tag) {
        this.isOpeningTag = !tag.startsWith("</");
        this.name = extractTagName(tag);
        this.isSingletonTag = isSingleton(name);
    }

    private String extractTagName(String tag) {
        int start = tag.startsWith("</") ? 2 : 1;
        int end = tag.indexOf(' ') > -1 ? tag.indexOf(' ') : tag.length() - 1;
        return tag.substring(start, end).toLowerCase();
    }

    private boolean isSingleton(String name) {
        String[] singletons = {"meta", "base", "br", "col", "command", "embed", "hr", "img", "input", "link", "param", "source", "!doctype"};
        for (String singleton : singletons) {
            if (name.equals(singleton)) {
                return true;
            }
        }
        return false;
    }

    public boolean matches(TagHtml other) {
        return this.name.equals(other.name) && this.isOpeningTag != other.isOpeningTag;
    }

    public String getName() {
        return name;
    }

    public boolean isOpeningTag() {
        return isOpeningTag;
    }

    public boolean isSingletonTag() {
        return isSingletonTag;
    }
}
