package assets.shaders.standardShaders;

import assets.cameras.Camera;
import assets.material.Material;
import assets.shaders.ShaderProgram;
import assets.textures.Texture;
import math.matrices.Matrix44f;
import utils.FileUtils;

public class CircleShader extends ShaderProgram {
	
	public CircleShader(String vertSource, String fragSource) {
		super(vertSource, fragSource);
	}
	
	
	public static CircleShader create() {
		String vert = FileUtils.loadShaderSourceCode("Shaders/CircleShader/circle.vert");
		String frag = FileUtils.loadShaderSourceCode("Shaders/CircleShader/circle.frag");
		
		return new CircleShader(vert, frag);
	}
	
	
	@Override
	public void setMaterial(Material material) {
		this.setUniformVector4f("material.color", material.color.toVector4f());
	}
	
	
	public void setMaterialTexture(Texture texture) {
		this.bindTexture("material.texture", texture);
	}


	@Override
	public void setCamera(Camera camera) {
		this.viewProjectionMatrix = camera.getViewProjectionMatrix();
	}
	

	@Override
	public void setModelMatrix(Matrix44f model) {
		//Combine the matrices and pass the result to the shader.
		this.setUniformMatrix4fv("mvpMatrix", viewProjectionMatrix.times(model));
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

}
