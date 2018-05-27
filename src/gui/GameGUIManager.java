package gui;

import dataType.GUIElementMatrix;
import gui.tileInfo.TileInfoWindow;
import interaction.TileSelecter;
import rendering.shapes.GUIQuad;
import work_in_progress.test.DragableWindow;
import work_in_progress.test.TestWindow;
import world.gameBoard.GameBoard;

import static utils.ColorPalette.*;

public class GameGUIManager {
	
	private static TileInfoWindow TIW;
	
	public static void init() {
		
		//Disable this to remove the gui window from the screen
		TIW = new TileInfoWindow();
		
	//	TestWindow window = new TestWindow();
		
		DragableWindow window2 = new DragableWindow(new GUIQuad(), WHITE, new GUIElementMatrix(0.2f, 0.2f, 0.4f, 0.4f));
		
	}
	
	public static void update() {
		
		TIW.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
		
	}
	
}
