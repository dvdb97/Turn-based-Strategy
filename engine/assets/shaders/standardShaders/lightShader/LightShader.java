package assets.shaders.standardShaders.lightShader;

import assets.shaders.ShaderProgram;
import assets.shaders.subshaders.colorSubshaders.ColorSubshader;
import math.matrices.Matrix44f;
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
	 * @deprecated
	 * 
	 * @param subshader A subshader to compute the color values.
	 * @return returns a LightShader that does per fragment light computing
	 */
	public static LightShader createPerFragmentLightShader(ColorSubshader subshader) {
		
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
	 * Tells the shader to look up the fragment's color from the texture.
	 */
	public void useTextureColor() {
		this.setUniformSubroutine("colorFunc", "textureColor", FRAGMENT_SHADER);
	}
	
	
	/**
	 * Tells the shader to take the material's color as the fragment's color.	
	 */
	public void useMaterialColor() {
		this.setUniformSubroutine("colorFunc", "materialColor", FRAGMENT_SHADER);
	}
	
	
	/**
	 * Tells the shader to take the attribute's color as the fragment's color.
	 */
	public void useAttribColor() {
		this.setUniformSubroutine("colorFunc", "attribColor", FRAGMENT_SHADER);
	}
	
	
	/**
	 * Tells the shader to use the fragment's final color as it is.
	 */
	public void useDefaultFinalColorFunction() {
		this.setUniformSubroutine("finalColorFunc", "finalLightColor", FRAGMENT_SHADER);
	}
	
	
	/**
	 * tells the shader to apply toon shading to the final color.
	 */
	public void useToonShading() {
		this.setUniformSubroutine("finalColorFunc", "toonShading", VERTEX_SHADER);
	}
	
	//TODO: Add functions to directly bind textures

}
