package assets.meshes;

import org.omg.CORBA.portable.ValueBase;

import math.matrices.Matrix44f;
import rendering.matrices.transformation.*;
import math.vectors.Vector3f;

public class Transformable {
	
	public static final float _1_DEGREE = 0.0174533f;
	
	private Vector3f translation;
	
	private Matrix44f translationMatrix;
	
	
	private Vector3f rotation;
	
	private Matrix44f rotationMatrix;
	
	
	private Vector3f scaling;
	
	private Matrix44f scalingMatrix;
	
	
	/**
	 * Initializes the Transformable with zero vectors for each transformation
	 */
	public Transformable() {
		this.translation = new Vector3f(0f, 0f, 0f);
		this.rotation = new Vector3f(0f, 0f, 0f);
		this.scaling = new Vector3f(0f, 0f, 0f);
		
		this.translationMatrix = new Matrix44f();
		this.rotationMatrix = new Matrix44f();
		this.scalingMatrix = new Matrix44f();
	}
	
	
	/**
	 * 
	 * Initializes the Transformable with given vectors
	 * 
	 * @param translation The translation of the Transformable
	 * @param rotation The rotation of the Transformable
	 * @param scale The scale of the Transformable
	 */
	public Transformable(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.translation = translation.copyOf();
		this.rotation = rotation.copyOf();
		this.scaling = scale.copyOf();
		
		this.translationMatrix = TranslationMatrix.getTranslationMatrix(translation);
		this.rotationMatrix = RotationMatrix.getRotationMatrix(rotation);
		this.scalingMatrix = ScalingMatrix.getScalingMatrix(scale);
	}
	
	
	/**
	 * 
	 * Translates the Transformable by the given vector
	 * 
	 * @param translation The additional translation
	 */
	public void translate(Vector3f translation) {
		this.translation.plus(translation);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.translationMatrix = TranslationMatrix.getTranslationMatrix(translation);
	}
	
	
	/**
	 * 
	 * Translates the Transformable by the given values
	 * 
	 * @param x The x translation
	 * @param y The y translation
	 * @param z The z translation
	 */
	public void translate(float x, float y, float z) {
		this.translation.increaseA(x);
		this.translation.increaseB(y);
		this.translation.increaseC(z);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.translationMatrix = TranslationMatrix.getTranslationMatrix(translation);
	}
	
	
	/**
	 * 
	 * Rotates the Transformable by the given vector. The values of
	 * the vector are used as radians.
	 * 
	 * @param rotation The rotations vector
	 */
	public void rotate(Vector3f rotation) {
		this.rotation.plus(rotation);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.rotationMatrix = RotationMatrix.getRotationMatrix(rotation);
	}
	
	
	/**
	 * 
	 * Rotates the Transformable by the given values. The values of
	 * are used as radians.
	 * 
	 * @param x The x rotation
	 * @param y The y rotation
	 * @param z The z rotation
	 */
	public void rotate(float x, float y, float z) {
		this.rotation.increaseA(x);
		this.rotation.increaseB(y);
		this.rotation.increaseC(z);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.rotationMatrix = RotationMatrix.getRotationMatrix(rotation);
	}
	
	
	/**
	 * 
	 * Scales the Transformable by the values of the given vector, each of 
	 * the values scaling the Transformable in the corresponding dimension.
	 * 
	 * @param scale The scaling vector
	 */
	public void scale(Vector3f scale) {
		this.scaling.increaseA(scale.getA());
		this.scaling.increaseB(scale.getB());
		this.scaling.increaseC(scale.getC());
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.scalingMatrix = ScalingMatrix.getScalingMatrix(scaling);
	}
	
	
	/**
	 * 
	 * Scales the Transformable by the given values, each of the values
	 * scaling the Transformable in the corresponding dimension.
	 * 
	 * @param x The x scale
	 * @param y The y scale
	 * @param z The z scale
	 */
	public void scale(float x, float y, float z) {
		this.scaling.increaseA(x);
		this.scaling.increaseB(y);
		this.scaling.increaseC(z);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.scalingMatrix = ScalingMatrix.getScalingMatrix(scaling);		
	}
	
	
	public void scale(float xyz) {
		this.scaling.increaseA(xyz);
		this.scaling.increaseB(xyz);
		this.scaling.increaseC(xyz);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.scalingMatrix = ScalingMatrix.getScalingMatrix(scaling);	
	}
	
	
	/**
	 * 
	 * Computes the transformation matrix for this model.
	 * 
	 * @return Returns the transformation matrix.
	 */
	public Matrix44f getTransformationMatrix() {
		return translationMatrix.times(rotationMatrix.times(scalingMatrix));
	}

}
