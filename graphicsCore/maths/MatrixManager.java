package maths;

import lwlal.Matrix44f;
import lwlal.Vector3f;

/*
 * This class is a utility class that generates a model / view matrix depending on the input
 */
public class MatrixManager {
	
	//TODO: It wasn't tested yet!
	
	
	//Returns a model matrix with the desired model transformations
	public static Matrix44f generateModelMatrix(float tx, float ty, float tz, float rx, float ry, float rz, float scale) {
		
		return getTranslationMatrix(tx, ty, tz).times(getScalingMatrix(scale).times(getRotationMatrix(rx, ry, rz)));		
		
	}
	
	
	//The same as above but with vectors as input
	public static Matrix44f generateModelMatrix(Vector3f translation, Vector3f rotation, float scale) {
		
		return generateModelMatrix(translation.getA(), translation.getB(), translation.getC(), 
								   rotation.getA(), rotation.getB(), rotation.getC(), scale);
		
	}
	
	
	//Returns a view matrix with the camera movement
	public static Matrix44f generateViewMatrix(float tx, float ty, float tz, float rx, float ry, float rz, float zoom) {
		
		return getRotationMatrix(-rx, -ry, -rz).times(getScalingMatrix(zoom).times(getTranslationMatrix(-tx, -ty, -tz)));
		
	}
	
	
	//The same as above but with vectors as input
	public static Matrix44f generateViewMatrix(Vector3f translation, Vector3f rotation, float scale) {
		
		return generateViewMatrix(translation.getA(), translation.getB(), translation.getC(), 
								   rotation.getA(), rotation.getB(), rotation.getC(), scale);
		
	}
	
	
	// ************** basic operations **************
	
	
	private static Matrix44f getTranslationMatrix(float x, float y, float z) {
		
		return new Matrix44f(1f, 0f, 0f, x, 
							 0f, 1f, 0f, y, 
							 0f, 0f, 1f, z, 
							 0f, 0f, 0f, 1f);
		
	}
	
	
	private static Matrix44f getRotationMatrix(float x, float y, float z) {
		
		Matrix44f xRotation = new Matrix44f(1f, 0f, 0f, 0f, 
											0f, cosine(x), -sine(x), 0f, 
											0f, sine(x), cosine(x), 0f, 
											0f, 0f, 0f, 1f);
		
		Matrix44f yRotation = new Matrix44f(cosine(y), 0f, sine(y), 0f, 
											0f, 1f, 0f, 0f, 
											-sine(y), 0f, cosine(y), 0f, 
											0f, 0f, 0f, 1f);
		
		Matrix44f zRotation = new Matrix44f(cosine(z), -sine(x), 0f, 0f, 
											sine(z), cosine(z), 0f, 0f, 
											0f, 0f, 1f, 0f, 
											0f, 0f, 0f, 1f);
		
		return xRotation.times(yRotation.times(zRotation));
		
	}
	
	
	private static Matrix44f getScalingMatrix(float scale) {
		
		return new Matrix44f(scale, 0f, 0f, 0f, 
							 0f, scale, 0f, 0f, 
							 0f, 0f, scale, 0f, 
							 0f, 0f, 0f, 1f);
		
	}
	
	
	// ************** Utility functions ************** 
	
	
	private static float sine(float value) {
		
		return (float)Math.sin(value);
		
	}
	
	
	private static float cosine(float value) {
		
		return (float)Math.cos(value);
		
	}

}
