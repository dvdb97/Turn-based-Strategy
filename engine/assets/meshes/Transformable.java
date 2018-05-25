package assets.meshes;

import math.matrices.Matrix44f;
import rendering.matrices.transformation.*;
import math.vectors.Vector3f;

public class Transformable {
	
	private Vector3f translation;
	
	private Vector3f rotation;
	
	private Vector3f scale;
	
	
	public Transformable() {
		this.translation = new Vector3f(0f, 0f, 0f);
		this.rotation = new Vector3f(0f, 0f, 0f);
		this.scale = new Vector3f(0f, 0f, 0f);
	}
	
	
	public Transformable(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.translation = translation.copyOf();
		this.rotation = rotation.copyOf();
		this.scale = scale.copyOf();
	}
	
	
	public void translate(Vector3f translation) {
		this.translation.plus(translation);
	}
	
	
	public void translate(float x, float y, float z) {
		this.translation.setA(translation.getA() + x);
		this.translation.setB(translation.getB() + y);
		this.translation.setC(translation.getC() + z);
	}
	
	
	public void rotate(Vector3f rotation) {
		this.rotation.plus(rotation);
	}
	
	
	public void rotate(float x, float y, float z) {
		this.rotation.setA(rotation.getA() + x);
		this.rotation.setB(rotation.getB() + y);
		this.rotation.setC(rotation.getC() + z);
	}
	
	
	public Matrix44f getTransformationMatrix() {
		
		Matrix44f rotationMat = RotationMatrix.getRotationMatrix(rotation);
		
		Matrix44f translationMatrix = TranslationMatrix.getTranslationMatrix(translation);
		
		Matrix44f scalingMatrix = RotationMatrix.getRotationMatrix(rotation);
		
		return scalingMatrix.times(rotationMat.times(translationMatrix));
		
	}

}
