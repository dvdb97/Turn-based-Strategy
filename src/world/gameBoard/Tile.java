package world.gameBoard;

import java.util.ArrayList;

import utils.Percentage;
import world.buildings.Building;
import world.city.City;

public class Tile {
	
	private int index;
	
	//geography
	private float avgHeight;
	private float heightSTDV;
	
	private boolean water;
	
	private Percentage fertility;
	private Percentage forest;		//Grad der Bewaldung
	
	//buildings
	private int maxNumBuildings;
	private ArrayList<Building> buildings;
	
	private City city;
	
	
	//*************************** constructor ***********************************
	public Tile(int index, float avgHeight, float heightSTDV, Percentage fertility) {
		
		this.index = index;
		this.avgHeight = avgHeight;
		this.heightSTDV = heightSTDV;
		this.fertility = fertility;
		
		if (avgHeight < 0) {
			water = true;
		} else {
			water = false;
		}
		
		forest = calcForest();
		
	}
	
	//0.693147
	
	private Percentage calcForest() {
		
		if (water) {
			return new Percentage();
		}
		
		float a = 0.693147f;
		
		Percentage heightInfluence = new Percentage(avgHeight<a ? avgHeight/a : (avgHeight<2*a ? (2-avgHeight/a) : 0 ));
		
		return heightInfluence.times(fertility);
		
	}
	
	
	
	//*************************** city ******************************************
	
	
	/**
	 * 
	 * @param city
	 * @return false if city already has been set
	 */
	public boolean setCity(City city) {
		
		if (this.city != null) {
			System.err.println("there can be only one city on a tile");
			return false;
		}
		
		this.city = city;
		return true;
		
	}
	
	/**
	 * 
	 * @return true if has been removed, false if there wasn't a city to remove
	 */
	public boolean removeCity() {
		
		if (city == null) {
			return false;
		}
		
		city = null;
		return true;
		
	}
	
	
	//**************************** buildings *************************************
	
	public boolean addBuilding(Building building) {
		
		if (buildings.size() >= maxNumBuildings) {
			return false;
		}
		
		buildings.add(building);
		return true;
		
	}
	
	public Building getBuilding(int i) {
		return buildings.get(i);
	}
	
	
	
	//**************************** get & set *************************************
	
	public int getIndex() {
		return index;
	}
	
	public int getNumBuildings() {
		return buildings.size();
	}
	
	/**
	 * 
	 * @return true, if this tile is traded as a water tile (average height < 0)
	 */
	public boolean isWater() {
		return water;
	}
	
	
	/**
	 * @return the average height
	 */
	public float getAvgHeight() {
		return avgHeight;
	}


	/**
	 * @param average height the avgHeight to set
	 */
	public void setAvgHeight(float avgHeight) {
		this.avgHeight = avgHeight;
	}


	/**
	 * @return the heights standard deviation
	 */
	public float getHeightSTDV() {
		return heightSTDV;
	}


	/**
	 * @param heightSTDV the heights standard deviation to set
	 */
	public void setHeightSTDV(float heightSTDV) {
		this.heightSTDV = heightSTDV;
	}


	/**
	 * @return the fertility
	 */
	public Percentage getFertility() {
		return fertility;
	}


	/**
	 * @param fertility the fertility to set
	 */
	public void setFertility(Percentage fertility) {
		this.fertility = fertility;
	}


	/**
	 * @return the forest value (how much forest is on this tile), float between 0 and 1
	 */
	public Percentage getForest() {
		return forest;
	}


	/**
	 * @param forest the forest value to set
	 */
	public void setForest(Percentage forest) {
		this.forest = forest;
	}
	
	
}
