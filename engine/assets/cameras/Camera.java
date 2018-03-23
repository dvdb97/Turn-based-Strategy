package assets.cameras;

import static utils.Const.HALF_PI;

import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import rendering.matrices.ViewMatrix;

public class Camera {
	
	//The initial position of the camera
	private Vector3f position;
	
	/*
	 * The rotation of this camera:
	 * 
	 * pitch: up and down
	 * 
	 * roll: tilt
	 * 
	 * yaw: right and left
	 * 
	 */
	private Vector3f rotation;
	
	private float zoom;
	
	
	private ViewMatrix viewMatrix;
	private boolean updated;
	
	
	public Camera() {
		this.position = new Vector3f(0.0f, 0.0f, 0.0f);
		this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
		this.zoom = 1.0f;
		
		viewMatrix = new ViewMatrix(this);
		updated = true;
	}
	
	
	public Camera(Vector3f position) {
		//TODO
		updated = true;
	}
	
	
	public Camera(Vector3f position, Vector3f rotation, float zoom) {
		//TODO
		updated = true;
	}
	
	
	//******************************** core functions ********************************
	
	
	/**
	 * 
	 * @return Returns a reference to the view matrix for this camera
	 */
	public ViewMatrix getViewMatrix() {
		if (!updated) {
			viewMatrix.refresh();
			updated = true;
		}
		
		return viewMatrix;
	}
	
	
	/**
	 * 
	 * @return Returns the multiplicative inverse of the view matrix
	 */
	public Matrix44f getInvertedViewMatrix() {
		return viewMatrix.getMultiplicativeInverse();
	}
	
	
	//******************************** Translation ********************************
	
