package math.vectors;

public class Vector2f extends Vectorf {

	private Vector2f() {
		super(2);
		// TODO Auto-generated constructor stub
	}
	
	
	public Vector2f(float a, float b) {
		this();
		
		setA(a);
		setB(b);
	}
	
	
	/**
	 * Vector addition
	 * 
	 * @param v summand
	 * @return returns itself after a component wise addition
	 */
	public Vector2f plusEQ(Vector2f v) {
		super.plus(v);
		return this;
	}
	
	
	/**
	 * Vector addition
	 * 
	 * @param v summand
	 * @return returns a new Vector
	 */
	public Vector2f plus(Vector2f v) {
		Vector2f output = this.copyOf();
		
		return output.plusEQ(v);
		
	}
	
	/**
	 * @return returns a deep copy of this vector
	 */
	public Vector2f copyOf() {
		return new Vector2f(get(0), get(1));
	}
	
	
	public float dot(Vector2f vec) {
		return this.getA() * vec.getA() + this.getB() * vec.getB();
	}
	
	
	public Vector2f times(float value) {
		return new Vector2f(value * getA(), value * getB());
	}
	
	
	public Vector2f timesEQ(float value) {
		this.setA(value * getA());
		this.setB(value * getB());
		
		return this;
	}
	
	
	public Vector2f normalized() {
		return timesEQ(1f / norm());
	}
	
	
	public void setA(float a) {
		set(0, a);
	}
	
	
	public void setB(float b) {
		set(1, b);
	}
	
	
	public float getA() {
		// TODO Auto-generated method stub
		return get(0);
	}
	
	
	public float getB() {
		// TODO Auto-generated method stub
		return get(1);
	}

}
