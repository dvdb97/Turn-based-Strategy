package world.agents;

import world.city.City;

public class MilitaryUnit extends Agent {
	
	//TODO: maybe use a custom class for agent properties, similar to Percentage
	private int strength;
	private int endurance;
	private int experience;
	
	public MilitaryUnit(City homeCity) {
		super(homeCity);
	}
	
	public String getAgentInfoString() {
		return "Strengh:    " + strength + "\nEndurance:  " + endurance + "\nExperience: " + experience;
	}
}
