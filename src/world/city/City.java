package world.city;

import world.WorldManager;
import world.gameBoard.GameBoard;
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
		
		int length = WorldManager.getLengthInTiles();
		int index = tile.getIndex();
		Tile[] spoi;
		
		if (SPOIRadius == 1 && index%2 == 1) {
			
			spoi = new Tile[SPOI_R1_LENGTH];
			
			for (int i=0; i<SPOI_R1_LENGTH; i++) {
				
				spoi[i] = GameBoard.getTile( (index%length + SPOI_R1_INDICES_DX[i]) + (index/length + SPOI_R1_INDICES_DY[i])*length );
				
			}
			
		}
		
		return null;
		
	}
	
	private static final int SPOI_R1_LENGTH = 6;
	private static final int[] SPOI_R1_INDICES_DX = new int[] {1, 1, 0,-1, 0, 1};
	private static final int[] SPOI_R1_INDICES_DY = new int[] {0, 1, 1, 0,-1,-1};
	
}
