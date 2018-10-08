package assets.light;

import assets.cameras.Camera;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;


public class DirectionalLight extends Camera {
	
	private Vector3f color;
	
	
	//****************************** constructor ******************************
	
	
	public DirectionalLight(Vector3f color) {
		super(new Vector3f(0f, 0f, 1f), new Vector3f(0f, 0f, -1f), ProjectionType.ORTHOGRAPHIC);
		this.color = color;
	}
	
	
	public DirectionalLight(Vector3f direction, Vector3f color) {
		super(direction.times(-1f), direction, ProjectionType.ORTHOGRAPHIC);
		this.color = color;
	}
	
	
	//****************************** Transformations ******************************
	
	
	public void fitToFrustrum(Camera camera) {
		//Take the center point of the camera's view frustrum.
		Vector3f center = camera.getFrustrumCenter();
		
		//The distance between the near and far clipping plane of the camera's projection matrix.
		float d = camera.getProjectionMatrix().getFarPlane() - camera.getProjectionMatrix().getNearPlane();
		
		//This light's light direction.
		Vector3f ld = getLightDirection();
		
		//Define a position for this light source.
		Vector3f position = new Vector3f(center.getA() + d * -ld.getA(), center.getB() + d * -ld.getB(), center.getC() + d * -ld.getC());
		this.setPosition(position);
		
		
		float minX, minY, minZ, maxX, maxY, maxZ;
		minX = minY = minZ = Float.MAX_VALUE;
		maxX = maxY = maxZ = Float.MIN_VALUE;
		
		Vector4f[] corners = camera.getFrustrumCorners();
		
		for (int i = 0; i < corners.length; ++i) {
			corners[i] = this.getViewMatrix().times(corners[i]);
			
			if (corners[i].getA() < minX)
				minX = corners[i].getA();
			
			if (corners[i].getA() > maxX)
				maxX = corners[i].getA();
			
			if (corners[i].getB() < minY)
				minY = corners[i].getB();
			
			if (corners[i].getB() > maxY)
				maxY = corners[i].getB();
			
			if (corners[i].getC() < minZ)
				minZ = corners[i].getC();
			
			if (corners[i].getC() > maxZ)
				maxZ = corners[i].getC();
		}
		
		System.out.println("X: " + minX + " to " + maxX + "; " + 
						   "Y: " + minY + " to " + maxY + "; " +
						   "Z: " + minZ + " to " + maxZ);
		
		this.setOrthographicProjection(maxZ, minZ, minX, maxX, minY, maxY);		
	}
	
	
	public Vector3f getLightDirection() {
		return getViewDirection();
	}
	
	
	public Vector3f getColor() {
		return color;
	}


	public void setColor(Vector3f color) {
		this.color = color;
	}
	
}
