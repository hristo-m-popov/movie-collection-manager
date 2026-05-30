import database.DBInitializer;
import ui.MainFrame;

public class MainClass {

	public static void main(String[] args) {
		// създаваме таблиците в БД
		DBInitializer.initializeDatabase();
		
		// създаваме главния прозорец
		// показваме го на екрана
		new MainFrame().setVisible(true);
	}
}