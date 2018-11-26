package assets.shaders.standardShaders;

import assets.shaders.ShaderProgram;
import utils.FileUtils;

public class StandardShader extends ShaderProgram {

	private StandardShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
	}
	
	
	public static StandardShader generate() {
		String vert = FileUtils.loadShaderSourceCode("Shaders/StandardShaders/shader.vert");
		String frag = FileUtils.loadShaderSourceCode("Shaders/StandardShaders/shader.frag");
		
		return new StandardShader(vert, frag);
	}

}
