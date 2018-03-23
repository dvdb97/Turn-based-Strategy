package assets.light;


import math.MathUtils;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import assets.cameras.CameraOperator;
import graphics.matrices.TransformationMatrix;


public class LightSource {
	
	private Vector3f direction;
	
	private Vector3f color;
	
	private CameraOperator lightCamera;
	
	//****************************** constructor **************************************************
	
	
	public LightSource(Vector3f direction, Vector3f color) {
		
		this.direction = direction;
		this.direction.normalize();
		this.color = color;
		
		this.lightCamera = new CameraOperator();
		this.lightCamera.lookAt(direction.negatedCopy().times(2f), direction);
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
	
	public Matrix44f getLightViewMatrix() {
		return lightCamera.getViewMatrix();	
	}
	
}
