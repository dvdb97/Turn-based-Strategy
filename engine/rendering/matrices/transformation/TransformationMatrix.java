package rendering.matrices.transformation;

import math.matrices.Matrix44f;
import math.vectors.Vector3f;

public class TransformationMatrix extends Matrix44f{
	
	protected Vector3f translation = Vector3f.ZERO;
	protected Vector3f rotation = Vector3f.ZERO;
	protected Vector3f scale = new Vector3f(1f, 1f, 1f);
	
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
		
		this.translation = new Vector3f(transX, transY, transZ);
		this.rotation = new Vector3f(rotX, rotY, rotZ);
		this.scale = new Vector3f(scale, scale, scale);
		
		//precalc cos and sin
		calcCosAndSin();
		//change all components of the matrix in way, it can be called a transformation matrix
		updateData();
		
	}
	//only trans and one scale
	public TransformationMatrix(float transX, float transY, float transZ, float scale) {
		
		//gives an 4x4 identity matrix
		super();
		
		this.translation = new Vector3f(transX, transY, transZ);
		this.scale = new Vector3f(scale, scale, scale);
		
		updateData();
		
	}
	
	
	public TransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = new Vector3f(scale, scale, scale);
	}
	
	
	public TransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.translation = translation;		
		this.rotation = rotation;
		this.scale = scale;
	}
	
	
	public TransformationMatrix() {
		//gives an 4x4 identity matrix
		super();
	}
	
	
	//---------------------- utils ------------------------------
	//updates the components of the matrix using the given values
	protected void updateData() {
		
		setA1( scale.getA() * cRY * cRZ);
		setA2(-scale.getA() * sRZ);
		setA3( scale.getA() * sRY);
		setA4( scale.getA() * translation.getA());
		setB1( scale.getB() * sRZ);
		setB2( scale.getB() * cRX * cRZ);
		setB3(-scale.getB() * sRX);
		setB4( scale.getB() * translation.getB());
		setC1(-scale.getC() * sRY);
		setC2( scale.getC() * sRX);
		setC3( scale.getC() * cRX * cRY);
		setC4( scale.getC() * translation.getC());
		
	//	setD4( scale);
		setD4( 1.0f);
	}
	
	//pre calc cos and sin of the rot-values 
	private void calcCosAndSin() {
		cRX = (float)Math.cos(rotation.getA());
		sRX = (float)Math.sin(rotation.getA());
		cRY = (float)Math.cos(rotation.getB());
		sRY = (float)Math.sin(rotation.getB());
		cRZ = (float)Math.cos(rotation.getC());
		sRZ = (float)Math.sin(rotation.getC());
	}
	
	//------------------------ Get & Set --------------------------------
	
	public void setScale(float scale) {
		this.scale = new Vector3f(scale, scale, scale);
		
		updateData();
	}
	
	public void setScale(Vector3f scale) {
		this.scale = scale;
		
		updateData();
	}
	
	public void setTrans(float x, float y, float z) {
		this.translation = new Vector3f(x, y, z);
		
		updateData();
	}
	
	public void setTrans(Vector3f xyz) {
		this.translation = xyz;
		
		updateData();
	}
	
	public void setTransScale(Vector3f xyz, float scale) {
		this.translation = xyz;
		
		//The data update is already included in this function.
		this.setScale(scale);
	}
	
	public void setRot(float x, float y, float z) {
		this.rotation = new Vector3f(x, y, z);
		
		calcCosAndSin();
		
		updateData();
	}
	
	public void setRot(Vector3f rotation) {
		this.setRot(rotation.getA(), rotation.getB(), rotation.getC());
	}
	
	public void setAll(Vector3f translation, float rotX, float rotY, float rotZ, float scale) {
		this.translation = translation;
		this.rotation = new Vector3f(rotX, rotY, rotZ);
		this.scale  = new Vector3f(scale, scale, scale);
		
		calcCosAndSin();
		
		updateData();
	}
	
	public void setAll(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
		
		calcCosAndSin();
		
		updateData();
	}
	
	public void setAll(Vector3f translation, Vector3f rotation, float scale) {
		this.setAll(translation, rotation, new Vector3f(scale, scale, scale));
	}
	
	public Vector3f getTranslation() {
		return translation;
	}
	
	//TODO: maybe not needed
	public float getTransX() {
		return translation.getA();
	}

	public float getTransY() {
		return translation.getB();
	}

	public float getTransZ() {
		return translation.getC();
	}
	
	public Vector3f getRotation() {
		return rotation;
	}

	public float getRotX() {
		return rotation.getA();
	}

	public float getRotY() {
		return rotation.getB();
	}

	public float getRotZ() {
		return rotation.getC();
	}

	public Vector3f getScale() {
		return scale;
	}
	
}
