package assets.light;


import math.MathUtils;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import graphics.matrices.TransformationMatrix;


public class LightSource {
	
	private Vector3f direction;
	
	private Vector3f color;
	
	private Matrix44f lightViewMatrix;
	private boolean changes;
	
	//****************************** constructor **************************************************
	
	
	public LightSource(Vector3f direction, Vector3f color) {
		
		this.direction = direction;
		this.color = color;
		
		changes = true;
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
		changes = true;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
		changes = true;
	}
	
	
	/**
	 * 
	 * Generates a view matrix for this light source. As it is a directional light without a position
	 * it will place the lights position at the boundary of the view box
	 * 
	 * @return Returns a view matrix for this light source. 
	 */
	public Matrix44f generateLightViewMatrix() {
		
		if (!changes) {
			return lightViewMatrix;
		}
		
		float max = MathUtils.max(direction.getA(), direction.getB(), direction.getC());
		float r = 1f / max;
		
		Vector3f lightPosition = direction.times(r).negatedCopy();
		
		lightViewMatrix = new TransformationMatrix(lightPosition.negatedCopy(), direction.negatedCopy(), 1f);
		
		changes = false;
		
		return lightViewMatrix;
		
	}
	
}
