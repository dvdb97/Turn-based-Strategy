package world.city;

import world.gameBoard.Tile;

public class City {
	
	private Tile tile;
	private SphereOfInfluence spoi;
	
	private Population population;
	
	//TODO: to implement
	//all trading related stuff
	//...
	
	//******************** contructor ***********************************
	
	public City(Population population) {
		
		this.population = population;
		
	}
	
	//******************** get & set ************************************
	
	/**
	 * this method is only useful, when called the first time
	 * 
	 * @param tile the tile this city is placed on
	 * @return true, if tile was set. false, if the tile already has been set.
	 */
	public boolean setTile(Tile tile) {
		
		if (this.tile != null) {
			return false;
		}
		
		this.tile = tile;
		spoi = new SphereOfInfluence(tile);
		return true;
		
	}
	
}