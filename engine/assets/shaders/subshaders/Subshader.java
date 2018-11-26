package assets.shaders.subshaders;

import utils.FileUtils;

public class Subshader {
	
	/**
	 * 
	 * @author Dario
	 *
	 * An exception that can be thrown if the source code doesn't
	 * match the requirements for a subshader.
	 */
	private class InvalidSubshaderException extends RuntimeException {}
	
	
	private String sourceCode;
	
	
	public Subshader(String sourceCode) {
		if (!sourceCode.contains("vec4 color()")) {
			System.err.println("Failed to load the subshader. Please make sure the code is valid glsl code.");
			throw new InvalidSubshaderException();
		}
		
		this.sourceCode = sourceCode;
	}
	
	
	public static Subshader loadSubshader(String path) {
		return new Subshader(FileUtils.loadShaderSourceCode(path));
	}
	
	public String getSourceCode() {
		return sourceCode;
	}

}
