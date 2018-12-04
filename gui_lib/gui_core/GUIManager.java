package gui_core;

<<<<<<< HEAD
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
=======
import java.util.LinkedList;

import fontRendering.font.GUIFontCollection;
import fundamental.ClickableElement;
import fundamental.GUIWindow;
import input.Mouse;
import interaction.Window;
<<<<<<< HEAD
import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
>>>>>>> master
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;
=======
>>>>>>> gui_changes

public class GUIManager {
	
	
	private static boolean initialized = false;
	
	private static int idCounter;
	
	
	private static LinkedList<GUIWindow> windows;
	
	private static int width;
	
	private static int height;
	
	private static ClickableElement clickedElement;
	
	
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
		
		Mouse.init();
		
	}
	
	//***************************************************************************************
	
	/**
	 * 
	 * @return true,  if any window is hit
	 */
	public static boolean processInput() {
		
		Mouse.update();
		
		if (clickedElement != null) {
			
			clickedElement.processInput();
			return true;
			
		} else {
			
			for (GUIWindow window : windows) {
				
				if (window.processInput()) {
					return true;
				}
				
			}
			
		}
		
		return false;
		
	}
	
	public static void update() {
		
		for (GUIWindow window : windows) {
			
			//TODO: Only call it when there were changes
			//updates rendering matrices (and its inversions)
			window.update();
		}
		
<<<<<<< HEAD
<<<<<<< HEAD
		guiShader.bind();
=======
>>>>>>> master
=======
	}

	public static void render() {
>>>>>>> gui_changes
		
		for (GUIWindow window : windows) {
			
			window.render();
			
		}
		
	}
	
	//************************** clicked element ******************************************
	
	public static void setClickedElement(ClickableElement element) {
		
		if (clickedElement == null) {
			clickedElement = element;
		} else {
			System.err.println("GUIManager: clickedElement already set");
		}
		
	}
	
	
	public static void resetClickedELement(ClickableElement element) {
		
		if (clickedElement == element) {
			clickedElement = null;
		} else {
			//TODO: error report
			System.err.println("GUIManager: TODO");
		}
		
	}
	
	//***************************************************************************************
	
	public static void setPrimaryWindow(GUIWindow window) {
		if (windows.contains(window)) {
			windows.remove(window);
			windows.add(window);
		} else {
			System.err.println("GUIManager: window not found");
		}
	}
	
	
	//***************************************************************************************
	
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
