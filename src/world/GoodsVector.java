package world;

import utils.Const;

public class GoodsVector {
	
	public int[] goods;
	
	//------------------------     check    ---------------------
	public boolean canPay(int[] cost) {
		
		//create a copy of this.goods
		GoodsVector test = new GoodsVector(goods);
		
		//pay
		test.add(cost);
		
		//check if any amount is negative
		boolean canPay = true;
		for (int g=0; g<test.goods.length; g++) {
			if (test.goods[g] < 0) {
				canPay = false;
				break;
			}
		}
		
		return canPay;
	}
	
	//-------------------------    maths    ---------------------
	public void add(GoodsVector v) {
		
		for (int g=0; g<Const.NUM_GOODS; g++) {
			goods[g] += v.goods[g];
		}
		
	}
	
	public void add(int[] v) {
		
		for (int g=0; g<Const.NUM_GOODS; g++) {
			goods[g] += v[g];
		}
		
	}
	
	public void transmitTo(GoodsVector recipient, GoodsVector amounts) {
		
		recipient.add(amounts);
		this.substract(amounts);
		
	}
	
	private void substract(GoodsVector v) {
		
		for (int g=0; g<Const.NUM_GOODS; g++) {
			goods[g] -= v.goods[g];
		}
		
	}
	
	//-------------------------   display   ---------------------
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("(" + goods[0]);
		for (int g=1; g<goods.length; g++) {
			sb.append(", " + goods[g]);
		}
		sb.append(")");
		
		
		return sb.toString();
	}
	
	//------------------------- constructor ---------------------
	public GoodsVector(int[] amounts) {
		
		this();
		this.add(amounts);
		
	}
	
	public GoodsVector() {
		
		goods = new int[Const.NUM_GOODS];
	}
	
}
