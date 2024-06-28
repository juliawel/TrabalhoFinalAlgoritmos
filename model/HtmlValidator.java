package model;
import java.io.*;

public class HtmlValidator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java HtmlValidator <caminho_do_arquivo>");
            return;
        }
        String filePath = args[0];
        validateHtml(filePath);
    }

    public static void validateHtml(String filePath) {
        Stack stack = new Stack();
        TagCounter counter = new TagCounter();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                int start = 0;
                while (start < line.length()) {
                    int openTagStart = line.indexOf('<', start);
                    if (openTagStart == -1) break;
                    int openTagEnd = line.indexOf('>', openTagStart);
                    if (openTagEnd == -1) break;

                    HtmlTag tag = new HtmlTag(line.substring(openTagStart, openTagEnd + 1));
                    if (tag.isOpeningTag && !tag.isSingletonTag) {
                        stack.push(tag);
                    } else if (!tag.isOpeningTag) {
                        if (stack.isEmpty()) {
                            System.out.println("Erro: tag final inesperada " + tag.name);
                            return;
                        }
                        HtmlTag openTag = stack.pop();
                        if (!tag.matches(openTag)) {
                            System.out.println("Erro: esperava " + openTag.name + " mas encontrou " + tag.name);
                            return;
                        }
                    }
                    if (!tag.isOpeningTag || !tag.isSingletonTag) {
                        counter.addTag(tag);
                    }

                    start = openTagEnd + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        if (!stack.isEmpty()) {
            System.out.println("Erro: faltam tags finais.");
            while (!stack.isEmpty()) {
                HtmlTag openTag = stack.pop();
                System.out.println("Esperava " + openTag.name);
            }
            return;
        }

        System.out.println("Arquivo bem formatado.");
        TagNode[] sortedTags = counter.getSortedTags();
        for (TagNode tagNode : sortedTags) {
            System.out.println(tagNode.tagName + ": " + tagNode.count);
        }
    }
}
