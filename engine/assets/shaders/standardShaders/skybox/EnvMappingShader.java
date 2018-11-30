package assets.shaders.standardShaders.skybox;

import assets.shaders.ShaderProgram;
import utils.FileUtils;

/**
 * 
 * @author Dario
 * 
 * How to use:
 * 
 * - Set Camera
 * - Bind Skybox
 * - Set Model matrix
 *
 */
public class EnvMappingShader extends ShaderProgram {

	private EnvMappingShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
	}
	
	
	public static EnvMappingShader createEnvMappingShader() {
		String vertSource = FileUtils.loadShaderSourceCode("Shaders/EnvironmentMapping/EnvMapping.vert");
		String fragSource = FileUtils.loadShaderSourceCode("Shaders/EnvironmentMapping/EnvMapping.frag");
		
		return new EnvMappingShader(vertSource, fragSource);
	}

}
