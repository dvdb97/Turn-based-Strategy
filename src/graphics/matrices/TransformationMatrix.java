package graphics.matrices;

import math.matrices.Matrix44f;
import math.vectors.Vector3f;

public class TransformationMatrix extends Matrix44f{
	
	protected float transX = 0.0f;
	protected float transY = 0.0f;
	protected float transZ = 0.0f;
	protected float rotX   = 0.0f;
	protected float rotY   = 0.0f;
	protected float rotZ   = 0.0f;
	//TODO: add scaleX,Y,Z ??
	protected float scale  = 1.0f;
	
	//c=cosine, s=sine, r=rotation
	protected float cRX = 1.0f;
	protected float sRX = 0.0f;
	protected float cRY = 1.0f;
	protected float sRY = 0.0f;
	protected float cRZ = 1.0f;
	protected float sRZ = 0.0f;
	
	//---------------------- contructor --------------------------
	//general
	public TransformationMatrix(float transX, float transY, float transZ, float rotX, float rotY, float rotZ, float scale) {
		
		//gives an 4x4 identy matrix
		super();
		
		this.transX = transX;
		this.transY = transY;
		this.transZ = transZ;
		this.rotX   = rotX;
		this.rotY   = rotY;
		this.rotZ   = rotZ;
		this.scale  = scale;
		
		//precalc cos and sin
		calcCosAndSin();
		//change all components of the matrix in way, it can be called a transformation matrix
		updateData();
		
	}
	//only trans and one scale
	public TransformationMatrix(float transX, float transY, float transZ, float scale) {
		
		//gives an 4x4 identity matrix
		super();
		
		this.transX = transX;
		this.transY = transY;
		this.transZ = transZ;
		this.scale  = scale;
		
		updateData();
		
	}
	
	
	public TransformationMatrix() {
		//gives an 4x4 identity matrix
		super();
	}
	
	
	//---------------------- utils ------------------------------
	//updates the components of the matrix using the given values
	protected void updateData() {
		
		setA1( scale*cRY*cRZ);
		setA2(-scale*sRZ);
		setA3( scale*sRY);
		setA4( scale*transX);
		setB1( scale*sRZ);
		setB2( scale*cRX*cRZ);
		setB3(-scale*sRX);
		setB4( scale*transY);
		setC1(-scale*sRY);
		setC2( scale*sRX);
		setC3( scale*cRX*cRY);
		setC4( scale*transZ);
		
	//	setD4( scale);
		setD4( 1.0f);
	}
	
	//pre calc cos and sin of the rot-values 
	private void calcCosAndSin() {
		cRX = (float)Math.cos(rotX);
		sRX = (float)Math.sin(rotX);
		cRY = (float)Math.cos(rotY);
		sRY = (float)Math.sin(rotY);
		cRZ = (float)Math.cos(rotZ);
		sRZ = (float)Math.sin(rotZ);
	}
	
	//------------------------ Get & Set --------------------------------
	public void setScale(float scale) {
		this.scale = scale;
		
		updateData();
	}
	//TODO: maybe decide: vector or 3 scalars
	//decision: vector3f because a position can be nicely represented by a vector
	public void setTrans(float x, float y, float z) {
		transX = x;
		transY = y;
		transZ = z;
		
		updateData();
	}
	public void setTrans(Vector3f xyz) {
		transX = xyz.getA();
		transY = xyz.getB();
		transZ = xyz.getC();
		
		updateData();
	}
	public void setTransScale(Vector3f xyz, float scale) {
		transX = xyz.getA();
		transY = xyz.getB();
		transZ = xyz.getC();
		this.scale = scale;
		
		updateData();
	}
	public void setRot(float x, float y, float z) {
		rotX = x;
		rotY = y;
		rotZ = z;
		
		calcCosAndSin();
		
		updateData();
	}
	public void setAll(Vector3f transition, float rotX, float rotY, float rotZ, float scale) {
		
		this.transX = transition.getA();
		this.transY = transition.getB();
		this.transZ = transition.getC();
		this.rotX   = rotX;
		this.rotY   = rotY;
		this.rotZ   = rotZ;
		this.scale  = scale;
		
		calcCosAndSin();
		
		updateData();
	}
	
	//TODO: maybe not needed
	public float getTransX() {
		return transX;
	}

	public float getTransY() {
		return transY;
	}

	public float getTransZ() {
		return transZ;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public float getScale() {
		return scale;
	}
	
}
