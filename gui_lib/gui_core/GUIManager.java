package gui_core;

<<<<<<< HEAD
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
=======
import java.util.LinkedList;

import assets.textures.Texture;
import assets.textures.Texture2D;
import elements.containers.GUIWindow;
import fontRendering.font.GUIFontCollection;
import graphics.matrices.Matrices;
import interaction.Window;
import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
>>>>>>> master
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;

public class GUIManager {
	
	
	private static boolean initialized = false;
	
	private static int idCounter;
	
	
	private static LinkedList<GUIWindow> windows;
	
	private static int width;
	
	private static int height;
	
	
	private GUIManager() {
		
	}
	
	public static void init(Window window) {
		
		if (initialized) {
			return;
		}
		
		GUIShaderCollection.init(window);
		
		GUIFontCollection.init();
		
		idCounter = 0;
		
		initialized = true;
		
		windows = new LinkedList<GUIWindow>();
		
		width = window.getWidth();
		
<<<<<<< HEAD
		guiShader.bind();
=======
		height = window.getHeight();
>>>>>>> master
		
	}
	
	
	public static void update() {
		
		float cursorX = CursorPosInput.getXPosAsOpengl();
		float cursorY = CursorPosInput.getYPosAsOpengl();
		
		boolean leftMouseButtonDown = MouseInputManager.isLeftMouseButtonPressed();
		boolean rightMouseButtonDown = MouseInputManager.isRightMouseButtonPressed();
		
		for (GUIWindow window : windows) {
			
			//TODO: Only call it when there were changes
			window.update();
			
			if (window.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown)) {
				break;
			}
			
		}
		
<<<<<<< HEAD
		guiShader.bind();
=======
>>>>>>> master
		
		for (GUIWindow window : windows) {
			
			window.render();
			
		}
		
	}
	
	
<<<<<<< HEAD
	public static void disableGuiShader() {
		
		if (!initialized) {
			init();
		}
		
		guiShader.unbind();
=======
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
>>>>>>> master
	}

}
