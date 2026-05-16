package ui;

import dao.GenreDAO;
import models.Genre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GenrePanel extends JPanel {

    private GenreDAO genreDAO = new GenreDAO();
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;

    public GenrePanel() {
        setLayout(new BorderLayout());

        // --- ТАБЛИЦА ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Име"}, 0);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        add(new JScrollPane(table), BorderLayout.CENTER);
        // --- ДОЛНА ЧАСТ ---
        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));

        // Ред за търсене
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Търси");
        JButton showAllBtn = new JButton("Покажи всички");
        searchPanel.add(new JLabel("Търси по име:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(showAllBtn);

        // Ред за добавяне
        JPanel insertPanel = new JPanel();
        JTextField nameField = new JTextField(20);
        JButton insertBtn = new JButton("Добави");
        insertPanel.add(new JLabel("Ново име:"));
        insertPanel.add(nameField);
        insertPanel.add(insertBtn);

        // Ред за редактиране и изтриване
        JPanel editPanel = new JPanel();
        JTextField editField = new JTextField(20);
        JButton updateBtn = new JButton("Редактирай");
        JButton deleteBtn = new JButton("Изтрий");
        editPanel.add(new JLabel("Ново име:"));
        editPanel.add(editField);
        editPanel.add(updateBtn);
        editPanel.add(deleteBtn);

        bottomPanel.add(searchPanel);
        bottomPanel.add(insertPanel);
        bottomPanel.add(editPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- БУТОНИ ---

        // Покажи всички
        showAllBtn.addActionListener(e -> loadData());

        // Търсене
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                loadData();
            } else {
                List<Genre> results = genreDAO.searchByName(keyword);
                tableModel.setRowCount(0);
                for (Genre g : results) {
                    tableModel.addRow(new Object[]{g.getGenreId(), g.getName()});
                }
            }
        });

        // Добави
        insertBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Въведи име!");
                return;
            }
            genreDAO.insert(name);
            nameField.setText("");
            loadData();
        });

        // Редактирай
        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Избери ред от таблицата!");
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String newName = editField.getText().trim();
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Въведи ново име!");
                return;
            }
            genreDAO.update(id, newName);
            editField.setText("");
            loadData();
        });

        // Изтрий
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Избери ред от таблицата!");
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Сигурен ли си?");
            if (confirm == JOptionPane.YES_OPTION) {
                genreDAO.delete(id);
                loadData();
            }
        });

        // Зареди данните при отваряне
        loadData();
    }

    // Зарежда всички жанрове в таблицата
    private void loadData() {
        tableModel.setRowCount(0);
        List<Genre> genres = genreDAO.getAll();
        for (Genre g : genres) {
            tableModel.addRow(new Object[]{g.getGenreId(), g.getName()});
        }
    }
}