package assets.shaders;

import utils.FileUtils;

public class ShaderLoader {
	
	public static ShaderProgram loadShader(String vertPath, String fragPath) {
		
		String vertSource = FileUtils.loadShaderSourceCode(vertPath);
		String fragSource = FileUtils.loadShaderSourceCode(fragPath);
		
		return new ShaderProgram(vertSource, fragSource);
		
	}

}
