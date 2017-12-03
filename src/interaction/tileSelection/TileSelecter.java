package interaction.tileSelection;

import core.Application;
import elements.Tile;
import interaction.input.CursorPosInput;
import math.vectors.Vector3f;

public class TileSelecter {
	
	
	private static Tile[] tiles;
	
	
	private static Tile selectedTile;
	
	private static int selectedTileIndex;
	
	
	public static void init(Tile[] generatedTiles) {
		
		tiles = generatedTiles;
		
	}
	
	
	public static void update() {
		
		
		
	}
	
	
	public static int computeSelectedTileIndex() {
		
		float cursorX = Application.toOpenglXCoords(CursorPosInput.getXPos());
		float cursorY = Application.toOpenglYCoords(CursorPosInput.getYPos());
		
		Vector3f rayStart = new Vector3f(cursorX, cursorY, 1.0f);
		Vector3f rayDirection = new Vector3f(0f, 0f, 1f);
		
		
	}

}
