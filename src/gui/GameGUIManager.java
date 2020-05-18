package gui;

import fundamental.GUIWindow;
import gui.tileInfo.TileInfoWindow;
import implementations.ColorPickWindow;
import interaction.TileSelecter;
import world.agents.Agent;
import world.gameBoard.GameBoard;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	private static AgentInfoWindow aiw;
	
	
	public static void init() {
		
		tiw = new TileInfoWindow();
		
	}
	
	public static void update() {
		tiw.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
	}
	
	public static void showAgentInfoWindow(Agent agent) {
		aiw = new AgentInfoWindow(agent);
	}
	
	public static void hideAgentInfoWindow() {
		aiw.delete();
		aiw = null;
	}
}
