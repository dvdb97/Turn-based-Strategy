package gui;

import gui.tileInfo.TileInfoWindow;
import interaction.TileSelecter;
import world.gameBoard.GameBoard;

public class GameGUIManager {
	
	private static TileInfoWindow TIW;
	
	public static void init() {
		
		//Disable this to remove the gui window from the screen
		TIW = new TileInfoWindow();
		
	}
	
	public static void update() {
		
		TIW.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
		
	}
	
}