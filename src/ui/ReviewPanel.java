package ui;

import dao.ReviewDAO;
import dao.MovieDAO;
import models.Review;
import models.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReviewPanel extends JPanel {

    private ReviewDAO reviewDAO = new ReviewDAO();
    private MovieDAO movieDAO = new MovieDAO();
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> movieCombo;
    private List<Movie> movieList;

    public ReviewPanel() {
        setLayout(new BorderLayout());

        // --- ТАБЛИЦА ---
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Филм", "Ревю", "Рейтинг", "Дата"}, 0);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        add(new JScrollPane(table), BorderLayout.CENTER);
        // --- ДОЛНА ЧАСТ ---
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1));

        // Ред за филтриране по филм
        JPanel filterPanel = new JPanel();
        movieCombo = new JComboBox<>();
        JButton filterBtn = new JButton("Покажи ревютата за филма");
        JButton showAllBtn = new JButton("Покажи всички");
        filterPanel.add(new JLabel("Филм:"));
        filterPanel.add(movieCombo);
        filterPanel.add(filterBtn);
        filterPanel.add(showAllBtn);

        // Ред за добавяне
        JPanel insertPanel = new JPanel();
        JComboBox<String> insertMovieCombo = new JComboBox<>();
        JTextField reviewField = new JTextField(15);
        JTextField ratingField = new JTextField(4);
        JTextField dateField = new JTextField(10);
        JButton insertBtn = new JButton("Добави");
        insertPanel.add(new JLabel("Филм:"));
        insertPanel.add(insertMovieCombo);
        insertPanel.add(new JLabel("Ревю:"));
        insertPanel.add(reviewField);
        insertPanel.add(new JLabel("Рейтинг:"));
        insertPanel.add(ratingField);
        insertPanel.add(new JLabel("Дата (гггг-мм-дд):"));
        insertPanel.add(dateField);
        insertPanel.add(insertBtn);

        // Ред за редактиране
        JPanel editPanel = new JPanel();
        JTextField editReviewField = new JTextField(15);
        JTextField editRatingField = new JTextField(4);
        JTextField editDateField = new JTextField(10);
        JButton updateBtn = new JButton("Редактирай");
        editPanel.add(new JLabel("Ревю:"));
        editPanel.add(editReviewField);
        editPanel.add(new JLabel("Рейтинг:"));
        editPanel.add(editRatingField);
        editPanel.add(new JLabel("Дата (гггг-мм-дд):"));
        editPanel.add(editDateField);
        editPanel.add(updateBtn);

        // Ред за изтриване
        JPanel deletePanel = new JPanel();
        JButton deleteBtn = new JButton("Изтрий избраното ревю");
        deletePanel.add(deleteBtn);

        bottomPanel.add(filterPanel);
        bottomPanel.add(insertPanel);
        bottomPanel.add(editPanel);
        bottomPanel.add(deletePanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Зареди филмите в падащите менюта
        loadMovieCombos(movieCombo, insertMovieCombo);

        // --- БУТОНИ ---

        // Покажи всички
        showAllBtn.addActionListener(e -> loadData());

        // Филтрирай по филм
        filterBtn.addActionListener(e -> {
            int movieId = movieList.get(movieCombo.getSelectedIndex()).getMovieId();
            List<Review> results = reviewDAO.getByMovieId(movieId);
            tableModel.setRowCount(0);
            for (Review r : results) {
                tableModel.addRow(new Object[]{
                    r.getReviewId(),
                    getMovieName(r.getMovieId()),
                    r.getReviewText(),
                    r.getUserRating(),
                    r.getWatchDate()
                });
            }
        });

        // Добави
        insertBtn.addActionListener(e -> {
            try {
                int movieId = movieList.get(insertMovieCombo.getSelectedIndex()).getMovieId();
                String reviewText = reviewField.getText().trim();
                double rating = Double.parseDouble(ratingField.getText().trim());
                java.sql.Date date = java.sql.Date.valueOf(dateField.getText().trim());

                if (reviewText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Въведи ревю!");
                    return;
                }

                reviewDAO.insert(movieId, reviewText, rating, date);
                reviewField.setText("");
                ratingField.setText("");
                dateField.setText("");
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Рейтингът трябва да е число!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Датата трябва да е във формат гггг-мм-дд!");
            }
        });

        // Редактирай
        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Избери ревю от таблицата!");
                return;
            }
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String reviewText = editReviewField.getText().trim();
                double rating = Double.parseDouble(editRatingField.getText().trim());
                java.sql.Date date = java.sql.Date.valueOf(editDateField.getText().trim());

                if (reviewText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Въведи ревю!");
                    return;
                }

                reviewDAO.update(id, reviewText, rating, date);
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Рейтингът трябва да е число!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Датата трябва да е във формат гггг-мм-дд!");
            }
        });

        // Изтрий
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Избери ревю от таблицата!");
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Сигурен ли си?");
            if (confirm == JOptionPane.YES_OPTION) {
                reviewDAO.delete(id);
                loadData();
            }
        });

        loadData();
    }

    private void loadMovieCombos(JComboBox<String>... combos) {
        movieList = movieDAO.getAll();
        for (JComboBox<String> combo : combos) {
            combo.removeAllItems();
            for (Movie m : movieList) combo.addItem(m.getTitle());
        }
    }

    private String getMovieName(int movieId) {
        for (Movie m : movieList) {
            if (m.getMovieId() == movieId) return m.getTitle();
        }
        return "Неизвестен";
    }

    private void loadData() {
        tableModel.setRowCount(0);
        movieList = movieDAO.getAll();
        List<Review> reviews = reviewDAO.getAll();
        for (Review r : reviews) {
            tableModel.addRow(new Object[]{
                r.getReviewId(),
                getMovieName(r.getMovieId()),
                r.getReviewText(),
                r.getUserRating(),
                r.getWatchDate()
            });
        }
    }
}