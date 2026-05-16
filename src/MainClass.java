import database.DBInitializer;
import ui.MainFrame;

public class MainClass {

	public static void main(String[] args) {
		DBInitializer.initializeDatabase();
		
		new MainFrame().setVisible(true);
	}
}