package assets.shaders.subshaders.colorSubshaders;

import utils.FileUtils;

public class ColorSubshader {
	
	/**
	 * 
	 * @author Dario
	 *
	 * An exception that can be thrown if the source code doesn't
	 * match the requirements for a subshader.
	 */
	private class InvalidSubshaderException extends RuntimeException {}
	
	
	private String sourceCode;
	
	
	public ColorSubshader(String sourceCode) {
		if (!sourceCode.contains("vec4 color()")) {
			System.err.println("Failed to load the subshader. Please make sure the code is valid glsl code.");
			throw new InvalidSubshaderException();
		}
		
		this.sourceCode = sourceCode;
	}
	
	
	public static ColorSubshader loadSubshader(String path) {
		return new ColorSubshader(FileUtils.loadShaderSourceCode(path));
	}
	
	public String getSourceCode() {
		return sourceCode;
	}

}
