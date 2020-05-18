package world;

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
	
	public static void init(GameBoardModel gameBoardModel, List<Mesh3D> meepleModels, SuperGrid superGrid) {
		AgentAuthority.gameBoardModel = gameBoardModel;
		AgentAuthority.meepleModels = meepleModels;
		AgentAuthority.superGrid = superGrid;
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
		return true;
		
	}
	
	public static boolean moveAgent(Agent agent, int tileIndex) {

		if (agent==null)
			return false;
		
		
		
		return true;
		
	}
	
}
