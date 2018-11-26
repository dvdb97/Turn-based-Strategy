package assets.shaders.standardShaders.lightShader;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.light.DepthBuffer;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.shaders.ShaderProgram;
import assets.shaders.subshaders.Subshader;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
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
	
	
	/**
	 * 
	 * @param subshader A subshader to compute the color values.
	 * @return returns a LightShader that does per fragment light computing
	 */
	public static LightShader createPerFragmentLightShader(Subshader subshader) {
		
		String vertSource = FileUtils.loadShaderSourceCode(path + "lightShader.vert");
		String fragSource = FileUtils.loadShaderSourceCode(path + "lightShader.frag");
		
		//Replace the standard color-function with the subshader's function.
		fragSource = fragSource.replace("vec4 color() {\n" + 
				"	return material.color;\n" + 
				"}", subshader.getSourceCode());
		
		return new LightShader(vertSource, fragSource);
	}
	
	
	//*********************************** uniform setting *********************************
	
	
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
	public void setLightViewProjectionMatrix(Matrix44f lightViewProjectionMatrix) {
		this.setUniformMatrix4fv("lightVPMatrix", lightViewProjectionMatrix);
	}
	
	
	/**
	 * 
	 * Passes the view and projection matrix for the light source as an uniform variable to the gpu
	 * 
	 * @param lightViewMatrix The matrix that moves the model into the view space of the light source
	 * @param lightProjectionMatrix The projection matrix that is used for shadow mapping
	 */
	public void setLightVPMatrix(Matrix44f lightViewProjectionMatrix) {
		this.setLightViewProjectionMatrix(lightViewProjectionMatrix);
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
		this.setLightSource(light, false);
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
	public void setLightSource(DirectionalLight light, boolean shadows) {
		this.setUniformVector3f("light.direction", light.getViewDirection());
		
		this.setUniformVector3f("light.color", light.getColor());
		
		this.setLightVPMatrix(light.getViewProjectionMatrix());
		
		if (shadows) {
			this.setUniform1i("shadowsActive", 1);
			light.getShadowMap().bind();
		} else {
			this.setUniform1i("shadowsActive", 0);
		}
	}

}
