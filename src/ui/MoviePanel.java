package ui;

import dao.MovieDAO;
import dao.GenreDAO;
import dao.DirectorDAO;
import models.Movie;
import models.Genre;
import models.Director;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MoviePanel extends JPanel {

    private MovieDAO movieDAO = new MovieDAO();
    private GenreDAO genreDAO = new GenreDAO();
    private DirectorDAO directorDAO = new DirectorDAO();
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> genreCombo;
    private JComboBox<String> directorCombo;
    private JComboBox<String> editGenreCombo;
    private JComboBox<String> editDirectorCombo;
    private List<Genre> genreList;
    private List<Director> directorList;

    public MoviePanel() {
        setLayout(new BorderLayout());

        // --- ТАБЛИЦА ---
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Заглавие", "Година", "Рейтинг", "Жанр", "Режисьор"}, 0);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        add(new JScrollPane(table), BorderLayout.CENTER);
        // --- ДОЛНА ЧАСТ ---
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1));

        // Ред за търсене
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Търси");
        JButton showAllBtn = new JButton("Покажи всички");
        searchPanel.add(new JLabel("Търси по заглавие:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(showAllBtn);

        // Ред за добавяне
        JPanel insertPanel = new JPanel();
        JTextField titleField = new JTextField(10);
        JTextField yearField = new JTextField(4);
        JTextField ratingField = new JTextField(4);
        genreCombo = new JComboBox<>();
        directorCombo = new JComboBox<>();
        JButton insertBtn = new JButton("Добави");
        insertPanel.add(new JLabel("Заглавие:"));
        insertPanel.add(titleField);
        insertPanel.add(new JLabel("Година:"));
        insertPanel.add(yearField);
        insertPanel.add(new JLabel("Рейтинг:"));
        insertPanel.add(ratingField);
        insertPanel.add(new JLabel("Жанр:"));
        insertPanel.add(genreCombo);
        insertPanel.add(new JLabel("Режисьор:"));
        insertPanel.add(directorCombo);
        insertPanel.add(insertBtn);

        // Ред за редактиране
        JPanel editPanel = new JPanel();
        JTextField editTitleField = new JTextField(10);
        JTextField editYearField = new JTextField(4);
        JTextField editRatingField = new JTextField(4);
        editGenreCombo = new JComboBox<>();
        editDirectorCombo = new JComboBox<>();
        JButton updateBtn = new JButton("Редактирай");
        editPanel.add(new JLabel("Заглавие:"));
        editPanel.add(editTitleField);
        editPanel.add(new JLabel("Година:"));
        editPanel.add(editYearField);
        editPanel.add(new JLabel("Рейтинг:"));
        editPanel.add(editRatingField);
        editPanel.add(new JLabel("Жанр:"));
        editPanel.add(editGenreCombo);
        editPanel.add(new JLabel("Режисьор:"));
        editPanel.add(editDirectorCombo);
        editPanel.add(updateBtn);

        // Ред за изтриване
        JPanel deletePanel = new JPanel();
        JButton deleteBtn = new JButton("Изтрий избрания филм");
        deletePanel.add(deleteBtn);

        bottomPanel.add(searchPanel);
        bottomPanel.add(insertPanel);
        bottomPanel.add(editPanel);
        bottomPanel.add(deletePanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Зареди жанровете и режисьорите в падащите менюта
        loadCombos(genreCombo, directorCombo);
        loadCombos(editGenreCombo, editDirectorCombo);

        // --- БУТОНИ ---

        // Покажи всички
        showAllBtn.addActionListener(e -> loadData());

        // Търсене
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                loadData();
            } else {
                List<Movie> results = movieDAO.searchByTitle(keyword);
                tableModel.setRowCount(0);
                for (Movie m : results) {
                    tableModel.addRow(new Object[]{
                        m.getMovieId(),
                        m.getTitle(),
                        m.getReleaseYear(),
                        m.getRating(),
                        getGenreName(m.getGenreId()),
                        getDirectorName(m.getDirectorId())
                    });
                }
            }
        });

        // Добави
        insertBtn.addActionListener(e -> {
            try {
            	loadCombos(genreCombo, directorCombo);
            	loadCombos(editGenreCombo, editDirectorCombo);
                String title = titleField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                double rating = Double.parseDouble(ratingField.getText().trim());
                int genreId = genreList.get(genreCombo.getSelectedIndex()).getGenreId();
                int directorId = directorList.get(directorCombo.getSelectedIndex()).getDirectorId();

                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Въведи заглавие!");
                    return;
                }

                movieDAO.insert(title, year, rating, genreId, directorId);
                titleField.setText("");
                yearField.setText("");
                ratingField.setText("");
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Годината и рейтингът трябва да са числа!");
            }
        });

        // Редактирай
        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Избери филм от таблицата!");
                return;
            }
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String title = editTitleField.getText().trim();
                int year = Integer.parseInt(editYearField.getText().trim());
                double rating = Double.parseDouble(editRatingField.getText().trim());
                int genreId = genreList.get(editGenreCombo.getSelectedIndex()).getGenreId();
                int directorId = directorList.get(editDirectorCombo.getSelectedIndex()).getDirectorId();

                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Въведи заглавие!");
                    return;
                }

                movieDAO.update(id, title, year, rating, genreId, directorId);
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Годината и рейтингът трябва да са числа!");
            }
        });

        // Изтрий
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Избери филм от таблицата!");
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Сигурен ли си?");
            if (confirm == JOptionPane.YES_OPTION) {
                movieDAO.delete(id);
                loadData();
            }
        });

        loadCombos(genreCombo, directorCombo);
        loadCombos(editGenreCombo, editDirectorCombo);
        loadData();    
      }

    // Зарежда жанровете и режисьорите в падащите менюта
    private void loadCombos(JComboBox<String> gCombo, JComboBox<String> dCombo) {
        genreList = genreDAO.getAll();
        directorList = directorDAO.getAll();
        gCombo.removeAllItems();
        dCombo.removeAllItems();
        for (Genre g : genreList) gCombo.addItem(g.getName());
        for (Director d : directorList) dCombo.addItem(d.getName());
    }

    // Връща името на жанра по ID
    private String getGenreName(int genreId) {
        for (Genre g : genreList) {
            if (g.getGenreId() == genreId) return g.getName();
        }
        return "Неизвестен";
    }

    // Връща името на режисьора по ID
    private String getDirectorName(int directorId) {
        for (Director d : directorList) {
            if (d.getDirectorId() == directorId) return d.getName();
        }
        return "Неизвестен";
    }

    private void loadData() {
    	tableModel.setRowCount(0);
        genreList = genreDAO.getAll();
        directorList = directorDAO.getAll();
        loadCombos(genreCombo, directorCombo);
        loadCombos(editGenreCombo, editDirectorCombo);
        List<Movie> movies = movieDAO.getAll();
        for (Movie m : movies) {
            tableModel.addRow(new Object[]{
                m.getMovieId(),
                m.getTitle(),
                m.getReleaseYear(),
                m.getRating(),
                getGenreName(m.getGenreId()),
                getDirectorName(m.getDirectorId())
            });
        }
    }
    
    public void refreshCombos() {
        loadCombos(genreCombo, directorCombo);
        loadCombos(editGenreCombo, editDirectorCombo);
    }
}