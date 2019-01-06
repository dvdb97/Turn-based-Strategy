package gui;

import fundamental.GUIWindow;
import gui.tileInfo.TileInfoWindow;
import interaction.TileSelecter;
import world.gameBoard.GameBoard;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	
	
	public static void init() {
		
		tiw = new TileInfoWindow();
	//	window = new DragableWindow(new GUIQuad(), WHITE, new GUIElementMatrix(0, 0, 0.25f, 0.25f));
	
		
	}
	
	public static void update() {
		tiw.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
	}
	
}
