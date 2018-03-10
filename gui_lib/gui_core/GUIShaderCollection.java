package gui_core;

import interaction.Window;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;
import rendering.matrices.projection.ProjectionMatrix;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;

public class GUIShaderCollection {
	
	private static boolean initialized = false;
	
	private static ShaderProgram guiShader;
	
	private static ProjectionMatrix projectionMatrix;
	
	
	public static void init(Window window) {
		
		if (initialized) {
			return;
		}
		
		projectionMatrix = ProjectionMatrix.generateOrthographicProjectionMatrix(window.getProportions());
		
		guiShader = ShaderLoader.loadShader("Shaders/GUI/GUI.vert", "Shaders/GUI/GUI.frag");
		
		initialized = true;
		
	}
	
	
	public static void useFontShader(Matrix44f renderingMatrix) {
	
		if (!initialized) {
			return;
		}
		
		guiShader.use();
		
		guiShader.setUniformMatrix4fv("u_Matrix", renderingMatrix.toArray());
		guiShader.setUniformMatrix4fv("u_ProjectionMatrix", projectionMatrix.toArray());
		guiShader.setUniform1i("u_textured", 1);
		guiShader.setUniform1i("u_fontRendering", 1);
		
	}
	
	
	public static void disableFontShader() {
		
		if (!initialized) {
			return;
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
			return;
		}
		
		guiShader.use();
		
		guiShader.setUniformMatrix4fv("u_Matrix", renderingMatrix.toArray());
		guiShader.setUniformMatrix4fv("u_ProjectionMatrix", projectionMatrix.toArray());
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
			return;
		}
		
		guiShader.use();
		
		guiShader.setUniformMatrix4fv("u_Matrix", renderingMatrix.toArray());
		guiShader.setUniformMatrix4fv("u_ProjectionMatrix", projectionMatrix);
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
			return;
		}
		
		guiShader.disable();
	}

}