	/**
	 * 
	 * Moves the camera in the given direction
	 * 
	 * @param dx The value by which the x translation shall be increased
	 * @param dy The value by which the y translation shall be increased
	 * @param dz The value by which the z translation shall be increased
	 * 
	 */
	public void move(float dx, float dy, float dz) {
		this.move(new Vector3f(dx, dy, dz));
	}
	
	
	/**
	 * 
	 * Moves the camera in the given direction
	 * 
	 * @param direction The direction of the cameras movement
	 * 
	 */
	public void move(Vector3f direction) {
		position.plusEQ(direction);
		this.updated = false;
	}
	
	
	/**
	 * 
	 * Moves the camera to the given position
	 * 
	 * @param position The new position
	 */
	public void moveTo(Vector3f position) {
		this.position = position.copyOf();
		this.updated = false;
	}
	
	
	/**
	 * 
	 * @return Returns the current position of the camera
	 */
	public Vector3f getPosition() {
		return position.copyOf();
	}
	
	
	/**
	 * 
	 * Increases the camera's x translation.
	 * 
	 * @param dx The value by which the x position shall be increased
	 */
	public void incrX(float dx) {
		setX(getX() + dx);
	}
	
	
	/**
	 * 
	 * Changes the x position of the camera
	 * 
	 * @param x The new x value
	 */
	public void setX(float x) {
		position.setA(x);
		this.updated = false;
	}
	
	
	/**
	 * 
	 * @return Returns the current x position
	 */
	public float getX() {
		return position.getA();
	}
	
	
	/**
	 * 
	 * Increases the camera's y position.
	 * 
	 * @param dy The value by which the y position shall be increased
	 */
	public void incrY(float dy) {
		setY(getY() + dy);		
	}
	
	
	/**
	 * 
	 * Changes the y position of the camera
	 * 
	 * @param y The new y value
	 */
	public void setY(float y) {
		position.setB(y);
		this.updated = false;
	}
	
	
	/**
	 * 
	 * @return Returns the current y position
	 */
	public float getY() {
		return position.getB();
	}
	
	
	/**
	 * 
	 * Increases the camera's z position.
	 * 
	 * @param dz The value by which the z position shall be increased
	 */
	public void incrZ(float dz) {
		setZ(getZ() + dz);		
	}
	
	
	/**
	 * 
	 * Changes the z position of the camera
	 * 
	 * @param z The new z value
	 */
	public void setZ(float z) {
		position.setC(z);
		this.updated = false;
	}
	
	
	/**
	 * 
	 * @return Returns the current z position
	 */
	public float getZ() {
		return position.getC();
	}
	
	
	//******************************** Zoom ********************************
	
	
	/**
	 * 
	 * Increases the camera's zoom.
	 * 
	 * @param dZoom The value by which the zoom shall be increased
	 */
	public void incrZoom(float dZoom) {
		float temp = zoom + dZoom;
		
		//check bc
		if (temp > 0 && temp < 1) {
			zoom = temp;
		}
		
		this.updated = false;
	}
	
	
	/**
	 * 
	 * Changes the zoom of the camera
	 * 
	 * @param zoom The new zoom value
	 */
	public void setZoom(float zoom) {		
		//check bc
		if (zoom > 0 && zoom < 1) {
			this.zoom = zoom;
		}
		
		this.updated = false;
	}
	
	
	/**
	 * 
	 * @return Returns the current value of zoom
	 */
	public float getZoom() {
		return zoom;
	}
	
	
	//******************************** Rotation ********************************
	
	
	/**
	 * 
	 * Rotates the camera
	 * 
	 * @param dRoll The roll rotation
	 * @param dPitch The pitch rotation
	 * @param dYaw The yaw rotation
	 */
	public void rotate(float dRoll, float dPitch, float dYaw) {
		this.incrRoll(dRoll);
		this.incrPitch(dPitch);
		this.incrYaw(dYaw);		
	}
	
	
	/**
	 * 
	 * Rotates the camera 
	 * 
	 * @param dRotation The rotation
	 */
	public void rotate(Vector3f dRotation) {
		this.rotate(dRotation.getA(), dRotation.getB(), dRotation.getC());
	}
	
	
	/**
	 * 
	 * @return Returns the current rotation of the camera
	 */
	public Vector3f getRotation() {
		return rotation;
	}
	
	
	/**
	 * 
	 * @param rotation The new value for the rotation
	 */
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation.copyOf();
		this.updated = false;
	}
	
	
	/**
	 * 
	 * Increases the camera's pitch.
	 * 
	 * @param dPitch The value by which the pitch shall be increased
	 */
	public void incrPitch(float dPitch) {
		float temp = getPitch() + dPitch;
		
		//check bc
		if (temp > -HALF_PI && temp < HALF_PI) {
			setPitch(temp);
		}
	}
	
	
	/**
	 * 
	 * Changes the pitch of the camera
	 * 
	 * @param yaw The new roll value
	 */
	public void setPitch(float pitch) {
		rotation.setA(pitch);	
		this.updated = false;
	}
	
	
	/**
	 * 
	 * @return Returns the current value of pitch
	 */
	public float getPitch() {
		return rotation.getA();
	}
	
	
	/**
	 * 
	 * Increases the camera's yaw.
	 * 
	 * @param dRoll The value by which the yaw shall be increased
	 */
	public void incrYaw(float dYaw) {
		float temp = getYaw() + dYaw;
		
		//check bc
		if (temp > -HALF_PI && temp < HALF_PI) {
			setYaw(temp);
		}
	}
	
	
	/**
	 * 
	 * Changes the yaw of the camera
	 * 
	 * @param yaw The new roll value
	 */
	public void setYaw(float yaw) {
		rotation.setB(yaw);
		this.updated = false;
	}
	
	
	/**
	 * 
	 * @return Returns the current value of yaw
	 */
	public float getYaw() {
		return rotation.getB();
	}
	
	
	/**
	 * 
	 * Increases the camera's roll.
	 * 
	 * @param dRoll The value by which the roll shall be increased
	 */
	public void incrRoll(float dRoll) {
		float temp = getRoll() + dRoll;
		
		//check bc
		if (temp > 0 && temp < HALF_PI) {
			setRoll(temp);
		}
	}
	
	
	/**
	 * 
	 * Changes the roll of the camera
	 * 
	 * @param roll The new roll value
	 */
	public void setRoll(float roll) {
		rotation.setC(roll);
		this.updated = false;
	}
	
	
	/**
	 * 
	 * @return Returns the current value of roll
	 */
	public float getRoll() {
		return rotation.getC();
	}	

}