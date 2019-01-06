package assets.meshes;

import math.matrices.Matrix44f;
import rendering.matrices.transformation.*;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

public class Transformable {
	
	private Transformable parent;
	
	public static final float _1_DEGREE = 0.0174533f;	
	
	private TransformationMatrix transformationMatrix;
	
	
	/**
	 * Initializes the Transformable with zero vectors for each transformation
	 */
	public Transformable() {
		transformationMatrix = new TransformationMatrix();	
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
		transformationMatrix = new TransformationMatrix(translation, rotation, scale);
	}
	
	
	/**
	 * 
	 * Translates the Transformable by the given vector
	 * 
	 * @param translation The additional translation
	 */
	public void translate(Vector3f translation) {
		setTranslation(transformationMatrix.getTranslation().plus(translation));
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
		this.translate(new Vector3f(x, y, z));
	}
	
	
	public void setTranslation(Vector3f translation) {
		transformationMatrix.setTrans(translation);
	}
	
	
	public void setTranslation(float x, float y, float z) {
		setTranslation(new Vector3f(x, y, z));
	}
	
	
	public Vector3f getTranslation() {
		return transformationMatrix.getTranslation();
	}
	
	
	/**
	 * 
	 * Rotates the Transformable by the given vector. The values of
	 * the vector are used as radians.
	 * 
	 * @param rotation The rotations vector
	 */
	public void rotate(Vector3f rotation) {
		this.setRotation(transformationMatrix.getRotation().plus(rotation));
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
		this.rotate(new Vector3f(x, y, z));
	}
	
	
	public void setRotation(Vector3f rotation) {
		transformationMatrix.setRot(rotation);
	}
	
	
	public void setRotation(float x, float y, float z) {
		transformationMatrix.setRot(x, y, z);
	}
	
	
	public Vector3f getRotation() {
		return transformationMatrix.getRotation();
	}
	
	
	/**
	 * 
	 * Scales the Transformable by the values of the given vector, each of 
	 * the values scaling the Transformable in the corresponding dimension.
	 * 
	 * @param scale The scaling vector
	 */
	public void scale(Vector3f scale) {
		this.setScaling(transformationMatrix.getScale().plus(scale));
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
		this.scale(new Vector3f(x, y, z));		
	}
	
	
	public void scale(float xyz) {
		this.scale(xyz, xyz, xyz);	
	}
	
	
	public void setScaling(Vector3f scale) {
		transformationMatrix.setScale(scale);
	}
	
	
	public void setScaling(float x, float y, float z) {
		this.setScaling(new Vector3f(x, y, z));
	}
	
	
	public void setScaling(float xyz) {
		this.setScaling(xyz, xyz, xyz);
	}
	
	
	public Vector3f getScaling() {
		return transformationMatrix.getScale();
	}
	
	
	public void adaptTo(Transformable transform) {
		transformationMatrix.setAll(transform.getTranslation(),transform.getRotation(), transform.getScaling());
	}
	
	
	public void adaptTo(Mesh mesh) {
		this.adaptTo(mesh.getTransformable());
	}
	
	
	/**
	 * 
	 * Adds a transformable as the parent for this transformable.
	 * The parent will be stored as a reference.
	 */
	public void setParentTransform(Transformable parent) {
		this.parent = parent;
	}
	
	/**
	 * 
	 * Adds this mesh's transformable as the parent for this transformable.
	 * The parent will be stored as a reference.
	 */
	public void setParentTransfrom(Mesh mesh) {
		this.setParentTransform(mesh.getTransformable());
	}
	
	
	/**
	 * 
	 * Removes the parent that is currently assigned to this transformable.
	 */
	public void removeParent() {
		this.parent = null;
	}
	
	
	/**
	 * Replaces the parent that is currently assigned to this transforable
	 * with the given transformable. This method does the same as setParentTransform
	 * but its name might make code more understandable.
	 */
	public void replaceParent(Transformable parent) {
		setParentTransform(parent);
	}
	
	
	/**
	 * 
	 * Computes the transformation matrix for this model.
	 * 
	 * @return Returns the transformation matrix.
	 */
	public Matrix44f getTransformationMatrix() {
		if (parent != null) {
			return parent.getTransformationMatrix().times(transformationMatrix);
		}
		
		return transformationMatrix;
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

}
