package fontRendering.rendering.shader;

import math.matrices.Matrix44f;
import rendering.shaders.ShaderProgram;

public class TextRenderingShader extends ShaderProgram {

	public TextRenderingShader() {
		super("Shaders/GUI/Font/FontShader.vert", "Shaders/GUI/Font/FontShader.frag");
	}
	
	
	public void prepareForRendering(Matrix44f mvpMatrix) {		
		this.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());
	}

}
