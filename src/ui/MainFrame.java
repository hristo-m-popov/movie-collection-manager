package ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Заглавие на прозореца
        setTitle("Movie Collection Manager");
        
        // Размер на прозореца
        setSize(900, 600);
        
        // Затвори програмата когато се затвори прозореца
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Центрирай прозореца на екрана
        setLocationRelativeTo(null);

        // JTabbedPane = компонент с табове (като табове в браузър)
        JTabbedPane tabs = new JTabbedPane();
        
        GenrePanel genrePanel = new GenrePanel();
        DirectorPanel directorPanel = new DirectorPanel();
        MoviePanel moviePanel = new MoviePanel();
        ReviewPanel reviewPanel = new ReviewPanel();
        SearchPanel searchPanel = new SearchPanel();
        
        // Добавяме таб за всяка таблица
        tabs.addTab("Жанрове", genrePanel);
        tabs.addTab("Режисьори", directorPanel);
        tabs.addTab("Филми", moviePanel);
        tabs.addTab("Ревюта", reviewPanel);
        tabs.addTab("Търсене", searchPanel);
        
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedComponent() == moviePanel) {
                moviePanel.refreshCombos();
            }
        });

        // Добавяме табовете към прозореца
        add(tabs);
    }
}