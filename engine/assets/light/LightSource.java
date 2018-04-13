package assets.light;

import math.MathUtils;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;


public class LightSource {
	
	private Vector3f direction;
	
	private Vector3f color;
	
	//****************************** constructor **************************************************
	
	
	public LightSource(Vector3f direction, Vector3f color) {
		
		this.direction = direction;
		this.direction.normalize();
		this.color = color;
		
	}
	
	
	//****************************** get & set ****************************************************
	
	
	public Vector3f getDirection() {
		return direction;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	
}
