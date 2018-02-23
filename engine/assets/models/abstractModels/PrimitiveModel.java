package assets.models.abstractModels;


import assets.textures.Texture;
import math.matrices.TransformationMatrix;


/*
 * This class takes care of some of the very basic features of 
 * a model such as management of the transformation matrix 
 */
public abstract class PrimitiveModel {
	
	
	private Texture texture;
	
	
	private TransformationMatrix transformationMatrix;
	
	
	//****************************** contructor **********************************
	
	
	public PrimitiveModel() {
		
		texture = null;
		
		transformationMatrix = new TransformationMatrix();
		
	}
	
	
	//**************************** get & set *************************************
	
	
	public void setTexture(Texture texture) {
		
		this.texture = texture;
		
	}
	
	
	public Texture getTexture() {
		
		return texture;
		
	}
	
	
	public void setTransformationMatrix(TransformationMatrix matrix) {
		
		this.transformationMatrix = matrix;
		
	}
	
	
	public TransformationMatrix getTransformationMatrix() {
		
		return transformationMatrix;
		
	}
	
	
	public void scale(float factor) {
		
		transformationMatrix.setScale(factor);
		
	}
	
	
	public void translate(float x, float y, float z) {
		
		transformationMatrix.setTrans(x, y, z);
		
	}
	
	
	public void rotate(float x, float y, float z) {
		
		transformationMatrix.setRot(x, y, z);
		
	}
	
	
	public boolean isTextured() {
		return texture == null;
	}
	

}
