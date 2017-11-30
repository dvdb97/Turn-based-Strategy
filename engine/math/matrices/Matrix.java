package math.matrices;


/**
 * abstract class representing a matrix or a vector
 * (which is a special kind of a matrix)
 */

public abstract class Matrix {
	
	/** @param contains the elements of a matrix */
	private float[] data;
	
	//---------------------------- calculus ---------------------------
	
	
	/**
	 * 
	 * @param m Matrix that will be added to this matrix
	 * @return returns itself after the addition
	 */
	protected Matrix plus(Matrix m) {
		
		for (int i=0; i<data.length; i++) {
			this.data[i] += m.data[i];
		}
		
		return this;
		
	}
	
	/**
	 * 
	 * @param m Matrix that will be subtracted of this matrix
	 * @return returns itself after the subtraction
	 */
	protected Matrix minus(Matrix m) {
		
		for (int i=0; i<data.length; i++) {
			this.data[i] -= m.data[i];
		}
		
		return this;
		
	}
	
	/**
	 * 
	 * @param m the other factor of the dot product
	 * @return returns the dot product of this and m
	 */
	protected float scalar(Matrix m) {
		
		float result = 0;
		
		for (int i=0; i<data.length; i++) {
			result += this.data[i]*m.data[i];
		}
		
		return result;
		
	}
	
	
	
}
