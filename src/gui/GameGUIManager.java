package gui;

import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import gui.tileInfo.TileInfoWindow;
import interaction.TileSelecter;
import rendering.shapes.GUIQuad;
import work_in_progress.test.DragableWindow;
import world.gameBoard.GameBoard;

import static utils.ColorPalette.*;

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
