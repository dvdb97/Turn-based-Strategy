package fontRendering.rendering.shader;

import math.matrices.Matrix44f;
import rendering.shaders.ShaderProgram;
import utils.FileUtils;

public class TextRenderingShader extends ShaderProgram {

	public TextRenderingShader() {
		
		super(FileUtils.loadShaderSourceCode("Shaders/GUI/Font/FontShader.vert"), FileUtils.loadShaderSourceCode("Shaders/GUI/Font/FontShader.frag"));		
		
	}
	
	
	public void prepareForRendering(Matrix44f mvpMatrix) {		
		this.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());
	}

}
