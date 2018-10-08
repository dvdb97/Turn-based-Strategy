package assets.shaders.standardShaders.shadowMapping;

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
	
	
	public void setModelMatrix(Matrix44f modelMatrix) {
		this.setUniformMatrix4fv("modelMatrix", modelMatrix);
	}
	
	
	public void prepareForRendering(Matrix44f lightViewMatrix, Matrix44f projectionMatrix) {
		this.use();
		
		this.setUniformMatrix4fv("viewMatrix", lightViewMatrix);
		this.setUniformMatrix4fv("projectionMatrix", projectionMatrix);
	}

}
