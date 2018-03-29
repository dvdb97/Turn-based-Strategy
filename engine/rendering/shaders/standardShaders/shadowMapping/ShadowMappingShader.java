package rendering.shaders.standardShaders.shadowMapping;

import math.matrices.Matrix44f;
import rendering.shaders.ShaderProgram;
import utils.FileUtils;

public class ShadowMappingShader extends ShaderProgram {

	private ShadowMappingShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
	}
	
	
	public static ShadowMappingShader generateShader() {
		return new ShadowMappingShader(FileUtils.loadShaderSourceCode("Shaders/ShadowMappingShader/ShadowMappingShader.vert"), 
									   FileUtils.loadShaderSourceCode("Shaders/ShadowMappingShader/ShadowMappingShader.frag"));
	}
	
	
	public void prepareForRendering(Matrix44f modelMatrix, Matrix44f lightViewMatrix, Matrix44f projectionMatrix) {
		this.use();
		
		this.setUniformMatrix4fv("modelMatrix", modelMatrix);
		this.setUniformMatrix4fv("viewMatrix", lightViewMatrix);
		this.setUniformMatrix4fv("projectionMatrix", projectionMatrix);
		
	}

}
