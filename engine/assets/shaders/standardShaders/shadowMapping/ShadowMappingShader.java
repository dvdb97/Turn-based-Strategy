package assets.shaders.standardShaders.shadowMapping;

import assets.cameras.Camera;
import assets.shaders.ShaderProgram;
import math.matrices.Matrix44f;
import utils.FileUtils;

public class ShadowMappingShader extends ShaderProgram {

	private ShadowMappingShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
	}
	
	
	public static ShadowMappingShader generateShader() {
		
		String vert = FileUtils.loadShaderSourceCode("Shaders/ShadowMappingShader/ShadowMappingShader.vert");
		String frag = FileUtils.loadShaderSourceCode("Shaders/ShadowMappingShader/ShadowMappingShader.frag");
		
		return new ShadowMappingShader(vert, frag);
	}


	@Override
	public void setModelMatrix(Matrix44f model) {
		//Combine the matrices and pass the result to the shader.
		this.setUniformMatrix4fv("mvpMatrix", viewProjectionMatrix.times(model));
	}


	@Override
	public void setCamera(Camera camera) {
		//Set the view-projection matrix as a variable to use it again when a model matrix is set.
		this.viewProjectionMatrix = camera.getViewProjectionMatrix();
	}

}
