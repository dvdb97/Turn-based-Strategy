package rendering.matrices.projectionMatrices;

import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion44f;

public class ProjectionMatrix extends Matrix44f {
	
	private float left;
	
	private float right;
	
	private float bottom;
	
	private float top;
	
	private float near; 
	
	private float far;
	
	private float widthHeightRelation;
	
	private Matrix44f multiplicativeInverse;
	
	
	private ProjectionMatrix(float widthHeightRelation) {
		
		this.left = -1f;
		
		this.right = 1f;
		
		this.bottom = -1f;
		
		this.top = 1f;
		
		this.near = 1f;
		
		this.far = -1f;
		
		this.widthHeightRelation = widthHeightRelation;
		
	}
	
	
	private void generatePerspectiveMatrix( ) {
		
		setD3(-1f);
		setD4(0f);		
		setA1(2*near / (right - left));
		setA3((right + left) / (right - left));
		setB2(2*near / (top-bottom) * widthHeightRelation);
		setB3((top + bottom) / (top - bottom) * widthHeightRelation);
		setC3(-(far + near) / (far - near));
		setC4(-2 * far * near / (far - near));
		
		this.multiplicativeInverse = MatrixInversion44f.generateMultiplicativeInverse(this);
		
	}
	
	
	private void generateOrthographicMatrix() {
		
		setA1(2 / (right - left));
		setB2(2 / (top - bottom));
		setC3(-2 / (far - near));
		setD1(-((right + left) / (right - left)));
		setD2(-((top + bottom) / (top - bottom)));
		setD3(-(far + near) / (far - near));	
		setD4(1f);
		
		this.multiplicativeInverse = MatrixInversion44f.generateMultiplicativeInverse(this);
		
	}
	
	
	/**
	 * 
	 * Generates a projection matrix for perspective rendering
	 * 
	 * @param widthHeightRelation The relation of the window's width and height
	 * @return Returns a projection matrix for perspective rendering
	 */
	public static ProjectionMatrix generatePerspectiveProjectionMatrix(float widthHeightRelation) {
		ProjectionMatrix matrix = new ProjectionMatrix(widthHeightRelation);
		
		matrix.generatePerspectiveMatrix();
		
		return matrix;
	}
	
	
	/**
	 * 
	 * Generates a projection matrix for orthographic rendering
	 * 
	 * @param widthHeightRelation The relation of the window's width and height
	 * @return Returns a projection matrix for orthographic rendering
	 */
	public static ProjectionMatrix generateOrthographicProjectionMatrix(float widthHeightRelation) {
		ProjectionMatrix matrix = new ProjectionMatrix(widthHeightRelation);
		
		matrix.generateOrthographicMatrix();
		
		return matrix;
	}
	
	
	/**
	 * 
	 * @return Returns the multiplicative inverse of this matrix
	 */
	public Matrix44f getMultiplicativeInverse() {
		return multiplicativeInverse;
	}


	public float getLeft() {
		return left;
	}


	public float getRight() {
		return right;
	}


	public float getBottom() {
		return bottom;
	}


	public float getTop() {
		return top;
	}


	public float getNear() {
		return near;
	}


	public float getFar() {
		return far;
	}


	public float getWidthHeightRelation() {
		return widthHeightRelation;
	}

}
