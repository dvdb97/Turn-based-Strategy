package world;

public class City {
	
	public int POSITION_ON_BOARD;
	public int PLAYER_ID;
	//TODO: implement capitol cities later
	//public boolean isCapitolCity;
	
	public GoodsVector inventory;
	
	//------------------------- constructor ---------------------
	public City(int positionOnBoard, int playerID, City payingCity) {
		
		//set fields
		this.POSITION_ON_BOARD = positionOnBoard;
		PLAYER_ID = playerID;
		
		//set start inventory
		inventory = new GoodsVector();
		
		//let the other city pay for it
		payingCity.inventory.add(utils.Const.CITY_COSTS);
		
	}
	
	public City(int positionOnBoard, int playerID) {
		
		//set fields
		this.POSITION_ON_BOARD = positionOnBoard;
		PLAYER_ID = playerID;
		
		//set start inventory
		inventory = new GoodsVector(utils.Const.START_INVENTORY);
		
	}
}
