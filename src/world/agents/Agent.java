package world.agents;

import world.city.City;

public abstract class Agent {
	//TODO: basic (and maybe abstract) agent class
	//TODO: subclasses for different kinds of agents (e.g. military units ...)
	
	private City homeCity;
	private int range;		//number of tiles, the agent can travel within one round

	//******************** constructor ***********************************
	
	public Agent(City homeCity) {
		
		this.homeCity = homeCity;
		this.range = 3;	//TODO: don't hard code
	}
	
	//******************** get & set ************************************
	
	public City getHomeCity() {
		return homeCity;
	}

	public String getAgentInfoString() {
		return "An Agent. Yay!";
	}	
}
