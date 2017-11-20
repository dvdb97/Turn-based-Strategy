package lwlal;

public class Vector2f extends Vectorf {

	private Vector2f() {
		super(2);
		// TODO Auto-generated constructor stub
	}
	
	
	public Vector2f(float a, float b) {
		this();
	}
	
	
	@Override
	protected Matrixf copyOf() {
		// TODO Auto-generated method stub
		return new Vector2f(getA(), getB());
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
