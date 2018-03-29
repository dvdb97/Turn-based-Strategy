package assets.light;

import assets.textures.Texture2D;
import math.matrices.Matrix44f;

public class ShadowMap {
	
	private Matrix44f lightViewMatrix;
	
	private Matrix44f lightProjectionMatrix;
	
	private Texture2D shadowMap;
	

	public ShadowMap(Texture2D shadowMap, Matrix44f lightViewMatrix, Matrix44f lightProjectionMatrix) {
		
		this.lightViewMatrix = lightViewMatrix;
		
		this.lightProjectionMatrix = lightProjectionMatrix;
		
		this.shadowMap = shadowMap;
	}

	
	public Texture2D getShadowMap() {
		return shadowMap;
	}


	public void setShadowMap(Texture2D shadowMap) {
		this.shadowMap = shadowMap;
	}


	public Matrix44f getLightViewMatrix() {
		return lightViewMatrix;
	}

	
	public void setLightViewMatrix(Matrix44f lightViewMatrix) {
		this.lightViewMatrix = lightViewMatrix;
	}


	public Matrix44f getLightProjectionMatrix() {
		return lightProjectionMatrix;
	}


	public void setLightProjectionMatrix(Matrix44f lightProjectionMatrix) {
		this.lightProjectionMatrix = lightProjectionMatrix;
	}
		
}
