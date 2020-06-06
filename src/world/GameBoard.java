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
	
	private static IGraph<Integer> graph;
	
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
	static void addStreet(int tileIndex1, int tileIndex2) {
		streets.setValue(tileIndex1, tileIndex2, 1);
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
	 * @return the requested tile or null if it doesn't exist (invalid index)
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
	
	public static int getNumTiles() {
		return tiles.length;
	}
	
	//-------------------------------- graph ---------------------------------
	
	public static IGraph<Integer> getGraph() {
		return graph;
	}
	
}

class GameBoardGraph extends SymmetricSparseMatrix implements IGraph<Integer> {
	
	GameBoardGraph() {
		super(GameBoard.getNumTiles(), GameBoard.getNumTiles()*6, 1f);
		
		float cost = 2f;
		
		for (int i=0; i<getN(); i++) {
			int [] surrounding = TileSurrounding.getSurrounding(i);
			for (int j=0; j<surrounding.length; j++) {
				if (surrounding[j] != -1) {
					cost = 1+Math.abs(GameBoard.getTile(i).getAvgHeight()-GameBoard.getTile(surrounding[j]).getAvgHeight())/0.1f;
					setValue(i, surrounding[j], (int)(cost*10000f));
				}
			}
		}
		
	}
	
	@Override
	public List<Integer> getSuccessors(Integer node) {
		int[] s = TileSurrounding.getSurrounding(node, 1);
		List<Integer> l = ListUtil.asListI(s, (e) -> ((e != -1) && !GameBoard.getTile(e).isWater()));
		return l;
	}
	
	@Override
	public float getCosts(Integer start, Integer end) {
		return getValue(start, end)/10000f;
	}
	
	@Override
	public float getHeuristic(Integer start, Integer end) {
		return WorldManager.getRelativeDistance(start, end);
	}	
}
