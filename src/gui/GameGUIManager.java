package gui;

import fundamental.GUIWindow;
import implementations.ColorPickWindow;
import interaction.TileSelecter;
import world.GameBoard;
import world.agents.Agent;
import world.estate.City;

public class GameGUIManager {
	
	private static GUIWindow window;
	private static TileInfoWindow tiw;
	private static AgentInfoWindow aiw;
	private static CityInfoWindow ciw;
	
	
	public static void init() {
		
		tiw = new TileInfoWindow();
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
	
	public static void showCityInfoWindow(City city) {
		if (ciw == null)
			ciw = new CityInfoWindow(city);
		else
			ciw.changeCity(city);	
	}
	
	
	public static void deleteAgentInfoWindow() {
		aiw = null;
	}
	
	public static void deleteCityInfoWindow() {
		ciw = null;
	}
}
