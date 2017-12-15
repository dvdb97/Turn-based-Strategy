package math.matrices;

/**
 * abstract class representing a matrix or a vector
 * (which is a special kind of a matrix)
 */

public abstract class Matrixf {
	
	/** @param contains the elements of a matrix */
	private float[] data;
	
	//---------------------------- constructor -----------------------
	protected Matrixf(int length) {
		
		data = new float[length];
	}
	
	
	//-------------------------------- generate -----------------------------
	
	
	/** 
	 * generates a random matrix. Usefull for testing
	 * 
	 * @param range the range of the values is 0 - range.
	 */
	public void generateRandomData(float range) {
		
		for (int i = 0; i < data.length; ++i) {
			
			data[i] = (float)Math.random() * range;
			
		}
		
	}
	
	
	//---------------------------- calculus ---------------------------
	
	
	protected void plus(Matrixf m) {
		
		for (int i=0; i<data.length; i++) {
			this.data[i] += m.data[i];
		}
		
		
	}
	
	protected void minus(Matrixf m) {
		
		for (int i=0; i<data.length; i++) {
			this.data[i] -= m.data[i];
		}
		
	}
	
	protected float scalar(Matrixf m) {
		
		float result = 0;
		
		for (int i=0; i<data.length; i++) {
			result += this.data[i]*m.data[i];
		}
		
		return result;
	}
	
	protected void times(Float r) {
		
		for (int i=0; i<data.length; i++) {
			this.data[i] *= r;
		}
		
	}
	
	protected void voidNegated() {
		times(new Float(-1));
	}	
	
	
	//-------------------------------- convert -----------------------------
	
	/**
	 * @return returns an array version of this matrix/vector
	 */
	public float[] toArray() {
		
		float[] array = new float[data.length];
		
		for (int i=0; i<data.length; i++) {
			array[i] = data[i];
		}
		
		return array;
	}
	
	protected abstract void print();
	
	protected abstract Matrixf copyOf();
	
	//---------------------------------- compare ------------------------------
	
	/**
	 * checks if both objects contain the same data
	 * 
	 * @param m tested Matrix
	 * @return true if 
	 */
	public boolean equivalentTo(Matrixf m) {
		
		if (this.data.length != m.data.length) {
			return false;
		}
		
		for (int i=0; i<data.length; i++) {
			if (this.data[i] != m.data[i]) {
				return false;
			}
		}
		
		return true;
		
	}
	
	//-------------------------------- get & set ---------------------------
	
	/**
	 * 
	 * @param i index of the wanted element
	 * @return element of float[] data at position i 
	 */
	protected float get(int i) {
		
		return data[i];
		
	}
	
	protected void set(int i, float f) {
		data[i] = f;
	}
	
	protected int getDataLength() {
		return data.length;
	}
	
}
