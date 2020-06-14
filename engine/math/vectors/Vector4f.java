package math.vectors;


public class Vector4f extends Vectorf {
	
	private final static int LENGTH = 4;
	
	//----------------------- constructor ----------------------
	private Vector4f(Vector4f v) {
		
		super(LENGTH);
		
		for (int i=0; i<v.getDataLength(); i++) {
			set(i, v.get(i));
		}
		
	}
	
	/**
	 * 
	 * @param a first component of the vector
	 * @param b second component of the vector
	 * @param c third component of the vector
	 * @param d fourth component of the vector
	 */
	public Vector4f(float a, float b, float c, float d) {
		
		super(LENGTH);
		
		set(0, a);
		set(1, b);
		set(2, c);
		set(3, d);
	}
	
	
	public Vector4f(Vector3f vec, float d) {
		super(LENGTH);
		
		setA(vec.getA());
		setB(vec.getB());
		setC(vec.getC());
		setD(d);
		
	}
	
	//------------------------- math methods -------------------
	
	/**
	 * Vector addition
	 * 
	 * @param v summand
	 * @return returns itself after a component wise addition
	 */
	public Vector4f plus(Vector4f v) {
		super.plus(v);
		return this;
	}
	
	
	/**
	 * Vector subtraction
	 * 
	 * @param v subtrahend
	 * @return returns itself after a component subtraction
	 */
	public Vector4f minus(Vector4f v) {
		super.minus(v);
		return this;
	}
	
	/**
	 * Scalar Multiplication
	 * 
	 * @param r the scalar, every component is multiplied with
	 * @return returns itself after the multiplication
	 */
	public Vector4f times(float r) {
		super.times(r);
		return this;
	}
	
	/**
	 * Dot Product (Inner Product)
	 * 
	 * @param v factor
	 * @return the dot product
	 */
	public float scalar(Vector4f v) {
		return super.scalar(v);
		
	}
	
	/**
	 * Normalization
	 * (changes the length of a vector to 1, while keeping the direction the same)
	 * 
	 * @return returns itself after the normalization
	 */
	public Vector4f normalize() {
		super.voidNormalize();
		return this;
	}
	
	/**
	 * Negation
	 * 
	 * @return itself, multiplied with -1
	 */
	public Vector4f negated() {
		super.voidNegated();
		return this;
	}
	
	
	
	//---------------------- other methods -------------------
	
	/**
	 * @return returns an array version of this vector
	 */
	public float[] toArray() {
		return new float[] {get(0), get(1), get(2), get(3)};
	}
	
	/**
	 * @return returns a deep copy of this vector
	 */
	public Vector4f copyOf() {
		return new Vector4f(get(0), get(1), get(2), get(3));
	}
	
	/**
	 * @return returns a new 3D vector, cutting of the fourth component
	 */
	public Vector3f toVector3f() {
		return new Vector3f(get(0), get(1), get(2));
	}
	
	public void random() {
		
		this.setA((float)Math.random() * 2f - 1f);
		this.setB((float)Math.random() * 2f - 1f);
		this.setC((float)Math.random() * 2f - 1f);
		this.setD((float)Math.random() * 2f - 1f);
	}
	
	
	@Override
	public String toString() {
		return "(" + getA() + ", " + getB() + ", " + getC() + ", " + getD() + ")";
	}	
	
	
	//------------------------- Get & Set ----------------------
	

	/**
	 * @return returns the first component of this vector
	 */
	public float getA() {
		return get(0);
	}
	
	/**
	 * @param a first component of the vector
	 */
	public void setA(float a) {
		set(0, a);
	}
	
	/**
	 * @return returns the second component of this vector
	 */
	public float getB() {
		return get(1);
	}
	
	/**
	 * @param b second component of the vector
	 */
	public void setB(float b) {
		set(1, b);
	}
	
	/**
	 * @return returns the third component of this vector
	 */
	public float getC() {
		return get(2);
	}
	
	/**
	 * @param c third component of the vector
	 */
	public void setC(float c) {
		set(2, c);
	}
	
	/**
	 * @return returns the fourth component of this vector
	 */
	public float getD() {
		return get(3);
	}
	
	/**
	 * @param d fourth component of the vector
	 */
	public void setD(float d) {
		set(3, d);
	}	
	
}
