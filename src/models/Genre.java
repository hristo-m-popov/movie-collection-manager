package models;

public class Genre {
	private int genreId;
	private String name;
	
	public Genre(int genreId, String name) {
		this.genreId = genreId;
		this.name = name;
	}
	
	//getters
	public int getGenreId() {return genreId;}
	public String getName() {return name;}
	
	//setters
	public void setGenreId(int genreId) {this.genreId = genreId;}
	public void setName(String name) {this.name = name;}
}
