package gui_core;

import java.util.LinkedList;

import assets.textures.Texture;
import assets.textures.Texture2D;
import elements.containers.GUIWindow;
import graphics.matrices.Matrices;
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;

public class GUIManager {
	
	
	private static boolean initialized = false;
	
	private static ShaderProgram guiShader;
	
	private static int idCounter;
	
	
	private static LinkedList<GUIWindow> windows;
	
	
	private GUIManager() {
		
	}
	
	
	public static void init() {
		
		if (initialized) {
			return;
		}
		
		idCounter = 0;
		
		guiShader = ShaderLoader.loadShader("Shaders/GUI/Font/FontShader.vert", "Shaders/GUI/Font/FontShader.frag");
		
		initialized = true;
		
		windows = new LinkedList<GUIWindow>();
		
	}
	
	
	public static void update(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		for (GUIWindow window : windows) {
			
			//TODO: Only call it when there were changes
			window.update();
			
			if (window.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown)) {
				
				break;
			}
			
		}
		
		
		for (GUIWindow window : windows) {
			
			window.render();
			
		}
		
	}
	
	
	public static boolean isInitialized() {
		return initialized;
	}
	
	
	public static int generateID() {		
		return idCounter++;
	}
	
	
	public static void addWindow(GUIWindow window) {
		windows.add(window);
	}
	
	
	public static void remove(GUIWindow window) {
		windows.remove(window);
	}
	
	
	public static void useGuiShader(Matrix44f renderingMatrix) {
		
		if (!initialized) {
			init();
		}
		
		guiShader.use();
		
		guiShader.setUniformMatrix4fv("u_Matrix", renderingMatrix.toArray());
		guiShader.setUniform1i("u_textured", 1);
		
	}
	
	
	public static void useGuiShader(Matrix44f renderingMatrix, Vector4f color) {
		
		if (!initialized) {
			init();
		}
		
		guiShader.use();
		
		guiShader.setUniformMatrix4fv("u_Matrix", renderingMatrix.toArray());
		guiShader.setUniform1i("u_textured", 0);
		guiShader.setUniformVector4f("u_Color", color);
		
	}
	
	
	public static void disableGuiShader() {
		
		if (!initialized) {
			init();
		}
		
		guiShader.disable();
	}

}
