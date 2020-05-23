package world.city;

import utils.TileSurrounding;
import world.GameBoard;
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
	
	//*******************************************************************
	
	private Tile[] getSPOI() {
		
		int[] indices = TileSurrounding.getSurrounding(GameBoard.getTile(this).getIndex(), spoiRadius);
		Tile[] spoi = new Tile[indices.length];
		for (int i=0; i<indices.length; i++) {
			spoi[i] = GameBoard.getTile(indices[i]);
		}
		return spoi;
	}
	
}