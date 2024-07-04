package model;

public class TagHtml {
    String name;
    boolean isOpeningTag;
    boolean isSingletonTag;

    public TagHtml(String tag) {
        if (tag.startsWith("</")) {
            this.name = tag.substring(2, tag.length() - 1).toLowerCase();
            this.isOpeningTag = false;
        } else {
            int spaceIndex = tag.indexOf(' ');
            if (spaceIndex != -1) {
                this.name = tag.substring(1, spaceIndex).toLowerCase();
            } else {
                this.name = tag.substring(1, tag.length() - 1).toLowerCase();
            }
            this.isOpeningTag = true;
        }

        String[] singletons = {"meta", "base", "br", "col", "command", "embed", "hr", 
"img", "input", "link", "param", "source", "!doctype"
};
        for (String singleton : singletons) {
            if (this.name.equals(singleton)) {
                this.isSingletonTag = true;
                break;
            }
        }
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
