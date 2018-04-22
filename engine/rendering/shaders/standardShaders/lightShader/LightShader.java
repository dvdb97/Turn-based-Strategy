package rendering.shaders.standardShaders.lightShader;

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

	
	private DirectionalLight lightSource = new DirectionalLight(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(1.0f, 1.0f, 1.0f));
	
	private StandardMaterial standardMaterial = new StandardMaterial();
	
	
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
	 * Passes the model, view and projection matrix as an uniform variables to the gpu
	 * 
	 * @param modelMatrix The model matrix
	 * @param viewMatrix The view matrix
	 * @param projectionMatrix The projection matrix
	 */
	public void setMVPMatrix(Matrix44f modelMatrix, Matrix44f viewMatrix, Matrix44f projectionMatrix) {
		this.setModelMatrix(modelMatrix);
		this.setViewMatrix(viewMatrix);
		this.setProjectionMatrix(projectionMatrix);		
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
	 * Sets the values of all the material-related uniform variables
	 * 
	 * @param mat The material
	 */
	public void setMaterial(Material mat) {
		
		this.setUniform3fv("material.emission", mat.emission.toArray());
		
		this.setUniform3fv("material.ambient", mat.ambient.toArray());
		
		this.setUniform3fv("material.diffuse", mat.diffuse.toArray());
		
		this.setUniform3fv("material.specular", mat.specular.toArray());
		
		this.setUniform1f("material.shininess", mat.shininess);
		
	}
	
	
	/**
	 * 
	 * Sets the values of all the light source related uniform variables
	 * 
	 * @param ls The light source
	 * @param lightViewMatrix The matrix that moves the model into the view space of the light source
	 * @param lightProjectionMatrix The projection matrix that is used for shadow mapping
	 */
	public void setLightSource(DirectionalLight ls, Matrix44f lightViewMatrix, Matrix44f lightProjectionMatrix, boolean shadows) {
		
		this.setUniform3fv("light.direction", ls.getViewDirection().toArray());
		
		this.setUniform3fv("light.color", ls.getColor().toArray());
		
		this.setLightVPMatrix(lightViewMatrix, lightProjectionMatrix);
		
		this.setUniform1i("shadowsActive", shadows ? 1 : 0);
		
	}
	
	
	public void setShadowMapInformation(ShadowMap shadowMap) {
		
		this.setLightVPMatrix(shadowMap.getLightViewMatrix(), shadowMap.getLightProjectionMatrix());
		
	}

}
