package assets.cameras;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import rendering.matrices.projectionMatrices.ProjectionMatrix;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

import static math.Trigonometry.*;

public class Camera {
	
	public enum ProjectionType {
		PERSPECTIVE, ORTHOGRAPHIC
	};
	
	//The current position of the camera
	private Vector3f position;
	
	//The current view direction of the camera
	private Vector3f viewDirection;
	
	//The up vector for creating a view matrix
	private Vector3f upVector;
	
	//The type of projection that is currently used.
	private ProjectionType projection;	
	
	
	//The camera's view matrix.
	private Matrix44f viewMatrix;
	
	//The inverse of the camera's view matrix.
	private Matrix44f invertedViewMatrix;
	
	
	//The camera's projection matrix.
	private ProjectionMatrix projectionMatrix;
	
	//The inverse of the camera's projection matrix.
	private Matrix44f invertedProjectionMatrix;
	
	//The field of view of the camera.
	private float fieldOfView = (float)Math.PI / 8f;
	
	
	//The inverse of this camera's view-projection matrix.
	private Matrix44f invertedViewProjectionMatrix;
	
		
	/**
	 * 
	 * Generates a camera with default values.
	 */
	public Camera() {
		this(ProjectionType.PERSPECTIVE);
	}
	
	
	/**
	 * 
	 * @param projection The type of projection the camera uses.
	 */
	public Camera(ProjectionType projection) {
		this.position = new Vector3f(0.0f, 0.0f, 0.0f);
		this.viewDirection = new Vector3f(0.0f, 0.0f, -1.0f);
		this.upVector = new Vector3f(0.0f, 1.0f, 0.0f);
		
		this.updateViewMatrix();
		this.setProjection(projection);
	}
	
	
	/**
	 * 
	 * @param position The camera's position.
	 */
	public Camera(Vector3f position) {
		this(position, ProjectionType.PERSPECTIVE);
	}
	
	
	/**
	 * 
	 * @param position The camera's position.
	 * @param projection The type of projection the camera uses.
	 */
	public Camera(Vector3f position, ProjectionType projection) {
		this.position = position.copyOf();
		this.viewDirection = new Vector3f(0.0f, 0.0f, -1.0f);
		this.upVector = new Vector3f(0.0f, 1.0f, 0.0f);
		
		this.updateViewMatrix();
		this.setProjection(projection);
	}
	
	
	/**
	 * 
	 * @param position The camera's position.
	 * @param viewDirection The camera's view direction.
	 */
	public Camera(Vector3f position, Vector3f viewDirection) {
		this(position, viewDirection, ProjectionType.PERSPECTIVE);
	}
	
	
	/**
	 * 
	 * @param position The camera's position.
	 * @param viewDirection The camera's view direction.
	 * @param projection The type of projection the camera uses.
	 */
	public Camera(Vector3f position, Vector3f viewDirection, ProjectionType projection) {
		this.position = position.copyOf();
		this.viewDirection = viewDirection.copyOf();
		this.upVector = new Vector3f(0.0f, 1.0f, 0.0f);
		
		this.updateViewMatrix();
		this.setProjection(projection);
	}
	
	
	//******************************** core functions ********************************
	
	
	/**
	 * 
	 * Sets the projection type of the camera to the given type
	 * generating a new projection matrix.
	 * 
	 * @param projection
	 */
	private void setProjection(ProjectionType projection) {		
		if (projection == ProjectionType.PERSPECTIVE) {
			setPerspectiveProjection(this.fieldOfView);
		} else {
			setOrthographicProjection();
		}
		
		this.invertedViewProjectionMatrix = invertedProjectionMatrix.times(invertedViewMatrix);
	}
	

	public void setPerspectiveProjection(float fieldOfView) {		
		projectionMatrix = ProjectionMatrix.perspective();
		invertedProjectionMatrix = projectionMatrix.inverse();
		
		this.projection = ProjectionType.PERSPECTIVE;
	}
	
	
	public void setPerspectiveProjection(float n, float f, float l, float r, float b, float t) {
		projectionMatrix = ProjectionMatrix.perspective(n, f, l, r, b, t);
		invertedProjectionMatrix = projectionMatrix.inverse();
		
		this.projection = ProjectionType.PERSPECTIVE;
	}
	
	
	public void setOrthographicProjection() {		
		projectionMatrix = ProjectionMatrix.orthographic();
		invertedProjectionMatrix = projectionMatrix.inverse();
		
		this.projection = ProjectionType.ORTHOGRAPHIC;
	}
	
	
	public void setOrthographicProjection(float n, float f, float l, float r, float b, float t) {
		projectionMatrix = ProjectionMatrix.orthographic(n, f, l, r, b, t);
		invertedProjectionMatrix = projectionMatrix.inverse();
		
		this.projection = ProjectionType.ORTHOGRAPHIC;
	}
	
	
	/**
	 * 
	 * @return Returns the type of projection that this camera uses.
	 */
	public ProjectionType getProjectionType() {
		return projection;
	}
	
	
	private void updateViewMatrix() {
		this.viewMatrix = generateViewMatrixA(position, viewDirection, upVector);
		this.invertedViewMatrix = generateInvertedViewMatrix();
	}
	
	
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
		this.move(viewDirection.times(-distance));
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
		
