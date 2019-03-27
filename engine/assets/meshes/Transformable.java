package assets.meshes;

import math.matrices.Matrix44f;
import rendering.matrices.transformation.*;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

public class Transformable {
	
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
		transformationMatrix.setTranslation(translation);
		transformationMatrix.setScale(scale);
		transformationMatrix.setRot(rotation);
	}
	
	
	/**
	 * 
	 * Translates the Transformable by the given vector
	 * 
	 * @param translation The additional translation
	 */
	public void translate(Vector3f translation) {
		transformationMatrix.setTranslation(translation);
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
		transformationMatrix.setTranslation(transformationMatrix.getTransX() + x,
											transformationMatrix.getTransY() + y,
											transformationMatrix.getTransZ() + z);
	}
	
	
	public void setTranslation(Vector3f translation) {
		transformationMatrix.setTranslation(translation);
	}
	
	
	public void setTranslation(float x, float y, float z) {
		transformationMatrix.setTranslation(x, y, z);
	}
	
	
	public Vector3f getPosition() {
		return transformationMatrix.getTranslation();
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
		transformationMatrix.setRot(transformationMatrix.getRotX() + rotation.getA(),
									transformationMatrix.getRotY() + rotation.getB(),
									transformationMatrix.getRotZ() + rotation.getC());
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
		transformationMatrix.setRot(transformationMatrix.getRotX() + x,
									transformationMatrix.getRotY() + y,
									transformationMatrix.getRotZ() + z);
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
		transformationMatrix.setScale(transformationMatrix.getScaleX() * scale.getA(),
									  transformationMatrix.getScaleY() * scale.getB(),
									  transformationMatrix.getScaleZ() * scale.getC());
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
		transformationMatrix.setScale(transformationMatrix.getScaleX() * x,
									  transformationMatrix.getScaleY() * y,
									  transformationMatrix.getScaleZ() * z);	
	}
	
	
	public void scale(float xyz) {
		scale(xyz, xyz, xyz);	
	}
	
	
	public void setScaling(Vector3f scale) {
		transformationMatrix.setScale(scale);
	}
	
	
	public void setScaling(float x, float y, float z) {
		transformationMatrix.setScale(x, y, z);
	}
	
	
	public void setScaling(float xyz) {
		transformationMatrix.setScale(xyz);
	}
	
	
	public Vector3f getScaling() {
		return transformationMatrix.getScale();
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
	
	
	/**
	 * 
	 * @return Returns the boundaries of a unit mesh transformed to world space by this Transformable
	 */
	public Vector4f[] getBoundaries() {
		return toWorldSpace(unitCube);
	}

}