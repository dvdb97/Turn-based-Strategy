package gui.tileInfo;

import world.gameBoard.Tile;

public class TileInfoStringIssuer {
	
	public static String getTileInfoString(Tile tile) {
		
		return "Index\t: " + tile.getIndex() + "\n"
	         + "Fertility\t: " + tile.getFertility().getString() + "\n"
		     + "Forest\t: " + tile.getForest().getString();

		
	}
	
}
