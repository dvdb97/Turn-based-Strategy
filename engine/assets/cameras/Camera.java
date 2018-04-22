package assets.cameras;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;

import static math.Trigonometry.*;

import interaction.input.KeyInput;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;

public class Camera {
	
	//The current position of the camera
	private Vector3f position;
	
	//The current view direction of the camera
	private Vector3f viewDirection;
	
	//The up vector for creating a view matrix
	private Vector3f upVector;
	
	//the current view matrix for this camera
	private Matrix44f viewMatrix;
	
	
	public Camera() {
		this.position = new Vector3f(0.0f, 0.0f, 0.0f);
		this.viewDirection = new Vector3f(0.0f, 0.0f, -1.0f);
		this.upVector = new Vector3f(0.0f, 1.0f, 0.0f);
	}
	
	
	public Camera(Vector3f position, Vector3f viewDirection) {
		this.position = position.copyOf();
		this.viewDirection = viewDirection.normalizedCopy();
		this.upVector = new Vector3f(0.0f, 1.0f, 0.0f);
	}
	
	
	//******************************** core functions ********************************
	
	
	/**
	 * 
	 * Moves the camera in the view direction (distance * viewDirection)
	 * 
	 * @param distance The distance
	 */
	public void forward(float distance) {
		this.move(viewDirection.times(distance));
	}
	
	
	/**
	 * 
	 * Moves the camera against the view direction (distance * -viewDirection)
	 * 
	 * @param distance
	 */
	public void backward(float distance) {
		this.move(-viewDirection.getA() * distance, -viewDirection.getB() * distance, -viewDirection.getC() * distance);
	}
	
	
	/**
	 * 
	 * Moves the camera in the given direction
	 * 
	 * @param dx The x direction
	 * @param dy The y direction
	 * @param dz The z direction
	 */
	public void move(float dx, float dy, float dz) {
		this.position.setA(position.getA() + dx);
		this.position.setB(position.getB() + dy);
		this.position.setC(position.getC() + dz);
	}
	
	
	/**
	 * 
	 * Moves the camera in the given direction
	 * 
	 * @param direction The direction of the camera movement
	 */
	public void move(Vector3f direction) {
		this.move(direction.getA(), direction.getB(), direction.getC());
	}
	
	
	/**
	 * 
	 * Moves the camera to the given position
	 * 
	 * @param position The new position of the camera
	 */
	public void moveTo(Vector3f position) {
		if (!this.position.equals(position))
			this.position = position.copyOf();
	}
	
	
	/**
	 * 
	 * Turns the camera to look at the given point
	 * 
	 * @param point The new point the camera should look at
	 */
	public void lookAt(Vector3f point) {
		this.viewDirection = point.minus(position).normalize();
	}
	
	
	/**
	 *
	 * Turns the camera to look in the given direction
	 *  
	 * @param viewDirection The new view direction of the camera
	 */
	public void lookInDir(Vector3f viewDirection) {
		if (!this.position.equals(viewDirection))
			this.viewDirection = viewDirection.normalizedCopy();		
	}
	
	
	/**
	 * 
	 * Moves the camera to the given position and turns it so that is looks at the given point
	 * 
	 * @param position The new position of the camera 
	 * @param point A point the camera should look at
	 */
	public void lookAt(Vector3f position, Vector3f point) {
		this.moveTo(position);
		this.lookAt(point);
	}
	
	
	/**
	 * 
	 * Moves the camera to the given position and turns it so that is looks in the given direction
	 * 
	 * @param position The new position of the camera
	 * @param viewDirection The new view direction of the camera
	 */
	public void lookInDir(Vector3f position, Vector3f viewDirection) {
		moveTo(position);
		lookInDir(viewDirection);
	}
	
	
	/**
	 * 
	 * Rotates the camera around the x-axis
	 * 
	 * @param radians The rotation in radians
	 */
	public void pitch(float radians) {
		
		Matrix33f rotationMatrix = new Matrix33f(1f, 0f, 0f, 0f, cos(radians), -sin(radians), 0f, sin(radians), cos(radians));
		
		this.viewDirection = rotationMatrix.times(viewDirection).normalize();
		
		this.upVector = rotationMatrix.times(upVector);
		
	}
	
	
	/**
	 * 
	 * Rotates the camera around the x-axis
	 * 
	 * @param degrees The rotation in degrees
	 */
	public void pitch(int degrees) {
		
		float radians = (float)(degrees / 360) * 2 * PI;
		
		pitch(radians);
		
	}
	
	
	/**
	 * 
	 * Rotates the camera around the y-axis
	 * 
	 * @param radians The rotation in radians
	 */
	public void yaw(float radians) {
		
		Matrix33f rotationMatrix = new Matrix33f(cos(radians), 0f, sin(radians), 0f, 1f, 0f, -sin(radians), 0f, cos(radians));
	
		this.viewDirection = rotationMatrix.times(viewDirection).normalize();
		
		this.upVector = rotationMatrix.times(upVector);
		
	}
	
	
	/**
	 * 
	 * Rotates the camera around the y-axis
	 * 
	 * @param degrees The rotation in degrees
	 */
	public void yaw(int degrees) {
		
		float radians = (float)(degrees / 360) * 2 * PI;
		
		yaw(radians);
		
	}
	
	
	/**
	 * 
	 * Rotates the camera around the x- and y-axis
	 * 
	 * TODO: Implement roll
	 * 
	 * @param pitch The degree of the rotation around the x-axis
	 * @param yaw The degree of the rotation around the y-axis
	 */
	public void rotate(float pitch, float yaw) {
		pitch(pitch);
		pitch(yaw);
	}
	
	
	public Vector3f getPosition() {
		return position;
	}


