package dao;

import database.DBConnection;
import models.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // Взима всички ревюта от базата данни
    public List<Review> getAll() {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM reviews")) {

            while (rs.next()) {
                reviews.add(new Review(
                    rs.getInt("review_id"),
                    rs.getInt("movie_id"),
                    rs.getString("review_text"),
                    rs.getDouble("user_rating"),
                    rs.getDate("watch_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Добавя ново ревю
    public void insert(int movieId, String reviewText, double userRating, Date watchDate) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO reviews (movie_id, review_text, user_rating, watch_date) VALUES (?, ?, ?, ?)")) {

            stmt.setInt(1, movieId);
            stmt.setString(2, reviewText);
            stmt.setDouble(3, userRating);
            stmt.setDate(4, watchDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Променя ревю
    public void update(int reviewId, String reviewText, double userRating, Date watchDate) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE reviews SET review_text = ?, user_rating = ?, watch_date = ? WHERE review_id = ?")) {

            stmt.setString(1, reviewText);
            stmt.setDouble(2, userRating);
            stmt.setDate(3, watchDate);
            stmt.setInt(4, reviewId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Изтрива ревю по ID
    public void delete(int reviewId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM reviews WHERE review_id = ?")) {

            stmt.setInt(1, reviewId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Взима всички ревюта за конкретен филм
    public List<Review> getByMovieId(int movieId) {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM reviews WHERE movie_id = ?")) {

            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(new Review(
                    rs.getInt("review_id"),
                    rs.getInt("movie_id"),
                    rs.getString("review_text"),
                    rs.getDouble("user_rating"),
                    rs.getDate("watch_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}