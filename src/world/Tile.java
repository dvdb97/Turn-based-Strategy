package world;

import utils.Const;
import world.City;

public class Tile {
	
	public int positionOnBoard;
	public int landscapeID;
	
	public String type;
	
	public City city;
	
	public int tribeID;
	
	//------------------------ display stats --------------------
	public void displayStats() {
		
		System.out.println("-------");
		
		//landscape
		System.out.println("landscape : " + landscapeID);
		
		//city
		System.out.print("city : ");
		if (city != null) {
			System.out.println("yes");
			System.out.println("city's inventory : " + city.inventory.toString());
		} else {
			System.out.println("no");
		}
		
		//tribe
		System.out.println("tribe : " + tribeID);
		
	}
	
	//------------------------- constructor ---------------------
	public Tile(int positionOnBoard, String type, int landscapeID) {
		
		this.positionOnBoard = positionOnBoard;
		
		this.type = type;
		
		this.landscapeID = landscapeID;
		
		tribeID = Const.MAX_NUM_TRIBES;
		
	}
	
	//-------------------------  get color  ---------------------
	//public float[] getColorAs
	
}