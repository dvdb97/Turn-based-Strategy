package world.agents;

import java.util.function.Consumer;

import assets.meshes.geometry.Color;
import world.estate.City;

public abstract class Agent {
	//TODO: basic (and maybe abstract) agent class
	//TODO: subclasses for different kinds of agents (e.g. military units ...)

	private Color color;
	private City homeCity;
	private int range;			//number of tiles, the agent can travel within one round
	private float budget;
	
	private Consumer<? super Agent> func;
	
	//******************** constructor ***********************************
	
	public Agent(City homeCity, Color color) {
		
		this.color = color;
		this.homeCity = homeCity;
		this.range = 3;	//TODO: don't hard code
		budget = 20f;
	}
	//************************************************************************
	
	public void increaseTravelBudget(float delta) {
		budget += delta;
		func.accept(this);
	}
	
	//******************** public getter **********************************

	public float getTravelBudget() {
		return budget;
	}
	
	public City getHomeCity() {
		return homeCity;
	}

	public String getAgentInfoString() {
		return "An Agent. Yay!";
	}
	
	public Color getColor() {
		return color.copyOf();
	}
	
	
	//************************************************************************
	
	public void setCallbackFunction(Consumer<? super Agent> func) {
		this.func = func;
	}
	
}
