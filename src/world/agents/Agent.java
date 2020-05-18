package world.agents;

import world.city.City;
import world.city.Population;

public class Agent {
	//TODO: basic (and maybe abstract) agent class
	//TODO: subclasses for different kinds of agents (e.g. military units ...)
	
	private City homeCity;
	private int position;	//tile index

	//******************** contructor ***********************************
	
	public Agent(City homeCity) {
		
		this.homeCity = homeCity;
		this.position = homeCity.getTileIndex();
		
	}
	
	//******************** get & set ************************************
	
	public int getPosition() {
		return position;
	}
		
}
