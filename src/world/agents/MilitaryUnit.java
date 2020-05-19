package world.agents;

import java.util.Random;

import assets.meshes.geometry.Color;
import world.city.City;

public class MilitaryUnit extends Agent {
	
	//TODO: maybe use a custom class for agent properties, similar to Percentage
	private int strength;
	private int endurance;
	private int experience;
	
	public MilitaryUnit(City homeCity, Color color) {
		super(homeCity, color);
		Random r = new Random();
		strength   = r.nextInt(100);
		endurance  = r.nextInt(100);
		experience = r.nextInt(100);
	}
	
	public String getAgentInfoString() {
		return "Strengh:    " + strength + "\nEndurance:  " + endurance + "\nExperience: " + experience;
	}
	
}
