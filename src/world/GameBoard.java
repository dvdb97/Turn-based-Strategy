package world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import pathfinding.IGraph;
import utils.ListUtil;
import utils.SymmetricSparseMatrix;
import utils.TileSurrounding;
import world.agents.Agent;
import world.city.City;

//this class is sort of a data structure
public class GameBoard {
		
	//-------------------------------- fields ---------------------------------
	private static int length, width;
	private static Tile[] tiles;
	private static HashMap<Tile, City> cities;
	private static HashMap<Agent, Tile> agents;
	private static SymmetricSparseMatrix streets;
	
	private static IGraph<Tile> graph;
	
	//---------------------------------- init -----------------------------------
	
	/**
	 * 
	 * @param tiles 
	 * @return true if tiles was set, false if tiles has been set before (tiles can only be set once)
	 */
	static boolean setTiles(Tile[] tiles, int length, int width) {
		
		if (GameBoard.tiles != null)
			return false;
		
		if (tiles.length != length*width)
			throw new IllegalArgumentException();
		
		GameBoard.length = length;
		GameBoard.width  = width;
		GameBoard.tiles  = tiles;
		GameBoard.graph  = new GameBoardGraph();

		cities = new HashMap<>();
		agents = new HashMap<>();
		streets = new SymmetricSparseMatrix(tiles.length);
		
		return true;
	}
	
	//-------------------------------- cities --------------------------------
	static boolean tileAvailableForCity(Tile tile) {
		//TODO: maybe use exceptions here
		if (cities.containsKey(tile)) {
			System.out.println("can't place two cities on one tile");
			return false;
		}
		
		if (tile.isWater()) {
			System.out.println("can't place a city on water");
			System.out.println(tile.getAvgHeight());
			return false;
		}
		
		return true;
	}
	
	/**
	 * BuildingAuthority is the only authority that has the authority to use this method!
	 */
	static void addCity(Tile tile, City city) {
		cities.put(tile, city);
	}

	//-------------------------------- agents --------------------------------

	/**
	 * AgentAuthority is the only authority that has the authority to use this method!
	 */
	static void addAgent(Agent agent) {
		agents.put(agent, getTile(agent.getHomeCity()));
	}
	
	/**
	 * AgentAuthority is the only authority that has the authority to use this method!
	 */
	static void moveAgent(Agent agent, int tileIndex) {
		agents.replace(agent, getTile(tileIndex));
	}
	
	/**
	 * AgentAuthority is the only authority that has the authority to use this method!
	 */
	static void deleteAgent(Agent agent) {
		agents.remove(agent);
	}
	
	//------------------------------- streets --------------------------------
	
	/**
	 * 
	 * @return false, if street has been added already. true, otherwise
	 */
	static boolean addStreet(int tileIndex1, int tileIndex2) {
		if (streets.getValue(tileIndex1, tileIndex2)==1)
			return false;
		streets.setValue(tileIndex1, tileIndex2, 1);
		return true;
	}
	
	//-------------------------------- public getter -------------------------
	
	public static Tile getTile(Agent agent) {
		return agents.get(agent);
	}
	
	public static Tile getTile(City city) {
		for (Iterator<Tile> i = cities.keySet().iterator(); i.hasNext(); ) {
			Tile t = i.next();
			if (cities.get(t) == city)
				return t;
		}
		return null;
	}
	
	/**
	 * @param tileIndex position of the requested agent's tile in "Tile[] tiles"
	 * @return the requested agent or null if there is no agent
	 */
	public static ArrayList<Agent> getAgents(int tileIndex) {
		ArrayList<Agent> agentList = new ArrayList<>();
		for (Iterator<Agent> i = agents.keySet().iterator(); i.hasNext(); ) {
			Agent a = i.next();
			if (agents.get(a).getIndex() == tileIndex)
				agentList.add(a);
		}
		return agentList;
	}
	
	/**
	 * @param tileIndex position of the requested city's tile in "Tile[] tiles"
	 * @return the requested city or null if there is no city
	 */
	public static City getCity(int tileIndex) {
		return cities.get(tiles[tileIndex]);
	}
		
	/**
	 * 
	 * @param index position of the requested tile in "Tile[] tiles"
	 * @return the requested tile
	 */
	public static Tile getTile(int index) {
		
		if (index < 0 || index >= tiles.length)
			return null;
		
		return tiles[index];
		
	}
	
	public static boolean isStreet(int tileIndex1, int tileIndex2) {
		return (streets.getValue(tileIndex1, tileIndex2) == 1);
	}
	
	
	/**
	 * @return the length
	 */
	public static int getLength() {
		return length;
	}
	
	
	/**
	 * @return the width
	 */
	public static int getWidth() {
		return width;
	}
	
	//-------------------------------- graph ---------------------------------
	
	public static IGraph<Tile> getGraph() {
		return graph;
	}
	
}

class GameBoardGraph implements IGraph<Tile> {
	
	GameBoardGraph() {
		
	}
	
	@Override
	public List<Tile> getSuccessors(Tile node) {
		List<Tile> l = ListUtil.asList(TileSurrounding.getSurroundingTiles(node.getIndex()));
		ListUtil.removeNullInPlace(l);
		l.removeIf((e) -> e.isWater());
		return l;
	}
	
	@Override
	public float getCosts(Tile start, Tile end) {
		return 1+Math.abs(start.getAvgHeight()-end.getAvgHeight())/0.1f;
	}
	
	@Override
	public float getHeuristic(Tile start, Tile end) {
		return WorldManager.getDistance(start.getIndex(), end.getIndex());	//TODO scale it, such that heuristic cost between to neighbouring tiles is one
	}	
}
