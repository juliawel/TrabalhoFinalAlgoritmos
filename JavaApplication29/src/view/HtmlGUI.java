package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import model.*;

public class HtmlGUI extends JFrame {
    private JTextField filePathField;
    private JTextArea resultArea;
    private JTable tagTable;
    private DefaultTableModel tableModel;

    public HtmlGUI() {
        setTitle("Validador HTML");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel filePathLabel = new JLabel("Arquivo:");
        filePathField = new JTextField();
        JButton analyzeButton = new JButton("Analisar");
        JButton browseButton = new JButton("Procurar");

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(browseButton, BorderLayout.WEST);
        buttonPanel.add(analyzeButton, BorderLayout.EAST);

        topPanel.add(filePathLabel, BorderLayout.WEST);
        topPanel.add(filePathField, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

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

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

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

        Pilha stack = new Pilha(100); // Capacidade inicial da pilha definida como 100
        ContarTags contador = new ContarTags();

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

                    TagHtml tag = new TagHtml(line.substring(openTagStart, openTagEnd + 1));
                    if (tag.isOpeningTag() && !tag.isSingletonTag()) {
                        stack.push(tag);
                    } else if (!tag.isOpeningTag()) {
                        if (stack.isEmpty()) {
                            resultArea.setText("Erro: tag final inesperada " + tag.getName());
                            return;
                        }
                        TagHtml openTag = stack.pop();
                        if (!tag.matches(openTag)) {
                            resultArea.setText("Erro: esperava " + openTag.getName() + " mas encontrou " + tag.getName());
                            return;
                        }
                    }
                    if (tag.isOpeningTag() || tag.isSingletonTag()) {
                        contador.addTag(tag);
                    }

                    start = openTagEnd + 1;
                }
            }
        } catch (IOException ex) {
            resultArea.setText("Erro ao ler o arquivo: " + ex.getMessage());
            return;
        }

        if (!stack.isEmpty()) {
            resultArea.setText("Erro: faltam tags finais.");
            while (!stack.isEmpty()) {
                TagHtml openTag = stack.pop();
                resultArea.append("\nEsperava " + openTag.getName());
            }
            return;
        }

        resultArea.setText("O arquivo está bem formatado.");
        String[] tagsOrdenadas = contador.getSortedTags();
        for (String tagName : tagsOrdenadas) {
            tableModel.addRow(new Object[]{tagName, contador.getCount(tagName)});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HtmlGUI gui = new HtmlGUI();
            gui.setVisible(true);
        });
    }
}
