package models;

import java.sql.Date;

public class Review {
	private int reviewId;
    private int movieId;
    private String reviewText;
    private double userRating;
    private Date watchDate;

    public Review(int reviewId, int movieId, String reviewText, double userRating, Date watchDate) {
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.reviewText = reviewText;
        this.userRating = userRating;
        this.watchDate = watchDate;
    }

    //getters
    public int getReviewId() { return reviewId; }
    public int getMovieId() { return movieId; }
    public String getReviewText() { return reviewText; }
    public double getUserRating() { return userRating; }
    public Date getWatchDate() { return watchDate; }

    //setters
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public void setUserRating(double userRating) { this.userRating = userRating; }
    public void setWatchDate(Date watchDate) { this.watchDate = watchDate; }
}