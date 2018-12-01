package gui;

import container.GUIWindow;
import dataType.GUIElementMatrix;
import gui.tileInfo.TileInfoWindow;
import interaction.TileSelecter;
import work_in_progress.test.ColorPickWindow;
import work_in_progress.test.FontWindow;
import world.gameBoard.GameBoard;

import static utils.ColorPalette.*;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	
	
	public static void init() {
		
		//window = new ColorPickWindow();
		//window = new FontWindow(RED, new GUIElementMatrix());
		tiw = new TileInfoWindow();
		
	}
	
	public static void update() {
		tiw.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
	}
	
}
