package dao;

import database.DBConnection;
import models.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    // Взима всички филми от базата данни
    public List<Movie> getAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {

            while (rs.next()) {
                movies.add(new Movie(
                    rs.getInt("movie_id"),
                    rs.getString("title"),
                    rs.getInt("release_year"),
                    rs.getDouble("rating"),
                    rs.getInt("genre_id"),
                    rs.getInt("director_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Добавя нов филм
    public void insert(String title, int releaseYear, double rating, int genreId, int directorId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO movies (title, release_year, rating, genre_id, director_id) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, title);
            stmt.setInt(2, releaseYear);
            stmt.setDouble(3, rating);
            stmt.setInt(4, genreId);
            stmt.setInt(5, directorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Променя филм
    public void update(int movieId, String title, int releaseYear, double rating, int genreId, int directorId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE movies SET title = ?, release_year = ?, rating = ?, genre_id = ?, director_id = ? WHERE movie_id = ?")) {

            stmt.setString(1, title);
            stmt.setInt(2, releaseYear);
            stmt.setDouble(3, rating);
            stmt.setInt(4, genreId);
            stmt.setInt(5, directorId);
            stmt.setInt(6, movieId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Изтрива филм по ID
    public void delete(int movieId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM movies WHERE movie_id = ?")) {

            stmt.setInt(1, movieId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Търси филм по заглавие
    public List<Movie> searchByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM movies WHERE title LIKE ?")) {

            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(new Movie(
                    rs.getInt("movie_id"),
                    rs.getString("title"),
                    rs.getInt("release_year"),
                    rs.getDouble("rating"),
                    rs.getInt("genre_id"),
                    rs.getInt("director_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}