package world.agents;

import assets.meshes.geometry.Color;
import world.city.City;

public abstract class Agent {
	//TODO: basic (and maybe abstract) agent class
	//TODO: subclasses for different kinds of agents (e.g. military units ...)

	private Color color;
	private City homeCity;
	private int range;		//number of tiles, the agent can travel within one round
	public int budget;		//TODO: temporary
	
	//******************** constructor ***********************************
	
	public Agent(City homeCity, Color color) {
		
		this.color = color;
		this.homeCity = homeCity;
		this.range = 3;	//TODO: don't hard code
		budget = 10;
	}
	
	//******************** public getter **********************************
	
	public City getHomeCity() {
		return homeCity;
	}

	public String getAgentInfoString() {
		return "An Agent. Yay!";
	}
	
	public Color getColor() {
		return color.copyOf();
	}
}
