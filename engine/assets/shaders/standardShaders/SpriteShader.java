package assets.shaders.standardShaders;

import assets.material.Material;
import assets.shaders.ShaderProgram;
import assets.textures.Texture;
import math.matrices.Matrix44f;
import utils.FileUtils;

public class SpriteShader extends ShaderProgram {

	private static final String PATH = "Shaders/StandardShaders/";
	
	private SpriteShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
		// TODO Auto-generated constructor stub
	}
	
	
	public static SpriteShader create() {
		String vert = FileUtils.loadShaderSourceCode(PATH + "sprite.vert");
		String frag = FileUtils.loadShaderSourceCode(PATH + "sprite.frag");
		
		return new SpriteShader(vert, frag);
	}
	
	
	public static SpriteShader createInstancedRenderingShader() {
		String vert = FileUtils.loadShaderSourceCode(PATH + "spriteInstanced.vert");
		String frag = FileUtils.loadShaderSourceCode(PATH + "sprite.frag");
		
		return new SpriteShader(vert, frag);
	}
	
	
	@Override
	public void setMaterial(Material material) {
		this.setUniformVector4f("material.color", material.color.toVector4f());
	}
	
	
	public void setMaterialTexture(Texture texture) {
		this.bindTexture("material.texture", texture);
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
