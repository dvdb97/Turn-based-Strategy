package rendering.shaders.standardShaders.shadowShader;

import math.matrices.Matrix44f;
import rendering.shaders.ShaderProgram;

public class ShadowMappingShader extends ShaderProgram {

	public ShadowMappingShader() {
		super("Shaders/ShadowMappingShader/ShadowMappingShader.vert", "Shaders/ShadowMappingShader/ShadowMappingShader.frag");
	}
	
	public void prepareForRendering(Matrix44f mvpMatrix) {
		this.setUniformMatrix4fv("mvpMatrix", mvpMatrix);
	}

}
