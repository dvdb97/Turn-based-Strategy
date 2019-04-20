package assets.meshes;

import assets.IInstantiatable;
import math.matrices.Matrix44f;
import rendering.matrices.transformation.*;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

public class Transformable implements IInstantiatable<Transformable> {
	
	private static final Vector4f[] unitCube = {
		//Front face:
		new Vector4f(-1f, 1f, 1f, 1f),
		new Vector4f(1f, 1f, 1f, 1f),
		new Vector4f(1f, -1f, 1f, 1f),
		new Vector4f(-1f, -1f, 1f, 1f),
		
		//Back face:
		new Vector4f(-1f, 1f, -1f, 1f),
		new Vector4f(1f, 1f, -1f, 1f),
		new Vector4f(1f, -1f, -1f, 1f),
		new Vector4f(-1f, -1f, -1f, 1f)
	};
	
	public static final float _1_DEGREE = 0.0174533f;
	
	private Transformable parent;
	
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
		this.scaling = new Vector3f(1f, 1f, 1f);
		
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
	
	
	public void setTranslation(Vector3f translation) {
		this.translation = translation.copyOf();
		
		this.translationMatrix = TranslationMatrix.getTranslationMatrix(translation);
	}
	
	
	public void setTranslation(float x, float y, float z) {
		this.translation = new Vector3f(x, y, z);
		
		this.translationMatrix = TranslationMatrix.getTranslationMatrix(translation);
	}
	
	
	public Vector3f getPosition() {
		return translation;
	}
	
	
	public Vector3f getTranslation() {
		return translation;
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
	
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation.copyOf();
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.rotationMatrix = RotationMatrix.getRotationMatrix(rotation);
	}
	
	
	public void setRotation(float x, float y, float z) {
		this.rotation = new Vector3f(x, y, z);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.rotationMatrix = RotationMatrix.getRotationMatrix(rotation);
	}
	
	
	public Vector3f getRotation() {
		return rotation;
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
	
	
	public void setScaling(Vector3f scale) {
		this.scaling = scale.copyOf();
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.scalingMatrix = ScalingMatrix.getScalingMatrix(scaling);
	}
	
	
	public void setScaling(float x, float y, float z) {
		this.scaling = new Vector3f(x, y, z);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.scalingMatrix = ScalingMatrix.getScalingMatrix(scaling);
	}
	
	
	public void setScaling(float xyz) {
		this.setScaling(xyz, xyz, xyz);
		
		//TODO: Change the matrix' values instead of creating a completely new one.
		this.scalingMatrix = ScalingMatrix.getScalingMatrix(scaling);
	}
	
	
	public Vector3f getScaling() {
		return scaling;
	}
	
	
	public void adaptTo(Transformable transform) {
		this.setTranslation(transform.getTranslation());
		this.setRotation(transform.getRotation());
		this.setScaling(transform.getScaling());
	}
	
	
	public void adaptTo(Mesh mesh) {
		this.adaptTo(mesh.getTransformable());
	}
	
	
	public void setParent(Transformable parent) {
		this.parent = parent;
	}
	
	
	public void setParent(Mesh mesh) {
		this.setParent(mesh.getTransformable());
	}
	
	
	public void removeParent() {
		this.parent = null;
	}
	
	
	private Matrix44f computeTransformationMatrix() {
		return translationMatrix.times(rotationMatrix.times(scalingMatrix));
	}
	
	
	/**
	 * 
	 * Computes the transformation matrix for this model.
	 * 
	 * @return Returns the transformation matrix.
	 */
	public Matrix44f getTransformationMatrix() {
		if (parent != null) {
			return parent.getTransformationMatrix().times(computeTransformationMatrix());
		}
		
		return computeTransformationMatrix();
	}
	
	
	public Matrix44f getInvertedTransformationMatrix() {		
		return getTransformationMatrix().inverse();
	}
	
	
	/**
	 * 
	 * Transforms a vector to world space
	 * 
	 * @param vec The vector to be transformed
	 * @return Returns a transformed vector.
	 */
	public Vector4f toWorldSpace(Vector4f vec) {
		return getTransformationMatrix().times(vec);
	}
	
	
	/**
	 * 
	 * Transforms a vector to world space
	 * 
	 * @param vec The vector to be transformed
	 * @return Returns a transformed vector.
	 */
	public Vector4f toWorldSpace(Vector3f vec) {
		return toWorldSpace(vec.toVector4f(1f));
	}
	
	
	/**
	 * 
	 * Transforms an array of vectors to world space
	 * 
	 * @param vec The vectors to be transformed
	 * @return Returns a transformed vectors.
	 */
	public Vector4f[] toWorldSpace(Vector4f[] vectors) {
		Matrix44f modelMatrix = getTransformationMatrix();
		Vector4f[] vec = new Vector4f[vectors.length];
		
		for (int i = 0; i < vectors.length; ++i) {
			vec[i] = modelMatrix.times(vectors[i]);
		}
		
		return vec;
	}
	
	
	/**
	 * 
	 * @return Returns the boundaries of a unit mesh transformed to world space by this Transformable
	 */
	public Vector4f[] getBoundaries() {
		return toWorldSpace(unitCube);
	}


	@Override
	public Transformable instanciate() {
		Transformable transformable = new Transformable();
		transformable.adaptTo(this);
		
		return transformable;
	}

}