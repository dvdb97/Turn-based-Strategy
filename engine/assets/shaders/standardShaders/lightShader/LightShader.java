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
	
	
	

}
