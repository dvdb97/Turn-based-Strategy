package lwlal;

public class Vector3f extends Vectorf {
	
	private final static int LENGTH = 3;
	
	//----------------------- constructor ----------------------
	private Vector3f(Vector3f v) {
		
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
	 */
	public Vector3f(float a, float b, float c) {
		
		super(LENGTH);
		
		set(0, a);
		set(1, b);
		set(2, c);
	}
	
	
	
	//------------------------- math methods -------------------
	
	/**
	 * Vector addition
	 * 
	 * @param v summand
	 * @return returns itself after a component wise addition
	 */
	public Vector3f plus(Vector3f v) {
		super.plus(v);
		return this;
	}
	
	/**
	 * Vector subtraction
	 * 
	 * @param v subtrahend
	 * @return returns itself after a component subtraction
	 */
	public Vector3f minus(Vector3f v) {
		super.minus(v);
		return this;
	}
	
	/**
	 * Scalar Multiplication
	 * 
	 * @param r the scalar, every component is multiplicated with
	 * @return returns itself after the multiplication
	 */
	public Vector3f times(float r) {
		super.times(r);
		return this;
	}
	
	/**
	 * Dot Product (Inner Product)
	 * 
	 * @param v factor
	 * @return the dot product
	 */
	public float scalar(Vector3f v) {
		return super.scalar(v);
		
	}
	
	/**
	 * Normalization
	 * (changes the length of a vector to 1, while keeping the direction the same)
	 * 
	 * @return returns itself after the normalization
	 */
	public Vector3f normalize() {
		super.voidNormalize();
		return this;
	}
	
	/**
	 * Negation
	 * 
	 * @return itself, multiplicated with -1
	 */
	public Vector3f negated() {
		super.voidNegated();
		return this;
	}
	
	/**
	 * Cross product (vector product)
	 * 
	 * @param v factor
	 * @return returns a new vector perpendicular to both, v and this
	 */
	public Vector3f cross(Vector3f v) {
		
		return new Vector3f(this.getB()*v.getC() - this.getC()*v.getB(),
							this.getC()*v.getA() - this.getA()*v.getC(),
							this.getA()*v.getB() - this.getB()*v.getA());
	}
	
	
	
	//---------------------- other methods -------------------
	
	/**
	 * @return returns a deep copy of this vector
	 */
	public Vector3f copyOf() {
		return new Vector3f(get(0), get(1), get(2));
	}
	
	/**
	 * 
	 * @param d fourth component of the new vector
	 * @return returns a new vector, copying this one and adding d as an additional component
	 */
	public Vector4f toVector4f(float d) {
		return new Vector4f(get(0), get(1), get(2), d);
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
	
}
