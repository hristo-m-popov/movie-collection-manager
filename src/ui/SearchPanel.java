package ui;

import dao.GenreDAO;
import dao.DirectorDAO;
import dao.MovieDAO;
import models.Genre;
import models.Director;
import models.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPanel extends JPanel {

    private GenreDAO genreDAO = new GenreDAO();
    private DirectorDAO directorDAO = new DirectorDAO();
    private MovieDAO movieDAO = new MovieDAO();
    private DefaultTableModel tableModel;
    private JComboBox<String> genreCombo;
    private JComboBox<String> directorCombo;
    private List<Genre> genreList;
    private List<Director> directorList;

    public SearchPanel() {
        setLayout(new BorderLayout());

        // --- ТАБЛИЦА ---
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Заглавие", "Година", "Рейтинг", "Жанр", "Режисьор"}, 0);
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        add(new JScrollPane(table), BorderLayout.CENTER);
        // --- ГОРНА ЧАСТ --- 
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("Търсене по 2 критерия"));

        genreCombo = new JComboBox<>();
        directorCombo = new JComboBox<>();
        JButton searchBtn = new JButton("Търси");
        JButton clearBtn = new JButton("Изчисти");

        searchPanel.add(new JLabel("Жанр:"));
        searchPanel.add(genreCombo);
        searchPanel.add(new JLabel("Режисьор:"));
        searchPanel.add(directorCombo);
        searchPanel.add(searchBtn);
        searchPanel.add(clearBtn);

        add(searchPanel, BorderLayout.NORTH);

        // Зареди жанровете и режисьорите
        loadCombos();

        // --- БУТОНИ ---

        // Търси
        searchBtn.addActionListener(e -> {
            int genreId = genreList.get(genreCombo.getSelectedIndex()).getGenreId();
            int directorId = directorList.get(directorCombo.getSelectedIndex()).getDirectorId();

            // Взима всички филми и филтрира по жанр И режисьор
            List<Movie> results = movieDAO.getAll().stream()
                .filter(m -> m.getGenreId() == genreId && m.getDirectorId() == directorId)
                .collect(Collectors.toList());

            tableModel.setRowCount(0);

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Няма намерени филми!");
                return;
            }

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
        });

        // Изчисти
        clearBtn.addActionListener(e -> tableModel.setRowCount(0));
    }

    private void loadCombos() {
        genreList = genreDAO.getAll();
        directorList = directorDAO.getAll();
        for (Genre g : genreList) genreCombo.addItem(g.getName());
        for (Director d : directorList) directorCombo.addItem(d.getName());
    }

    private String getGenreName(int genreId) {
        for (Genre g : genreList) {
            if (g.getGenreId() == genreId) return g.getName();
        }
        return "Неизвестен";
    }

    private String getDirectorName(int directorId) {
        for (Director d : directorList) {
            if (d.getDirectorId() == directorId) return d.getName();
        }
        return "Неизвестен";
    }
}