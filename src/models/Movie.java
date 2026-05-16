package models;

public class Movie {
	private int movieId;
	private String title;
	private int releaseYear;
	private double rating;
	private int genreId;
	private int directorId;
	
	public Movie(int movieId, String title, int releaseYear, double rating, int genreId, int directorId) {
		this.movieId = movieId;
		this.title = title;
		this.releaseYear = releaseYear;
		this.rating = rating;
		this.genreId = genreId;
		this.directorId = directorId;
	}
	
	//getters
	public int getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }
    public double getRating() { return rating; }
    public int getGenreId() { return genreId; }
    public int getDirectorId() { return directorId; }

	
	//setters
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public void setTitle(String title) { this.title = title; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public void setRating(double rating) { this.rating = rating; }
    public void setGenreId(int genreId) { this.genreId = genreId; }
    public void setDirectorId(int directorId) { this.directorId = directorId; }

	
}