package fontRendering.rendering.shader;

import assets.shaders.ShaderProgram;
import math.matrices.Matrix44f;
import utils.FileUtils;

public class TextRenderingShader extends ShaderProgram {

	public TextRenderingShader() {
		
		super(FileUtils.loadShaderSourceCode("Shaders/GUI/Font/FontShader.vert"), FileUtils.loadShaderSourceCode("Shaders/GUI/Font/FontShader.frag"));		
		
	}
	
	
	public void prepareForRendering(Matrix44f mvpMatrix) {		
		this.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());
	}

}
