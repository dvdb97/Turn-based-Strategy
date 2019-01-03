package assets.shaders.standardShaders.skybox;

import assets.material.Material;
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
		String vertSource = FileUtils.loadShaderSourceCode("Shaders/EnvironmentMapping/envMapping.vert");
		String fragSource = FileUtils.loadShaderSourceCode("Shaders/EnvironmentMapping/envMapping.frag");
		
		return new EnvMappingShader(vertSource, fragSource);
	}

	@Override
	public void setMaterial(Material material) {
		// TODO Auto-generated method stub
		super.setMaterial(material);
	}

}
