package math.vectors;


public class Vector2i {
	
	private int x;
	
	private int y;
	
	
	public Vector2i() {
		this(0, 0);
	}
	
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public Vector2i subtract(Vector2i vector) {
		return new Vector2i(this.getX() - vector.getX(), this.getY() - vector.getY());
	}
	
	
	public Vector2i add(Vector2i vector) {
		return new Vector2i(this.getX() + vector.getX(), this.getY() + vector.getY());
	}
	
	
	public void multiplyEQ(float value) {
		
		this.setX((int)(value * this.getX()));
		
		this.setY((int)(value * this.getY())); 
		
	}
	
	
	public Vector2i multiply(float value) {
		return new Vector2i((int)(value * this.getX()), (int)(value * this.getY()));
	}
	


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
	

}
