package world.buildings;

import world.GoodsVector;

abstract class Company extends Building implements Seller {
	
	private int labourCosts;
	
	private GoodsVector storage;
	
	private Branch[] branches;
	
}
