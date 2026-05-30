package database;


import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {
	public static void initializeDatabase() {

        try (Connection connection =
                     DBConnection.getConnection();

        	// изпраща SQL команди към базата
             Statement statement =
                     connection.createStatement()) {

            String createGenresTable = """
                    CREATE TABLE IF NOT EXISTS genres (
                        genre_id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL UNIQUE
                    );
                    """;

            String createDirectorsTable = """
                    CREATE TABLE IF NOT EXISTS directors (
                        director_id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    );
                    """;

            String createMoviesTable = """
                    CREATE TABLE IF NOT EXISTS movies (
                        movie_id INT AUTO_INCREMENT PRIMARY KEY,
                        
                        title VARCHAR(200) NOT NULL,
                        
                        release_year INT,
                        
                        rating DECIMAL(2,1),

                        genre_id INT NOT NULL,
                        
                        director_id INT NOT NULL,

                        FOREIGN KEY (genre_id)
                            REFERENCES genres(genre_id),

                        FOREIGN KEY (director_id)
                            REFERENCES directors(director_id)
                    );
                    """;

            String createReviewsTable = """
                    CREATE TABLE IF NOT EXISTS reviews (
                        review_id INT AUTO_INCREMENT PRIMARY KEY,

                        movie_id INT NOT NULL,

                        review_text VARCHAR(1000),

                        user_rating DECIMAL(2,1),

                        watch_date DATE,

                        FOREIGN KEY (movie_id)
                            REFERENCES movies(movie_id)
                            ON DELETE CASCADE
                    );
                    """;
            
            // изпращаме командите към БД
            statement.execute(createGenresTable);
            statement.execute(createDirectorsTable);
            statement.execute(createMoviesTable);
            statement.execute(createReviewsTable);

            System.out.println("Database initialized successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}