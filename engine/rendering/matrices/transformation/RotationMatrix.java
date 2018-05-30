package rendering.matrices.transformation;

import math.matrices.Matrix44f;
import math.vectors.Vector3f;

public class RotationMatrix {
	
	
	public static Matrix44f getRotationMatrix(float x, float y, float z) {
		
		return getZRotationMatrix(z).times(getYRotationMatrix(y).times(getXRotationMatrix(x)));		
		
	}
	
	
	public static Matrix44f getRotationMatrix(Vector3f vec) {
		
		return getRotationMatrix(vec.getA(), vec.getB(), vec.getC());
		
	}
	
	
	private static Matrix44f getXRotationMatrix(float value) {
		
		Matrix44f matrix = new Matrix44f();
		matrix.setIdentity();
		
		matrix.setB2((float)Math.cos(value));
		matrix.setB3(-(float)Math.sin(value));
		matrix.setC2((float)Math.sin(value));
		matrix.setC3((float)Math.cos(value));
		
		return matrix;		
		
	}
	
	
	private static Matrix44f getYRotationMatrix(float value) {
		
		Matrix44f matrix = new Matrix44f();
		matrix.setIdentity();
		
		matrix.setA1((float)Math.cos(value));
		matrix.setA3((float)Math.sin(value));
		matrix.setC1(-(float)Math.sin(value));
		matrix.setC3((float)Math.cos(value));
		
		return matrix;
		
	}
	
	
	private static Matrix44f getZRotationMatrix(float value) {
		
		Matrix44f matrix = new Matrix44f();
		matrix.setIdentity();
		
		matrix.setA1((float)Math.cos(value));
		matrix.setA2(-(float)Math.sin(value));
		matrix.setB1((float)Math.sin(value));
		matrix.setB2((float)Math.cos(value));
		
		return matrix;
		
	}
	

}
