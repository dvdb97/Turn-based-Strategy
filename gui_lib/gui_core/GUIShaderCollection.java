package gui_core;

import math.matrices.Matrix44f;
import math.vectors.Vector4f;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;

public class GUIShaderCollection {
	
	private static boolean initialized = false;
	
	private static ShaderProgram guiShader;
	
	
	public static void init() {
		
		if (initialized) {
			return;
		}
		
		guiShader = ShaderLoader.loadShader("Shaders/GUI/GUI.vert", "Shaders/GUI/GUI.frag");
		
		initialized = true;
		
	}
	
	
	public static void useFontShader(Matrix44f renderingMatrix) {
	
		if (!initialized) {
			init();
		}
		
		guiShader.use();
		
		guiShader.setUniformMatrix4fv("u_Matrix", renderingMatrix.toArray());
		guiShader.setUniform1i("u_textured", 1);
		guiShader.setUniform1i("u_fontRendering", 1);
		
	}
	
	
	public static void disableFontShader() {
		
		if (!initialized) {
			init();
		}
		
		guiShader.disable();
		
	}
	
	
	/**
	 * 
	 * Enables the gui shader for textured rendering
	 * 
	 * @param renderingMatrix The transformation matrix for the model
	 */
	public static void useGuiShader(Matrix44f renderingMatrix) {
		
		if (!initialized) {
			init();
		}
		
		guiShader.use();
		
		guiShader.setUniformMatrix4fv("u_Matrix", renderingMatrix.toArray());
		guiShader.setUniform1i("u_textured", 1);
		guiShader.setUniform1i("u_fontRendering", 0);
		
	}
	
	
	/**
	 * 
	 * Enables the gui shader for colored rendering
	 * 
	 * @param renderingMatrix The transformation matrix for the model
	 * @param color The color to render the model with
	 */
	public static void useGuiShader(Matrix44f renderingMatrix, Vector4f color) {
		
		if (!initialized) {
			init();
		}
		
		guiShader.use();
		
		guiShader.setUniformMatrix4fv("u_Matrix", renderingMatrix.toArray());
		guiShader.setUniform1i("u_textured", 0);
		guiShader.setUniform1i("u_fontRendering", 0);
		guiShader.setUniformVector4f("u_Color", color);
		
	}
	
	
	/**
	 * 
	 * Disables the shader.
	 * 
	 */
	public static void disableGuiShader() {
		
		if (!initialized) {
			init();
		}
		
		guiShader.disable();
	}

}
