package gui;

import fundamental.GUIWindow;
import implementations.ColorPickWindow;
import interaction.TileSelecter;
import world.GameBoard;
import world.agents.Agent;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	private static AgentInfoWindow aiw;
	
	
	public static void init() {
		
		tiw = new TileInfoWindow();
		new TurnGUI();
	}
	
	public static void update() {
		tiw.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
	}
	
	public static void showAgentInfoWindow(Agent agent) {
		if (aiw == null)
			aiw = new AgentInfoWindow(agent);
		else
			aiw.changeAgent(agent);	
	}
	
	public static void hideAgentInfoWindow() {
		aiw.close();
		aiw = null;
		System.out.println(aiw);
	}
}
