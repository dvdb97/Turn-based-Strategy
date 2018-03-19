package assets.cameras;

import static utils.Const.HALF_PI;

import math.matrices.Matrix33f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.matrices.ViewMatrix;

public class Camera {
	
	//The initial position of the camera
	private Vector3f position;
	
	//The initial view direction of the camera
	private Vector3f viewDirection;
	

	//The current translation of this camera
	private Vector3f translation;
	
	/*
	 * The rotation of this camera: roll, pitch, yaw
	 * 
	 * roll: up and down
	 * 
	 * yaw: right and left
	 * 
	 * roll: tilt
	 * 
	 */
	private Vector3f rotation;
	
	private float zoom;
	
	
	private ViewMatrix viewMatrix;
	
	
	public Camera() {
		init();
		
		this.translation = new Vector3f(0.0f, 0.0f, 0.0f);
		this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
		this.zoom = 1.0f;
		
		viewMatrix = new ViewMatrix(this);
	}
	
	
	public Camera(Vector3f position) {
		//TODO
	}
	
	
	public Camera(Vector3f position, Vector3f rotation, float zoom) {
		//TODO
	}
	
	
	private void init() {
		this.position = new Vector3f(0.0f, 0.0f, 1.0f);
		this.viewDirection = new Vector3f(0.0f, 0.0f, -1.0f);
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
	 * @param direction The direction of the cameras translation
	 * 
	 */
	public void move(Vector3f direction) {
		translation.plusEQ(direction);
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
	 * Moves the camera to the given position
	 * 
	 * @param position The new position
	 */
	public void moveTo(Vector3f position) {
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
		//TODO: Not needed yet
	}
	
	
	/**
	 * 
	 * @return Returns the current translation of the camera
	 */
	public Vector3f getTranslation() {
		return translation.copyOf();
	}
	
	
	/**
	 * 
	 * Increases the camera's x translation.
	 * 
	 * @param dx The value by which the x translation shall be increased
	 */
	public void incrXTranslation(float dx) {
		setXTranslation(getXTranslation() + dx);		
	}
	
	
	/**
	 * 
	 * Changes the x translation of the camera
	 * 
	 * @param x The new x value
	 */
	public void setXTranslation(float x) {
		translation.setA(x);
	}
	
	
	/**
	 * 
	 * @return Returns the current x translation
	 */
	public float getXTranslation() {
		return translation.getA();
	}
	
	
	/**
	 * 
	 * Increases the camera's y translation.
	 * 
	 * @param dy The value by which the y translation shall be increased
	 */
	public void incrYTranslation(float dy) {
		setYTranslation(getYTranslation() + dy);		
	}
	
	
	/**
	 * 
	 * Changes the y translation of the camera
	 * 
	 * @param y The new y value
	 */
	public void setYTranslation(float y) {
		translation.setB(y);
	}
	
	
	/**
	 * 
	 * @return Returns the current y translation
	 */
	public float getYTranslation() {
		return translation.getB();
	}
	
	
	/**
	 * 
	 * Increases the camera's z translation.
	 * 
	 * @param dz The value by which the z translation shall be increased
	 */
	public void incrZTranslation(float dz) {
		setZTranslation(getZTranslation() + dz);		
	}
	
	
	/**
	 * 
	 * Changes the z translation of the camera
	 * 
	 * @param z The new z value
	 */
	public void setZTranslation(float z) {
		translation.setC(z);
	}
	
	
	/**
	 * 
	 * @return Returns the current z translation
	 */
	public float getZTranslation() {
		return translation.getC();
	}
	
	
	//******************************** Position ********************************
	
	
	/**
	 * 
	 * @return Returns the current position of the camera in world space
	 */
	public Vector3f getPositionWorldSpace() {
		return viewMatrix.getMultiplicativeInverse().times(new Vector4f(position, 1.0f)).toVector3f();
	}
	
	
	/**
	 * 
	 * @return Returns the current position of the camera in view space
	 */
	public Vector3f getPositionViewSpace() {
		return position;
	}
	
	
	/**
	 * 
	 * @return Returns the current view direction of the camera in world space
	 */
	public Vector3f getViewDirectionWorldSpace() {
		return new Matrix33f(viewMatrix.getMultiplicativeInverse()).times(viewDirection);
	}
	
	
	public Vector3f getViewDirectionViewSpace() {
		return viewDirection;
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
	
	
	public void lookAt(Vector3f point) {
		//Makes the camera look at the given point	
	}
	
	
	public void lookAt(Vector3f position, Vector3f point) {
		//Makes the camera look at the given point from the given position
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
		rotation.setA(roll);
	}
	
	
	/**
	 * 
	 * @return Returns the current value of roll
	 */
	public float getRoll() {
		return rotation.getA();
	}	
	
	
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
		rotation.setB(pitch);		
	}
	
	
	/**
	 * 
	 * @return Returns the current value of pitch
	 */
	public float getPitch() {
		return rotation.getC();
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
		rotation.setC(yaw);
	}
	
	
	/**
	 * 
	 * @return Returns the current value of yaw
	 */
	public float getYaw() {
		return rotation.getC();
	}

}