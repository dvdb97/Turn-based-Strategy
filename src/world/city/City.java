package world.city;

import world.gameBoard.Tile;

public class City {
	
	private Tile tile;
	private Tile[] SPOI;	//sphereofinfluence
	private int SPOIRadius;
	
	private Population population;
	
	//TODO: to implement
	//all trading related stuff
	//...
	
	//******************** contructor ***********************************
	
	public City(Population population, int SPOIRadius) {
		
		this.population = population;
		this.SPOIRadius = SPOIRadius;
	}
	
	//******************** get & set ************************************
	
	/**
	 * this method is only useful, when called the first time
	 * 
	 * @param tile the tile this city is placed on
	 * @return true, if tile was set. false, if the tile already has been set.
	 */
	public boolean setTile(Tile tile) {
		
		if(tile != null) {
			return false;
		}
		
		this.tile = tile;
		SPOI = getSPOI(tile, SPOIRadius);
		return true;
		
	}
	
	
	//TODO:
	private Tile[] getSPOI(Tile tile, int SPOIRadius) {
		//not implemented yet
		return null;
		
	}
	
}
