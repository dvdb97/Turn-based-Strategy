package gui.tileInfo;

import world.GameBoard;
import world.Tile;

public class TileInfoStringIssuer {
	
	public static String getTileInfoString(Tile tile) {
		
		return "Index\t\t: " + tile.getIndex() + "\n"
	         + "Fertility\t: " + tile.getFertility().getString() + "\n"
		     + "Forest\t\t: " + tile.getForest().getString() + "\n"
		     + "Height\t\t: " + tile.getAvgHeight() + "\n"
		     + "Hügeligkeit\t\t: " + tile.getHeightSTDV() + "\n"
		     + "City\t\t: " + (GameBoard.getCity(tile.getIndex()) != null);

	}
	
}
