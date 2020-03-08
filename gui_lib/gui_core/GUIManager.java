package gui_core;

import java.util.HashSet;
import fundamental.GUIWindow;
import input.Mouse;
import interaction.Window;
import interaction.input.CursorPosInput;
import rendering.Renderer2D;

public class GUIManager {
	
	private static boolean initialized = false;	
	
	private static int width, height;
	
	private static HashSet<GUIWindow> windows;
	
	private static Input input;
	
	
	/**
	 * Disable the constructor as this class is only used in a static way. 
	 */
	private GUIManager() {}
	
	
	public static void init(Window window) {
		if (initialized) {
			return;
		}
		
		initialized = true;
		
		windows = new HashSet<GUIWindow>();
		width = window.getWidth();
		height = window.getHeight();
		input = new Input();
		
		Mouse.init();
	}
	
	
	/**
	 * 
	 * @return true,  if any window is hit
	 */
	public static void processInput() {
		Mouse.update();
		
		for (GUIWindow window : windows) {
			if (window.isObsolete()) {
				windows.remove(window);
				window.delete();
			}
		}
		
		int x = (int) CursorPosInput.getXPos();
		int y = (int) CursorPosInput.getYPos();
		
		input.dx = x - input.cursorX;
		input.dy = y - input.cursorY;
		input.cursorX = x;
		input.cursorY = y;
		input.leftMouseButton = Mouse.isLeftButtonPressed();
		input.rightMouseButton = Mouse.isRightButtonPressed();
		
		windows.forEach((e) -> e.processInput(input));
	}
	

	public static void render() {
		for (GUIWindow window : windows) {
			window.render();
		}
		
		Renderer2D.endFrame();
	}
	
	
	public static void setPrimaryWindow(GUIWindow window) {
		if (windows.contains(window)) {
			windows.remove(window);
			windows.add(window);
		} else {
			System.err.println("GUIManager: window not found");
		}
	}
	
	
	public static boolean isInitialized() {
		return initialized;
	}
	
	
	public static void addWindow(GUIWindow window) {
		windows.add(window);
	}
	
	
	public static void remove(GUIWindow window) {
		windows.remove(window);
	}
	
	
	public static void delete() {
		windows.forEach((e) -> e.delete());
	}

}
