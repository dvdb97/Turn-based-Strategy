package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import assets.meshes.Mesh3D;
import assets.meshes.Transformable;
import assets.meshes.geometry.Color;
import gameplay.Tribe;
import gui.GameGUIManager;
import models.gameboard.GameBoardModel;
import models.meeples.AgentModel;
import models.meeples.StreetModel;
import models.seeds.SuperGrid;
import pathfinding.AStarSearch;
import utils.ColorPalette;
import world.agents.Agent;
import world.agents.MilitaryUnit;
import world.estate.City;

public class AgentAuthority {
	
	private static GameBoardModel gameBoardModel;
	private static List<Mesh3D> meepleModels;
	private static SuperGrid superGrid;
	private static HashMap<Agent, AgentModel> agentModels;

	//TODO: dont hard code
	private static final int AGENT_COST = 15;
	
	public static void init(GameBoardModel gameBoardModel, List<Mesh3D> meepleModels, SuperGrid superGrid) {
		AgentAuthority.gameBoardModel = gameBoardModel;
		AgentAuthority.meepleModels   = meepleModels;
		AgentAuthority.superGrid      = superGrid;
		agentModels = new HashMap<>();
	}
	
	public static Agent requestAgentInCity(City city) {
		//TODO: in future buildings spawn agents, not cities
		
		if (city==null)
			return null;
		
		//if (city is not allowed to spawn agent)
			//return false;
		
		if (Tribe.getCash() < AGENT_COST)
			return null;
		
		//spawn agent
		Color agentColor = ColorPalette.randomColor();
		Agent agent = new MilitaryUnit(city, agentColor);
		GameBoard.addAgent(agent);
		
		Tribe.increaseCash(-AGENT_COST);
		
		AgentModel agentModel = new AgentModel(gameBoardModel.transformable, agentColor);
		agentModel.transformable.setScaling(0.05f, 0.05f, 0.05f);
		agentModel.transformable.setRotation(90f * Transformable._1_DEGREE, 0f, 0f);
		agentModel.transformable.setTranslation(superGrid.getHexCenter(GameBoard.getTile(city).getIndex()));
		meepleModels.add(agentModel);
		agentModels.put(agent, agentModel);
		return agent;
		
	}
	
	public static List<Integer> requestToMoveAgent(Agent agent, int destinationTileIndex) {

		if (agent==null)
			return null;
		
		if (GameBoard.getTile(destinationTileIndex).isWater()) {
			System.out.println("agent can't be on water");
			return null;
		}
		
		int startingTileIndex = GameBoard.getTile(agent).getIndex();
		
//		if (possibleToGetFromOneTileToTheOther(startingTileIndex, destinationTileIndex)) {
//			return false;	//implies constraint, that "agent can't be on water"(few lines above)
//		}
		
		List<Integer> path = GameBoard.getPathFinder().getPath(startingTileIndex, destinationTileIndex);
		float costs = GameBoard.getPathFinder().getPathCosts(path);
//		List<Integer> path = AStarSearch.getPath(GameBoard.getGraph(), startingTileIndex, destinationTileIndex);
//		float costs = AStarSearch.getPathCosts(GameBoard.getGraph(), path);
		
		if (agent.getTravelBudget() < costs) {
			return null;
		}
		
		GameBoard.moveAgent(agent, destinationTileIndex);
		agent.increaseTravelBudget(-costs);
		agentModels.get(agent).transformable.setTranslation(superGrid.getHexCenter(destinationTileIndex));
		return path;
		
	}
	
	public static boolean deleteAgent(Agent agent) {
		
		if (agent==null)
			return false;
		
		GameBoard.deleteAgent(agent);
		meepleModels.remove(agentModels.get(agent));
		agentModels.remove(agent);
		GameGUIManager.deleteAgentInfoWindow();
		System.out.println("agent deleted");
		return true;
		
	}
}
