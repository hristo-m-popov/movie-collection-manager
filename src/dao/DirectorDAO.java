package dao;

import database.DBConnection;
import models.Director;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO {

    // Взима всички режисьори от базата данни
    public List<Director> getAll() {
        List<Director> directors = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM directors")) {

            while (rs.next()) {
                directors.add(new Director(
                    rs.getInt("director_id"),
                    rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directors;
    }

    // Добавя нов режисьор
    public void insert(String name) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO directors (name) VALUES (?)")) {

            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Променя името на режисьор
    public void update(int directorId, String newName) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE directors SET name = ? WHERE director_id = ?")) {

            stmt.setString(1, newName);
            stmt.setInt(2, directorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Изтрива режисьор по ID
    public void delete(int directorId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM directors WHERE director_id = ?")) {

            stmt.setInt(1, directorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Търси режисьор по име
    public List<Director> searchByName(String name) {
        List<Director> directors = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM directors WHERE name LIKE ?")) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                directors.add(new Director(
                    rs.getInt("director_id"),
                    rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directors;
    }
}