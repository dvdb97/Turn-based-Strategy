package interaction.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashSet;
import java.util.Stack;
import org.lwjgl.glfw.GLFWKeyCallback;


public class KeyInputHandler extends GLFWKeyCallback {
	
	//All keys that have been pressed down last iteration. (GLFW_PRESS)
	private static HashSet<Integer> keysPressed = new HashSet<Integer>();
	
	//All keys that have been continuously pressed down for multiple iterations. (kind of GLFW_REPEAT)
	private static HashSet<Integer> keysRepeated = new HashSet<Integer>();
	
	//All keys that have been released last iteration. (GLFW_RELEASE)
	private static HashSet<Integer> keysReleased = new HashSet<Integer>();
	
	
	//The current KeyEventManager that handles all events for the key input.
	//private static KeyEventManager keyEventManager = new KeyEventManager();
	private static Stack<KeyEventManager> keyEventManagers = new Stack<>();
	
	public KeyInputHandler() {
		addKeyEventManager(new KeyEventManager());
	}
	
	/**
	 *  put a KeyEventManager on top of the KeyEventManager-Stack
	 *  
	 *  @param keyEventManager this KeyEventManager will manage key events until you remove this KEM or add another one
	 */
	public static void addKeyEventManager(KeyEventManager keyEventManager) {
		keyEventManagers.push(keyEventManager);
	}
	
	public static void removeKeyEventManager() {
		keyEventManagers.pop();
	}
	
	/*/**
	 * removes this KeyEventManager in case it is on top of the stack
	 * @param keyEventManager KEM you want to remove
	 * @return true, if KEM was actually removed
	 */
	/*public static boolean removeKeyEventManager(KeyEventManager keyEventManager) {
		if (keyEventManagers.peek() == keyEventManager) {
			keyEventManagers.pop();
			return true;
		}
		return false;
	}*/
	
	public static KeyEventManager getKeyEventManager() {
		return keyEventManagers.peek();
	}
	
	
	/**
	 * 
	 * Update all key input.
	 */
	public static void pollEvents() {		
		for (int key : keysPressed) {
			keyEventManagers.peek().triggerKeyDownEvent(key);
			keysRepeated.add(key);
		}
		
		keysPressed.clear();
		
		for (int key : keysRepeated) {
			keyEventManagers.peek().triggerKeyPressedEvent(key);
		}
		
		for (int key : keysReleased) {
			keyEventManagers.peek().triggerKeyReleasedEvent(key);
		}
		
		keysReleased.clear();
	}
	
	
	/**
	 * 
	 * @param key The key of interest
	 * @return Returns true if the key is currently pressed
	 */
	public static boolean keyPressed(int key) {
		return keysPressed.contains(key) || keysRepeated.contains(key);
	}
	
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {		
		if (key < 0 || key >= 400) {
			return;
		}
		
		if (action == GLFW_PRESS) {
			keysPressed.add(key);
		}
				
		if (action == GLFW_RELEASE) {			
			if (keysRepeated.contains(key)) {
				keysRepeated.remove(key);
				keysReleased.add(key);
			}
			
			if (keysPressed.contains(key)) {
				keysPressed.remove(key);
				keysReleased.add(key);
			}
		}
	}

}
