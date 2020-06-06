package world.buildings;

import world.GoodsVector;

abstract class Company extends Building implements Seller {
	
	public Company(int gain) {
		super(gain);
		// TODO Auto-generated constructor stub
	}

	private int labourCosts;
	
	private GoodsVector storage;
	
	private Branch[] branches;
	
}
