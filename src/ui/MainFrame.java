package ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Movie Collection Manager");
        
        setSize(950, 600);
        
        // EXIT_ON_CLOSE - затвори цялата програма
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // центрира прозореца на екрана - null означава спрямо целия екран
        setLocationRelativeTo(null);

        // компонент с табове
        JTabbedPane tabs = new JTabbedPane();
        
        GenrePanel genrePanel = new GenrePanel();
        DirectorPanel directorPanel = new DirectorPanel();
        MoviePanel moviePanel = new MoviePanel();
        ReviewPanel reviewPanel = new ReviewPanel();
        SearchPanel searchPanel = new SearchPanel();
        
        // добавамя табовете
        tabs.addTab("Жанрове", genrePanel);
        tabs.addTab("Режисьори", directorPanel);
        tabs.addTab("Филми", moviePanel);
        tabs.addTab("Ревюта", reviewPanel);
        tabs.addTab("Търсене", searchPanel);
        
        // слушател за събития
        // активира се всеки път когато user-a превключи към друг таб
        // ако да, обновяваме падащите менюта с жанрове и режисьори
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedComponent() == moviePanel) {
                moviePanel.refreshCombos();
            }
        });
        
        // добавяме компонента tabs в прозореца
        add(tabs);
    }
}