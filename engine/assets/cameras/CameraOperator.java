package assets.cameras;

import math.matrices.Matrix33f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

import static utils.Const.HALF_PI;
import static math.MathUtils.*;

public class CameraOperator extends Camera {
	
	private static final float PI = (float)Math.PI;
	
	public CameraOperator() {
		super();
	}

	public CameraOperator(Vector3f position, Vector3f rotation, float zoom) {
		super(position, rotation, zoom);
		// TODO Auto-generated constructor stub
	}


	public CameraOperator(Vector3f position) {
		super(position);
		// TODO Auto-generated constructor stub
	}


	/**
	 * 
	 * Moves the camera in the given direction by interpolating the direction over time
	 * 
	 * @param dx The x direction
	 * @param dy The y direction
	 * @param dz The z direction
	 * @param t The given time
	 */
	public void move(float dx, float dy, float dz, float t) {
		//TODO
	}
	
	
	/**
	 * 
	 * Moves the camera in the given direction by interpolating the direction over time
	 * 
	 * @param position The direction
	 * @param t The time for the movement
	 */	
	public void move(Vector3f direction, float t) {
		//TODO
	}
	
	
	/**
	 * 
	 * Moves the camera to the given position by interpolating the direction over time
	 * 
	 * @param position The final position
	 *
	 * @param t The time for the movement
	 */
	public void moveTo(Vector3f position, float t) {
		//TODO
	}
	
	
	public void lookAt(Vector3f point) {
		Vector3f pointViewSpace = this.getViewMatrix().times(new Vector4f(point, 1.0f)).toVector3f();
		pointViewSpace.normalize();
		
		//pointViewSpace.print();
		
		//Minus PI because the vector (0, 0, -1) should be 0° pitch in our right-handed coordinate system
		float pitch = (float)Math.atan2(pointViewSpace.getB(), pointViewSpace.getC()) - PI;
		float yaw = (float)Math.atan2(pointViewSpace.getA(), pointViewSpace.getC());
		
		//System.out.println("Pitch: " + Math.toDegrees(pitch) + " bzw. " + (pitch / Math.PI) + " pi");
		//System.out.println("Yaw: " + Math.toDegrees(yaw) + " bzw. " + (yaw / Math.PI) + " pi");
		
		this.setRotation(new Vector3f(pitch, yaw, 0.0f));
	}
	
	
	public void lookAt(Vector3f position, Vector3f point) {
		this.moveTo(position);
		lookAt(point);
	}
	
	
	public void moveBackward(float distance) {
		//Compute the direction the camera is looking to 
		Vector3f viewPoint = new Matrix33f(this.getInvertedViewMatrix()).times(new Vector3f(0.0f, 0.0f, -1.0f));
		Vector3f viewDirection = viewPoint.minus(this.getPosition()).normalize();
		
		//Move the camera in the opposite direction of the viewDirection
		this.moveTo(viewDirection.negated().times(distance));
	}
	
	
	public void moveForward(float distance) {
		//Compute the direction the camera is looking to 
		Vector3f viewPoint = new Matrix33f(this.getInvertedViewMatrix()).times(new Vector3f(0.0f, 0.0f, -1.0f));
		Vector3f viewDirection = viewPoint.minus(this.getPosition()).normalize();
		
		//Move the camera in the direction of the viewDirection
		this.moveTo(viewDirection.times(distance));
	}

}
