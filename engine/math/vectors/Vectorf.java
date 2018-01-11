package math.vectors;


import math.matrices.Matrixf;


abstract class Vectorf extends Matrixf {
	
	//------------------------- constructor ----------------------------
	protected Vectorf(int length) {
		super(length);
	}
	
	public float norm() {
		
		float result = 0;
		
		for (int i=0; i<getDataLength(); i++) {
			result += get(i)*get(i);
		}
		result = (float)Math.sqrt(result);
		
		return result;
		
	}
	
	protected void voidNormalize() {
		
		this.times(1/norm());
		
	}
	
	/**
	 * prints out the vector
	 */
	public void print() {
		
		for (int i=0; i<getDataLength(); i++) {
			System.out.print(get(i) + "  ");
		}
		
		System.out.println();
		
	}
	
}
