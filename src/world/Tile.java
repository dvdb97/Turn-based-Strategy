package world;

class Tile {
	
	private int index;
	
	//geography
	private float avgHeight;
	private float heightSTDV;
	
	private float fertility;
	private float forest;		//Grad der Bewaldung
	
	//buildings
	private int maxNumBuildings;
	private Building[] buildings;
	
	
	//------------------------- constructor --------------------------
	public Tile(int index, float avgHeight, float heightSTDV, float fertility) {
		
		this.index = index;
		this.avgHeight = avgHeight;
		this.heightSTDV = heightSTDV;
		this.fertility = fertility;
		
		forest = calcForest();
		
	}
	
	//0.693147
	
	private float calcForest() {
		
		float a = 0.693147f;
		
		float h = avgHeight>a ? avgHeight/a : (avgHeight-a)/a;
		float f = fertility;
		
	
		return h*f;
		
	}
	
	
	
	//-------------------------- get & set ----------------------------
	
	/**
	 * @return the avgHeight
	 */
	public float getAvgHeight() {
		return avgHeight;
	}


	/**
	 * @param avgHeight the avgHeight to set
	 */
	public void setAvgHeight(float avgHeight) {
		this.avgHeight = avgHeight;
	}


	/**
	 * @return the heightSTDV
	 */
	public float getHeightSTDV() {
		return heightSTDV;
	}


	/**
	 * @param heightSTDV the heightSTDV to set
	 */
	public void setHeightSTDV(float heightSTDV) {
		this.heightSTDV = heightSTDV;
	}


	/**
	 * @return the fertility
	 */
	public float getFertility() {
		return fertility;
	}


	/**
	 * @param fertility the fertility to set
	 */
	public void setFertility(float fertility) {
		this.fertility = fertility;
	}


	/**
	 * @return the forest
	 */
	public float getForest() {
		return forest;
	}


	/**
	 * @param forest the forest to set
	 */
	public void setForest(float forest) {
		this.forest = forest;
	}
	
	
}
