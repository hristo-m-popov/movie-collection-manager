package models;

public class Director {
	private int directorId;
	private String name;
	
	public Director(int directorId, String name) {
		this.directorId = directorId;
		this.name = name;
	}
	
	//getters
	public int getDirectorId() {return directorId;}
	public String getName() {return name;}
	
	//setters
	public void setDirectorId(int directorId) {this.directorId = directorId;}
	public void setName(String name) {this.name = name;}
}
