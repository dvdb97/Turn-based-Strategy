package world.estate;

import utils.Percentage;

public class Population {
	
	//occupation
	
	private Percentage satisfaction; //affected by health, employment rate and howWellIsTheNeedForGoodsSatisfied
	private Percentage health;
	private Percentage employmentRate;
	private Percentage howWellIsTheNeedForGoodsSatisfied;
	
	//************************ constructor ********************************
	
	public Population(Percentage satisfaction, Percentage health, Percentage employmentRate,
			Percentage howWellIsTheNeedForGoodsSatisfied) {
		
		this.satisfaction = satisfaction;
		this.health = health;
		this.employmentRate = employmentRate;
		this.howWellIsTheNeedForGoodsSatisfied = howWellIsTheNeedForGoodsSatisfied;
		
	}
	
	/**
	 * TODO: standard values
	 */
	public Population() {
		this(new Percentage(0f), new Percentage(0f), new Percentage(0f), new Percentage(0f));
	}
	
	
	//**************************** get & set *******************************
	
	/**
	 * @return the population's satisfaction
	 */
	public Percentage getSatisfaction() {
		return satisfaction;
	}

	/**
	 * @return the population's health
	 */
	public Percentage getHealth() {
		return health;
	}

	/**
	 * @return the employment rate, what percentage of the population is employed
	 */
	public Percentage getEmploymentRate() {
		return employmentRate;
	}

	/**
	 * @return the percentage, how well the population's need for goods is satisfied
	 */
	public Percentage getHowWellIsTheNeedForGoodsSatisfied() {
		return howWellIsTheNeedForGoodsSatisfied;
	}
	
	
}
