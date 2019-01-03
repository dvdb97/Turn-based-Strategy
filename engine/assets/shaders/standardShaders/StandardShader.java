package assets.shaders.standardShaders;

import assets.material.Material;
import assets.shaders.ShaderProgram;
import assets.textures.Texture;
import utils.FileUtils;

public class StandardShader extends ShaderProgram {

	private StandardShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
		
		this.bind();
		this.useMaterialColor();
		this.unbind();
	}
	
	
	public static StandardShader create() {
		String vert = FileUtils.loadShaderSourceCode("Shaders/StandardShaders/shader.vert");
		String frag = FileUtils.loadShaderSourceCode("Shaders/StandardShaders/shader.frag");
		
		return new StandardShader(vert, frag);
	}
	
	
	public void setMaterial(Material material) {
		this.setUniformVector4f("material.color", material.color);
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
