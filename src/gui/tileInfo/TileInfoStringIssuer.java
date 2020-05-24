package gui.tileInfo;

import world.GameBoard;
import world.Tile;

public class TileInfoStringIssuer {
	
	public static String getTileInfoString(Tile tile) {
		
		String city;
		
		if (GameBoard.getCity(tile.getIndex()) == null) {
			city = "nein";
		} else {
			city = "ja";
		}
		
		return "Index:        " + tile.getIndex() + "\n"
	         + "Fertility:    " + tile.getFertility().getString() + "\n"
		     + "Forest:       " + tile.getForest().getString() + "\n"
		     + "Height:       " + tile.getAvgHeight() + "\n"
		     + "Hügeligkeit:  " + tile.getHeightSTDV() + "\n"
		     + "City:         " + city;

	}
	
}
