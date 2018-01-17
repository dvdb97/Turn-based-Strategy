package world;

class Tile {
	
	private int index;
	
	//geography
	private float avgHeight;
	private float heightSTDV;
	
	private float fertility;
	private float woodlot;
	
	//buildings
	private int maxNumBuildings;
	private Building[] buildings;
	
	
	//------------------------- constructor --------------------------
	public Tile(int index, float avgHeight, float heightSTDV) {
		
		this.index = index;
		this.avgHeight = avgHeight;
		this.heightSTDV = heightSTDV;
		
	}
	
}
