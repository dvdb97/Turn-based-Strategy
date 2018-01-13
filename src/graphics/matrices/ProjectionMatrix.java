package graphics.matrices;

import math.matrices.Matrix44f;

public class ProjectionMatrix extends Matrix44f{
	
	//TODO: maybe add an extra matrix mode "ortho" beside "frustum"
	
	private float left;
	private float right;
	private float bottom;
	private float top;
	private float near;
	private float far;
	
	private float widthOverHeight;
	
	
	private Matrix44f multiplicativeInverse;
	
	
	//---------------------- contructor --------------------------
	public ProjectionMatrix(float left, float right, float bottom, float top, float near, float far, float widthOverHeight) {
		
		super();
		
		this.widthOverHeight = widthOverHeight;
		
		setD3(-1.0f);
		setD4( 0.0f);
		
		this.left   = left;
		this.right  = right;
		this.bottom = bottom;
		this.top    = top;
		this.near   = near;
		this.far    = far;
		
		updateData();
		
	}
	
	//---------------------- utils ------------------------------
	//updates the components of the matrix using the given values
	private void updateData() {
		
		setA1( 2*near       / (right-left));
		setA3( (right+left) / (right-left));
		setB2( 2*near       / (top-bottom) * widthOverHeight);
		setB3( (top+bottom) / (top-bottom) * widthOverHeight);
		setC3(-(far+near)   / (far-near));
		setC4(-2*far*near   / (far-near));
		
		computeMultiplicativeInverse();
	}
	
	//---------------------- get & set --------------------------------
	public void setValues(float left, float right, float bottom, float top, float near, float far) {
		this.left   = left;
		this.right  = right;
		this.bottom = bottom;
		this.top    = top;
		this.near   = near;
		this.far    = far;
		
		updateData();
	}
	
	
	private void computeMultiplicativeInverse() {
		
		multiplicativeInverse = new Matrix44f();
		
		multiplicativeInverse.setA1((right - left) / (2 * near));
		multiplicativeInverse.setA4((right + left) / (right - left));
		
		multiplicativeInverse.setB2((top - bottom) / (2 * near * widthOverHeight));
		multiplicativeInverse.setB4((-top - bottom) / (2 * near));
		
		multiplicativeInverse.setC4(-1);
		
		multiplicativeInverse.setD3((far - near) / (-2 * far * near));
		multiplicativeInverse.setD4((far * far - near * near) / (-2 * far * far * near + 2 * near * near * far));
		
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
	
	public float getWidthOverHeight() {
		return widthOverHeight;
	}
	
	public Matrix44f getMultiplicativeInverse() {
		return multiplicativeInverse;
	}
	
}
