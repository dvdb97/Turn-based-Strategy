package world;

import java.util.HashMap;
import java.util.List;

import assets.meshes.Mesh3D;
import assets.meshes.Transformable;
import models.gameboard.GameBoardModel;
import models.meeples.AgentModel;
import models.seeds.SuperGrid;
import world.agents.Agent;
import world.agents.MilitaryUnit;
import world.city.City;
import world.gameBoard.GameBoard;

public class AgentAuthority {
	
	private static GameBoardModel gameBoardModel;
	private static List<Mesh3D> meepleModels;
	private static SuperGrid superGrid;
	private static HashMap<Agent, AgentModel> agentModels;
	
	public static void init(GameBoardModel gameBoardModel, List<Mesh3D> meepleModels, SuperGrid superGrid) {
		AgentAuthority.gameBoardModel = gameBoardModel;
		AgentAuthority.meepleModels   = meepleModels;
		AgentAuthority.superGrid      = superGrid;
		agentModels = new HashMap<>();
	}
	
	public static boolean requestAgentInCity(City city) {
		//TODO: in future buildings spawn agents, not cities
		
		if (city==null)
			return false;
		
		//if (city is not allowed to spawn agent)
			//return false;
		
		//if (user has no money)
			//return false;
		
		//spawn agent
		Agent agent = new MilitaryUnit(city);
		GameBoard.addAgent(agent);
		
		AgentModel agentModel = new AgentModel(gameBoardModel.transformable);
		agentModel.transformable.setScaling(0.05f, 0.05f, 0.05f);
		agentModel.transformable.setRotation(90f * Transformable._1_DEGREE, 0f, 0f);
		agentModel.transformable.setTranslation(superGrid.getHexCenter(city.getTileIndex()));
		meepleModels.add(agentModel);
		agentModels.put(agent, agentModel);
		return true;
		
	}
	
	public static boolean requestToMoveAgent(Agent agent, int tileIndex) {

		if (agent==null)
			return false;
		
		if (GameBoard.getTile(tileIndex).isWater()) {
			System.out.println("agent can't be on water");
			return false;
		}
		
		if (GameBoard.getAgent(tileIndex) != null) {
			return false;
		}
		
		GameBoard.moveAgent(agent, tileIndex);
		agentModels.get(agent).transformable.setTranslation(superGrid.getHexCenter(tileIndex));
		return true;
		
	}
	
}
