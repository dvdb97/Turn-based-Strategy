package interaction.tileSelection;

import elements.Tile;
import interaction.input.CursorPosInput;

public class TileSelecter {
	
	
	private static Tile[] tiles;
	
	
	private static Tile selectedTile;
	
	
	public static void init(Tile[] generatedTiles) {
		
		tiles = generatedTiles;
		
	}
	
	
	public static void update() {
		
		
		
	}
	
	
	public static int computeSelectedTileIndex() {
		
		double xPos = CursorPosInput.getXPos();
		double yPos = CursorPosInput.getYPos();
		
		return 1;
		
	}

}
