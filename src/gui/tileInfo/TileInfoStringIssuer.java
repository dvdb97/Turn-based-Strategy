package gui.tileInfo;

import world.GameBoard;
import world.Tile;

public class TileInfoStringIssuer {
	
	public static String getTileInfoString(Tile tile) {
		
		return "Index\t\t: " + tile.getIndex() + "\n"
	         + "Fertility\t: " + tile.getFertility().getString() + "\n"
		     + "Forest\t\t: " + tile.getForest().getString() + "\n"
		     + "City\t\t: " + (GameBoard.getCity(tile.getIndex()) != null);

	}
	
}
