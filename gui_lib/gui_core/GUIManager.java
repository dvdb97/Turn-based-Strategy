package gui_core;

import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
import math.matrices.Matrix33f;
import math.vectors.Vector4f;

public class GUIManager {
	
	private static boolean initialized = false;
	
	private static ShaderProgram guiShader;
	
	private static int idCounter;
	
	public static void init() {
		
		if (initialized) {
			return;
		}
		
		idCounter = 0;
		
		guiShader = ShaderLoader.loadShader("Shaders/GUI/GUI.vert", "Shaders/GUI/GUI.frag");
		
		initialized = true;
		
	}
	
	
	public static int generateID() {		
		return idCounter++;
	}
	
	
	public static void useGuiShader(Matrix33f renderingMatrix) {
		
		if (!initialized) {
			init();
		}
		
		guiShader.use();
		
		guiShader.setUniform1i("u_textured", 1);
	}
	
	
	public static void useGuiShader(Matrix33f renderingMatrix, Vector4f color) {
		
		if (!initialized) {
			init();
		}
		
		guiShader.use();
		
		guiShader.setUniform1i("u_textured", 0);
		guiShader.setUniformVector4f("u_color", color);
		
	}
	
	
	public static void disableGuiShader() {
		
		if (!initialized) {
			init();
		}
		
		guiShader.disable();
	}

}
