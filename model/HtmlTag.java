package model;
public class HtmlTag {
    String name;
    boolean isOpeningTag;
    boolean isSingletonTag;

    public HtmlTag(String tag) {
        // Remover <> e verificar se é uma tag final
        if (tag.startsWith("</")) {
            this.name = tag.substring(2, tag.length() - 1).toLowerCase();
            this.isOpeningTag = false;
        } else {
            // Remover atributos
            int spaceIndex = tag.indexOf(' ');
            if (spaceIndex != -1) {
                this.name = tag.substring(1, spaceIndex).toLowerCase();
            } else {
                this.name = tag.substring(1, tag.length() - 1).toLowerCase();
            }
            this.isOpeningTag = !this.name.startsWith("/");
        }
        
        // Verificar se é uma singleton tag
        String[] singletons = {"meta", "base", "br", "col", "command", "embed", "hr", "img", "input", "link", "param", "source", "!doctype"};
        for (String singleton : singletons) {
            if (this.name.equals(singleton)) {
                this.isSingletonTag = true;
                break;
            }
        }
    }

    public boolean matches(HtmlTag other) {
        return this.name.equals(other.name) && this.isOpeningTag != other.isOpeningTag;
    }
}