	public Vector3f getViewDirection() {
		return viewDirection;
	}
	
	
	protected Vector3f getUpVector() {
		return upVector;
	}
	
	
	//****************************************** matrix handling ******************************************


	/**
	 *  
	 * @return Generates a view matrix for this camera
	 */
	public Matrix44f getViewMatrix() {
		return generateViewMatrixA(position, viewDirection, upVector);
	}
	
	
	/**
	 * 
	 * @return Generates the multiplicative inverse of the view matrix
	 */
	public Matrix44f getInvertedViewMatrix() {
		return generateViewMatrixA(position.normalizedCopy(), viewDirection.negatedCopy(), upVector);
	}
	
	
	/**
	 * 
	 * @param eye The position of the camera
	 * @param viewDirection The view direction of the camera
	 * @param up A global up vector
	 * @return Returns a view matrix
	 */
	public static Matrix44f generateViewMatrixA(Vector3f eye, Vector3f viewDirection, Vector3f up) {
		
		Vector3f z = viewDirection.normalizedCopy();
		z.negated();
		
		up = up.normalizedCopy();		
		
		Vector3f x = up.cross(z).normalize();;			
		
		Vector3f y = z.cross(x).normalize();
		
		Matrix44f orientation = new Matrix44f(x.getA(), x.getB(), x.getC(), 0f, 
											  y.getA(), y.getB(), y.getC(), 0f, 
											  z.getA(), z.getB(), z.getC(), 0f, 
											  0f, 0f, 0f, 1f);
		
		Matrix44f translation = new Matrix44f(1f, 0f, 0f, -eye.getA(),
											  0f, 1f, 0f, -eye.getB(),
											  0f, 0f, 1f, -eye.getC(),
											  0f, 0f, 0f, 1f);
				
		return orientation.times(translation);
		
	}
	
	
	/**
	 * 
	 * @param eye The position of the camera
	 * @param center A point the camera looks at
	 * @param up A global up vector
	 * @return Returns a view matrix
	 */
	public static Matrix44f generateViewMatrixB(Vector3f eye, Vector3f center, Vector3f up) {
		
		return generateViewMatrixA(eye, center.minus(eye), up);
		
	}

}