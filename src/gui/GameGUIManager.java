package gui;

import fundamental.GUIWindow;
import gui.tileInfo.TileInfoWindow;
import implementations.ColorPickWindow;
import interaction.TileSelecter;
import world.gameBoard.GameBoard;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	
	
	public static void init() {
		
		tiw = new TileInfoWindow();
		new ColorPickWindow();
	
		
	}
	
	public static void update() {
		tiw.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
	}
	
}
