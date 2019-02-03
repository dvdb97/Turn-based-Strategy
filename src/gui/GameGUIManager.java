package gui;

import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import gui.tileInfo.TileInfoWindow;
import interaction.TileSelecter;
import rendering.shapes.GUIQuad;
import world.gameBoard.GameBoard;

import static utils.ColorPalette.*;

import container.DragableWindow;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	
	
	public static void init() {
		
		tiw = new TileInfoWindow();
	
		
	}
	
	public static void update() {
		tiw.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
	}
	
}