		updateViewMatrix();
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
		
		this.updateViewMatrix();
	}
	
	
	/**
	 * 
	 * Sets the camera's position.
	 * 
	 * @param position The new position of the camera.
	 */
	public void setPosition(Vector3f position) {
		moveTo(position);
	}
	
	
	/**
	 * 
	 * Turns the camera to look at the given point
	 * 
	 * @param point The new point the camera should look at
	 */
	public void lookAt(Vector3f point) {		
		this.viewDirection = point.minus(position).normalize();
		
		this.updateViewMatrix();
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
		
		this.updateViewMatrix();
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
		
		this.updateViewMatrix();
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
		
		this.updateViewMatrix();
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
	 * Rotates the camera around the z-axis
	 * 
	 * @param radians The rotation in radians
	 */
	public void roll(float radians) {
		Matrix33f rotationMatrix = new Matrix33f(cos(radians), -sin(radians), 0f, sin(radians), cos(radians), 0f, 0f, 0f, 1f);
		
		this.viewDirection = rotationMatrix.times(viewDirection).normalize();
		
		this.upVector = rotationMatrix.times(upVector);
		
		this.updateViewMatrix();
	}
	
	
	/**
	 * 
	 * Rotates the camera around the z-axis
	 * 
	 * @param degrees The rotation in degrees
	 */
	public void roll(int degrees) {
		float radians = (float)(degrees / 360) * 2 * PI;
		roll(radians);
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
		yaw(yaw);
	}
	
	
	/**
	 * 
	 * @return Returns the camera's position.
	 */
	public Vector3f getPosition() {
		return position;
	}

	
	/**
	 * 
	 * @return Returns the camera's view direction.
	 */
	public Vector3f getViewDirection() {
		return viewDirection;
	}
	
	
	/**
	 * 
	 * @return Returns the up vector that is used to construct the
	 * camera's view matrix.
	 */
	protected Vector3f getUpVector() {
		return upVector;
	}
	
	
	/**
	 * 
	 * @return Returns the camera's field of view.
	 */
	public float getFieldOfView() {
		return fieldOfView;
	}
	
	
	/**
	 * 
	 * @return Returns the camera's frustrum center in world space.
	 */
	public Vector3f getFrustrumCenter() {
		return getInvertedViewProjectionMatrix().times(projectionMatrix.getFrustrumCenter()).toVector3f();
	}
	
	
	/**
	 * 
	 * @return Returns the camera's frustrum's corners in world space.
	 */
	public Vector4f[] getFrustrumCorners() {
		Vector4f[] corners = projectionMatrix.getFrustrumCorners();
		
		for (int i = 0; i < corners.length; ++i) {
			corners[i] = invertedViewProjectionMatrix.times(corners[i]);
		}
		
		return corners;		
	}
	
	
	//****************************************** matrix handling ******************************************

	
	/**
	 * 
	 * @return Returns the camera's projection matrix.
	 */
	public ProjectionMatrix getProjectionMatrix() {
		return projectionMatrix;
	}
	
	
	/**
	 * 
	 * @return Returns the inverse of the camera's projection matrix.
	 */
	public Matrix44f getInvertedProjectionMatrix() {
		return invertedProjectionMatrix;
	}
	

	/**
	 *  
	 * @return Generates a view matrix for this camera
	 */
	public Matrix44f getViewMatrix() {
		return viewMatrix;
	}
	
	
	/**
	 * 
	 * @return Generates the multiplicative inverse of the view matrix
	 */
	public Matrix44f getInvertedViewMatrix() {
		return invertedViewMatrix;
	}
	
	
	public Matrix44f getInvertedViewProjectionMatrix() {
		return invertedProjectionMatrix;
	}
	
	
	private Matrix44f generateInvertedViewMatrix() {
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