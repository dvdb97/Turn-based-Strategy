package rendering.shaders.standardShaders.skybox;

import math.matrices.Matrix44f;
import rendering.shaders.ShaderProgram;
import utils.FileUtils;

public class SkyboxShader extends ShaderProgram {	
	
	private SkyboxShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
		// TODO Auto-generated constructor stub
	}
	
	
	public static SkyboxShader createSkyboxShader() {
		String vertSource = FileUtils.loadShaderSourceCode("Shaders/SkyboxShader/SkyboxShader.vert");
		String fragSource = FileUtils.loadShaderSourceCode("Shaders/SkyboxShader/SkyboxShader.frag");
		
		return new SkyboxShader(vertSource, fragSource);
	}
	
	
	/**
	 * 
	 * Sets the matrices as uniform variables.
	 * 
	 * @param view The view matrix of the camera that is currently used for rendering.
	 * @param projection The projection matrix of the camera that is currently used for rendering.
	 */
	public void setUniformMatrices(Matrix44f view, Matrix44f projection) {
		Matrix44f cleanedUpViewMatrix = view.toMatrix33f().toMatrix44f();
		
		this.setUniformMatrix4fv("viewMatrix", cleanedUpViewMatrix);
		this.setUniformMatrix4fv("projectionMatrix", projection);		
	}

}
