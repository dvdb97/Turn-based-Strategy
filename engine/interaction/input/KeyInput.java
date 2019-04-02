package interaction.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;

import org.lwjgl.glfw.GLFWKeyCallback;


public class KeyInput extends GLFWKeyCallback {
	
	private static boolean[] keys = new boolean[400];
	private static HashMap<Integer, KeyEvent> keyEvents = new HashMap<Integer, KeyEvent>();
	
	public static boolean keyPressed(int key) {
		return keys[key];
	}
	
	public static void addKeyEvent(int key, KeyEvent event) {
		keyEvents.put(key, event);
	}
	
	public static void removeKeyEvent(int key) {
		keyEvents.remove(key);
	}
	
	public static void pollEvents() {
		for (int key : keyEvents.keySet()) {
			if (keyPressed(key)) {
				keyEvents.get(key).keyPressed(key);
			}
		}
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		if (key < 0 || key >= 400) {
			return;
		}
		
		if (action == GLFW_PRESS) {
			keys[key] = true;
		}
				
		if (action == GLFW_RELEASE) {
			keys[key] = false;
		}		
	}

}
