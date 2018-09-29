package rendering.matrices.transformation;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;

import static math.Trigonometry.*;

/**
 * 
 * @author Dario
 *
 * TODO: Maybe move this to the Tranformable-Class
 * TODO: Only return Matrix33fs
 */
public class RotationMatrix {
	
	/**
	 * 
	 * Generates a rotation matrix to rotate a vector by the given amount.
	 * 
	 * @param x The x rotation in radians.
	 * @param y The y rotation in radians.
	 * @param z The z rotation in randians.
	 * @return Returns a Matrix44f.
	 */
	public static Matrix44f getRotationMatrix(float x, float y, float z) {
		return getZRotationMatrix(z).times(getYRotationMatrix(y).times(getXRotationMatrix(x)));		
	}
	
	
	/**
	 * 
	 * Generates a rotation matrix to rotate a vector by the given amount.
	 * 
	 * @param radians The rotation around the x, y and z axis in radians.
	 * @return Returns a Matrix44f
	 */
	public static Matrix44f getRotationMatrix(Vector3f radians) {
		return getRotationMatrix(radians.getA(), radians.getB(), radians.getC());
	}
	
	
	/**
	 * 
	 * Generates a rotation matrix to rotate a vector by the given angle around a specified axis.
	 * 
	 * @param radian The rotation angle measures in radians.
	 * @param axis The axis to rotate around.
	 * @return Returns a Matrix33f.
	 */
	public static Matrix33f geRotationMatrix(float radian, Vector3f axis) {
		if (axis.norm() != 1f) 
			axis.normalize();
		
		float c = cos(radian);
		float c1 = 1f - c;
		float s = sin(radian);
		
		return new Matrix33f(c + axis.getA() * axis.getA() * c1, axis.getA() * axis.getB() * c1 - axis.getC() * s, axis.getA() * axis.getC() * c1 + axis.getB() * s, 
							 axis.getB() * axis.getA() * c1 + axis.getC() * s, c + axis.getB() * axis.getB() * c1, axis.getB() * axis.getC() * c1 - axis.getA() * s, 
							 axis.getC() * axis.getA() * c1 - axis.getB() * s, axis.getA() * axis.getB() * c1 + axis.getA() * s, c + axis.getC() * axis.getC() * c1);
	}
	
	
	private static Matrix44f getXRotationMatrix(float value) {
		Matrix44f matrix = new Matrix44f();
		matrix.setIdentity();
		
		matrix.setB2(cos(value));
		matrix.setB3(-sin(value));
		matrix.setC2(sin(value));
		matrix.setC3(cos(value));
		
		return matrix;		
	}
	
	
	private static Matrix44f getYRotationMatrix(float value) {
		Matrix44f matrix = new Matrix44f();
		matrix.setIdentity();
		
		matrix.setA1(cos(value));
		matrix.setA3(sin(value));
		matrix.setC1(-sin(value));
		matrix.setC3(cos(value));
		
		return matrix;
	}
	
	
	private static Matrix44f getZRotationMatrix(float value) {
		Matrix44f matrix = new Matrix44f();
		matrix.setIdentity();
		
		matrix.setA1(cos(value));
		matrix.setA2(-sin(value));
		matrix.setB1(sin(value));
		matrix.setB2(cos(value));
		
		return matrix;
	}
}
