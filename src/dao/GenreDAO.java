package dao;

import database.DBConnection;
import models.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {

    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM genres")) {

            while (rs.next()) {
                genres.add(new Genre(
                    rs.getInt("genre_id"),
                    rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    // ? - placeholder за sql injection
    public void insert(String name) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO genres (name) VALUES (?)")) {

            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int genreId, String newName) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE genres SET name = ? WHERE genre_id = ?")) {

            stmt.setString(1, newName);
            stmt.setInt(2, genreId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int genreId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM genres WHERE genre_id = ?")) {

            stmt.setInt(1, genreId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Genre> searchByName(String name) {
        List<Genre> genres = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM genres WHERE name LIKE ?")) {

        	// % означава каквото и да е, търси навсякъде в текста
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                genres.add(new Genre(
                    rs.getInt("genre_id"),
                    rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }
}