package gui;

import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import gui.tileInfo.TileInfoWindow;
import interaction.TileSelecter;
import work_in_progress.ColorPickWindow;
import work_in_progress.test.FontWindow;
import world.gameBoard.GameBoard;

import static utils.ColorPalette.*;

import container.DragableWindow;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	
	
	public static void init() {
		
		tiw = new TileInfoWindow();
		new DragableWindow(WHITE, new GUIElementMatrix(-0.5f, 0.5f, 0.4f, 0.4f));
		new ColorPickWindow();
	
		
	}
	
	public static void update() {
		tiw.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
	}
	
}
