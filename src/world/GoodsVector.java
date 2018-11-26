package world;

public class GoodsVector {
	
	//--------------------- fields --------------------------------
	private String[] nameOfGoods;
	private int[] goods;
	private static final int NUM_GOODS = 1;
	
	//---------------------- constructor --------------------------
	
	/**
	 * not yet implemented
	 */
	public GoodsVector() {
		System.out.println("the constructor of world.GoodsVector is not implemented");
	}
	
	//-------------------- methods --------------------------------
	public void add(GoodsVector goodsVector) {
		
		for (int g=0; g<NUM_GOODS; g++) {
			this.goods[g] += goodsVector.getGood(g);
		}
		
	}
	
	//------------------------- get & set -------------------------
	public int getGood(int i) {
		return goods[i];
	}
	
	public void setGood(int i, int value) {
		goods[i] = value;
	}
	
}
