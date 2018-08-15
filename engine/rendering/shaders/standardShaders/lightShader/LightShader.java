package rendering.shaders.standardShaders.lightShader;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.light.ShadowMap;
import assets.material.Material;
import assets.material.StandardMaterial;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import rendering.shaders.ShaderProgram;
import utils.FileUtils;


public class LightShader extends ShaderProgram {
	
	//A constant for loading the files because I'm lazy to type it 4 times
	private static final String path = "Shaders/LightShaders/";	
	
	
	private LightShader(String vertPath, String fragPath) {
		super(vertPath, fragPath);
	}
	
	
	/**
	 * 
	 * @return returns a LightShader that does per vertex light computing
	 */
	public static LightShader createPerVertexLightShader() {
		
		String vertSource = FileUtils.loadShaderSourceCode(path + "lightShaderPV.vert");
		String fragSource = FileUtils.loadShaderSourceCode(path + "lightShaderPV.frag");
		
		return new LightShader(vertSource, fragSource);
	}
	
	
	/**
	 * 
	 * @return returns a LightShader that does per fragment light computing
	 */
	public static LightShader createPerFragmentLightShader() {
		
		String vertSource = FileUtils.loadShaderSourceCode(path + "lightShader.vert");
		String fragSource = FileUtils.loadShaderSourceCode(path + "lightShader.frag");
		
		return new LightShader(vertSource, fragSource);
	}
	
	
	//*********************************** uniform setting *********************************
	
	
	/**
	 * 
	 * Passes the model matrix as an uniform variable to the gpu
	 * 
	 * @param modelMatrix The model matrix
	 */
	public void setModelMatrix(Matrix44f modelMatrix) {
		
		this.setUniformMatrix4fv("modelMatrix", modelMatrix.toArray());
		
	}
	
	
	/**
	 * 
	 * Passes the view matrix as an uniform variable to the gpu
	 * 
	 * @param viewMatrix The view matrix
	 */
	public void setViewMatrix(Matrix44f viewMatrix) {
		
		this.setUniformMatrix4fv("viewMatrix", viewMatrix.toArray());
		
	}
	
	
	/**
	 * 
	 * Passes the projection matrix as an uniform variable to the gpu
	 * 
	 * @param projectionMatrix The projection matrix
	 */
	public void setProjectionMatrix(Matrix44f projectionMatrix) {
		
		this.setUniformMatrix4fv("projectionMatrix", projectionMatrix.toArray());
		
	}
	
	
	/**
	 * 
	 * Passes the light view matrix as an uniform variable to the gpu
	 * 
	 * @param lightViewMatrix The matrix that moves the model into the view space of the light source
	 */
	public void setLightViewMatrix(Matrix44f lightViewMatrix) {
		
		this.setUniformMatrix4fv("lightViewMatrix", lightViewMatrix);
		
	}
	
	
	/**
	 * 
	 * Passes the light projection matrix as an uniform variable to the gpu
	 * 
	 * @param lightProjectionMatrix The projection matrix that is used for shadow mapping
	 */
	public void setLightProjectionMatrix(Matrix44f lightProjectionMatrix) {
		
		this.setUniformMatrix4fv("lightProjectionMatrix", lightProjectionMatrix);
		
	}
	
	
	/**
	 * 
	 * Passes the view and projection matrix for the light source as an uniform variables to the gpu
	 * 
	 * @param lightViewMatrix The matrix that moves the model into the view space of the light source
	 * @param lightProjectionMatrix The projection matrix that is used for shadow mapping
	 */
	public void setLightVPMatrix(Matrix44f lightViewMatrix, Matrix44f lightProjectionMatrix) {
		this.setLightViewMatrix(lightViewMatrix);
		this.setLightProjectionMatrix(lightProjectionMatrix);
	}
	
	
	public void setCameraPosition(Vector3f pos) {
		
		this.setUniform3fv("cameraPosition", pos.toArray());
		
	}
	
	
	public void setAmbientLight(Vector3f vec) {
		
		this.setUniform3fv("ambientLight", vec.toArray());
		
	}
	
	
	/**
	 * 
	 * Sets the values of all the light source related uniform variables.
	 * Sets all shadow related variables to a default value.
	 * 
	 * @param light The light source
	 */
	public void setLightSource(DirectionalLight light) {
		this.setLightSource(light, Matrix44f.IDENTITY, Matrix44f.IDENTITY, false);
	}
	
	
	/**
	 * 
	 * Sets the values of all the light source related uniform variables
	 * 
	 * @param light The light source
	 * @param lightViewMatrix The matrix that moves the model into the view space of the light source
	 * @param lightProjectionMatrix The projection matrix that is used for shadow mapping
	 * @param shadows Enables or disables trying to read from a shadow map.
	 */	
	public void setLightSource(DirectionalLight light, Matrix44f lightViewMatrix, Matrix44f lightProjectionMatrix, boolean shadows) {
		
		this.setUniform3fv("light.direction", light.getViewDirection().toArray());
		
		this.setUniform3fv("light.color", light.getColor().toArray());
		
		this.setLightVPMatrix(lightViewMatrix, lightProjectionMatrix);
		
		this.setUniform1i("shadowsActive", shadows ? 1 : 0);
		
	}
	
	
	/**
	 * 
	 * Sets the values of all the camera related uniform variables
	 * 
	 * @param camera The camera the scene is rendered with.
	 */
	public void setCamera(Camera camera) {
		
		this.setUniform3fv("cameraPosition", camera.getPosition().toArray());
		
		//TODO: Maybe set view matrix here.
		
	}
	
	
	public void setShadowMapInformation(ShadowMap shadowMap) {
		
		this.setLightVPMatrix(shadowMap.getLightViewMatrix(), shadowMap.getLightProjectionMatrix());
		
	}

}
