package assets.light;

import assets.cameras.Camera;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;


public class DirectionalLight extends Camera {
	
	private Vector3f color;
	
	//****************************** constructor ******************************
	
	
	public DirectionalLight(Vector3f direction, Vector3f color) {
		super(direction.times(-1f), direction);
		this.color = color;
	}
	
	
	//****************************** Transformations ******************************
	
	
	@Override
	public Matrix44f getViewMatrix() {
		return generateViewMatrixA(getPosition(), getViewDirection(), getUpVector());
	}
	
	
	public Vector3f getColor() {
		return color;
	}


	public void setColor(Vector3f color) {
		this.color = color;
	}
	
}
