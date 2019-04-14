package interaction.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.TreeSet;

import org.lwjgl.glfw.GLFWKeyCallback;


public class KeyInputHandler extends GLFWKeyCallback {
	
	//All keys that have been pressed down last iteration.
	private static TreeSet<Integer> keysDown = new TreeSet<Integer>();
	
	//All keys that have been continuously pressed down for multiple iterations.
	private static TreeSet<Integer> keysPressed = new TreeSet<Integer>();
	
	//All keys that have been released last iteration.
	private static TreeSet<Integer> keysReleased = new TreeSet<Integer>();
	
	
	//The current KeyEventManager that handles all events for the key input.
	private static KeyEventManager eventSystem = new KeyEventManager();
	
	
	/**
	 * 
	 * Replace the old KeyEventManager with a new one.
	 * 
	 * @param keyEventManager The new KeyEventManager.
	 */
	public static void setKeyEventManager(KeyEventManager keyEventManager) {
		eventSystem = keyEventManager;
	}
	
	
	public static KeyEventManager getKeyEventManager() {
		return eventSystem;
	}
	
	
	public static void pollEvents() {
		for (int key : keysDown) {
			eventSystem.triggerKeyDownEvent(key);
			
			keysDown.remove(key);
			keysPressed.add(key);
		}
		
		for (int key : keysPressed) {
			eventSystem.triggerKeyPressedEvent(key);
		}
		
		for (int key : keysReleased) {
			eventSystem.triggerKeyReleasedEvent(key);
			keysReleased.remove(key);
		}
	}
	
	
	/**
	 * 
	 * @param key The key of interest
	 * @return Returns true if the key is currently pressed
	 */
	public static boolean keyPressed(int key) {
		return keysDown.contains(key) || keysPressed.contains(key);
	}
	
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (key < 0 || key >= 400) {
			return;
		}
		
		if (action == GLFW_PRESS) {
			keysDown.add(key);
		}
				
		if (action == GLFW_RELEASE) {			
			if (keysPressed.contains(key)) {
				keysPressed.remove(key);
				keysReleased.add(key);
			}
			
			if (keysDown.contains(key)) {
				keysDown.remove(key);
				keysReleased.add(key);
			}
		}
	}

}
