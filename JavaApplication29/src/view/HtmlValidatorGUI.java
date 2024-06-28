package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import model.*;

public class HtmlValidatorGUI extends JFrame {
    private JTextField filePathField;
    private JTextArea resultArea;
    private JTable tagTable;
    private DefaultTableModel tableModel;

    public HtmlValidatorGUI() {
        setTitle("HTML Validator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel filePathLabel = new JLabel("Arquivo:");
        filePathField = new JTextField();
        JButton analyzeButton = new JButton("Analisar");

        topPanel.add(filePathLabel, BorderLayout.WEST);
        topPanel.add(filePathField, BorderLayout.CENTER);
        topPanel.add(analyzeButton, BorderLayout.EAST);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        String[] columnNames = {"Tag", "Número de ocorrências"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tagTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(tagTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, resultScrollPane, tableScrollPane);
        splitPane.setResizeWeight(0.5);

        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeHtmlFile();
            }
        });
    }

    private void analyzeHtmlFile() {
        String filePath = filePathField.getText();
        resultArea.setText("");
        tableModel.setRowCount(0);

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
                    if (tag.isOpeningTag() && !tag.isSingletonTag()) {
                        stack.push(tag);
                    } else if (!tag.isOpeningTag()) {
                        if (stack.isEmpty()) {
                            resultArea.setText("Erro: tag final inesperada " + tag.getName());
                            return;
                        }
                        HtmlTag openTag = stack.pop();
                        if (!tag.matches(openTag)) {
                            resultArea.setText("Erro: esperava " + openTag.getName() + " mas encontrou " + tag.getName());
                            return;
                        }
                    }
                    if (tag.isOpeningTag() || tag.isSingletonTag()) {
                        counter.addTag(tag);
                    }

                    start = openTagEnd + 1;
                }
            }
        } catch (IOException e) {
            resultArea.setText("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        if (!stack.isEmpty()) {
            resultArea.setText("Erro: faltam tags finais.");
            while (!stack.isEmpty()) {
                HtmlTag openTag = stack.pop();
                resultArea.append("\nEsperava " + openTag.getName());
            }
            return;
        }

        resultArea.setText("O arquivo está bem formatado.");
        TagNode[] sortedTags = counter.getSortedTags();
        for (TagNode tagNode : sortedTags) {
            tableModel.addRow(new Object[]{tagNode.tagName, tagNode.count});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HtmlValidatorGUI validatorGUI = new HtmlValidatorGUI();
            validatorGUI.setVisible(true);
        });
    }
}
