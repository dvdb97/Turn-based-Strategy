package world.gameBoard;

import java.util.HashMap;

import world.city.City;

//this class is a sort of data structure
public class GameBoard {
	
	private static int length, width;
	
	//-------------------------------- fields ---------------------------------
	private static Tile[] tiles;
	private static HashMap<Tile, City> cities;
	
	public static boolean tileAvailableForCity(Tile tile) {
		//TODO: maybe use exceptions here
		if (cities.containsKey(tile)) {
			System.out.println("can't place two cities on one tile");
			return false;
		}
		
		if (tile.isWater()) {
			System.out.println("can't place a city on water");
			return false;
		}
		
		return true;
	}
	
	/**
	 * BuildingAuthority is the only authority that has the authority to use this method!
	 */
	public static void addCity(Tile tile, City city) {
		
		cities.put(tile, city);
		city.setTile(tile);
		
	}
	
	//-------------------------------- get & set -------------------------------
	
	/**
	 * 
	 * @param tiles 
	 * @return true if tiles was set, false if tiles has been set before (tiles can only be set once)
	 */
	public static boolean setTiles(Tile[] tiles, int length, int width) {
		
		if (GameBoard.tiles == null) {
			
			GameBoard.length = length;
			GameBoard.width  = width;
			GameBoard.tiles  = tiles;
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * @param index position of the requested tile in "Tile[] tiles"
	 * @return the requested tile
	 */
	public static Tile getTile(int index) {
		
		return tiles[index];
		
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
	
	
	//-------------------------------------- reset -------------------------------
	
	public static void reset() {
		
		tiles = null;
		cities = null;
		
	}
	
}