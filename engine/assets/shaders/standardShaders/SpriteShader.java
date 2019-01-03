package assets.shaders.standardShaders;

import assets.material.Material;
import assets.shaders.ShaderProgram;
import assets.textures.Texture;
import math.matrices.Matrix44f;
import utils.FileUtils;

public class SpriteShader extends ShaderProgram {

	private static final String PATH = "Shaders/StandardShaders/sprite";
	
	private SpriteShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
		// TODO Auto-generated constructor stub
	}
	
	
	public static SpriteShader create() {
		String vert = FileUtils.loadShaderSourceCode(PATH + ".vert");
		String frag = FileUtils.loadShaderSourceCode(PATH + ".frag");
		
		return new SpriteShader(vert, frag);
	}
	
	
	/**
	 * 
	 * Passes the MVPMatrix as an uniform to the shader. 
	 * 
	 * @param matrix The matrix to use as the MVPMatrix.
	 */
	public void setMVPMatrix(Matrix44f matrix) {
		this.setUniformMatrix4fv("mvpMatrix", matrix);
	}
	
	
	@Override
	public void setMaterial(Material material) {
		this.setUniformVector4f("material.color", material.color);
	}


	/**
	 * Tells the shader to look up the fragment's color from the texture.
	 */
	public void useTextureColor() {
		this.setUniformSubroutine("colorFunc", "textureColor", FRAGMENT_SHADER);
	}
	
	
	/**
	 * Tells the shader to take the material's color as the fragment's color.	
	 */
	public void useMaterialColor() {
		this.setUniformSubroutine("colorFunc", "materialColor", FRAGMENT_SHADER);
	}
	
	
	/**
	 * Tells the shader to take the attribute's color as the fragment's color.
	 */
	public void useAttribColor() {
		this.setUniformSubroutine("colorFunc", "attribColor", FRAGMENT_SHADER);
	}

}
