package gui;

import fundamental.GUIWindow;
import implementations.ColorPickWindow;
import interaction.TileSelecter;
import world.GameBoard;
import world.agents.Agent;
import world.estate.City;
import world.estate.Estate;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	private static AgentInfoWindow aiw;
	private static EstateInfoWindow eiw;
	
	
	public static void init() {
		
		tiw = new TileInfoWindow();
	}
	
	public static void update() {
		tiw.setTile(GameBoard.getTile(TileSelecter.getSelectedTileIndex()));
	}
	
	public static void showAgentInfoWindow(Agent agent) {
		if (agent == null)
			return;
		
		if (aiw == null)
			aiw = new AgentInfoWindow(agent);
		else
			aiw.changeAgent(agent);	
	}
	
	public static void showEstateInfoWindow(Estate estate) {
		if (estate == null)
			return;
		
		if (eiw == null)
			eiw = new EstateInfoWindow(estate);
		else
			eiw.changeEstate(estate);	
	}
	
	
	public static void deleteAgentInfoWindow() {
		aiw = null;
	}
	
	public static void deleteCityInfoWindow() {
		eiw = null;
	}
}
