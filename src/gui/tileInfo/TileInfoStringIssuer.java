package gui.tileInfo;

import world.gameBoard.Tile;

public class TileInfoStringIssuer {
	
	public static String getTileInfoString(Tile tile) {
		
		return "Index    : " + tile.getIndex() + "\n"
	         + "Fertility: " + tile.getFertility().getString() + "\n"
		     + "Forest   : " + tile.getForest().getString();

		
	}
	
}
