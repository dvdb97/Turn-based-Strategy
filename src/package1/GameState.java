package package1;

import world.*;

public class GameState {
	
	//The states the game can be in. Temporary!
	public static enum STATE {
		START_MENU, MENU, END_SCREEN, GAME, PAUSED
	};
	
	
	private static STATE gameState;
	
	
	public void init() {
		gameState = STATE.START_MENU;
		
		//Other stuff that can be set.
	}
	
	
	public STATE getCurrentGameState() {
		return gameState;
	}
	
	
	public void setGameState(STATE state) {
		gameState = state;
	}
	
	
	private static Tile[] tiles;
	public static int boardWIDTH, boardHEIGHT;
	
	public static float shiftX = -6.0f;
	public static float shiftY = -6.0f;
	public static float tilt   =  0.0f;
	public static float zoom = 0.2f;
	
	public static boolean boardVisible;
	
	public static int[] markedTile = {-1, -1};				//{col, row}
	public static int[] selectedTile = {-1, -1};			//{col, row}
	
	//TODO: temp
	public static float seaLevel = 0.0f;
	
	//---------------------- get & set --------------------------------
	//about tiles
	public static void setTiles(Tile[] tiles, int width, int height) {
		GameState.tiles = tiles;
		boardWIDTH = width;
		boardHEIGHT = height;
		
		//TODO: later this is not set here
		boardVisible = true;
	}
	
	public static boolean isTileMarked(int x, int y) {
		
		if (x == markedTile[0] && y == markedTile[1]) {
			return true;
		} else {
			return false;
		}
	}
	
	//------------------------- useful methods ---------------------------
	//resets markedTile to {-1, -1}
	public static void resetMarkedTile() {
		
		markedTile[0] = -1;
		markedTile[1] = -1;
		
	}
	
	public static void resetSelectedTile() {
		
		selectedTile[0] = -1;
		selectedTile[1] = -1;
		
	}
	
	public static boolean anyTileMarked() {
		
		if(markedTile[0] > -1 && markedTile[1] > -1) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static boolean anyTileSelected() {
		
		if (selectedTile[0] > -1 && selectedTile[1] > -1) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static int toInt(int[] tileCoords) {
		
		return tileCoords[0] + tileCoords[1]*boardWIDTH;
		
	}
}
