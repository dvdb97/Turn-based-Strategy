package gui.tileInfo;

import world.gameBoard.Tile;

public class TileInfoStringIssuer {
	
	public static String getTileInfoString(Tile tile) {
		
		return "Index\t\t: " + tile.getIndex() + "\n"
	         + "Fertility\t: " + tile.getFertility().getString() + "\n"
		     + "Forest\t\t: " + tile.getForest().getString() + "\n"
		     + "City\t\t: " + tile.hasCity();

	}
	
}
