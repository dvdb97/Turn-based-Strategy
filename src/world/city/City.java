package world.city;

import world.Tile;

public class City {
	
	private SphereOfInfluence spoi;		//TODO: spoi nicht bei city abspeichern, sondern bei bedarf sowas wie SPOI-Klasse.getSPOI(tile, radius)
	private int spoiRadius;
	
	private Population population;
	
	//TODO: to implement
	//all trading related stuff
	//...
	
	//******************** contructor ***********************************
	
	public City(Population population) {
		
		this.population = population;
		this.spoiRadius = 1;		//TODO: don't hard code
		
	}
}