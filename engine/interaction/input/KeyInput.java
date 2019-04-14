package interaction.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;

import org.lwjgl.glfw.GLFWKeyCallback;


public class KeyInput extends GLFWKeyCallback {
	
	private static boolean[] keys = new boolean[400];	
	
	/**
	 * this method sucks! Don't use it!
	 */
	public static boolean keyPressed(int key) {
		return keys[key];
	}
	
	//********************************** better way to do this ************************
	
	private static Map<Integer, List<KeyEvent>> keyEventLists = new HashMap<>();
	private static List<IntConsumer> generalKeyEvents = new ArrayList<>();
	
	public static void addKeyEvent(int key, KeyEvent keyEvent) {
		
		if (!keyEventLists.containsKey(key)) {
			keyEventLists.put(key, new ArrayList<>());
		}
		
		keyEventLists.get(key).add(keyEvent);
		
	}
	
	public static void addGeneralKeyEvent(IntConsumer generalKeyEvent) {
		generalKeyEvents.add(generalKeyEvent);
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		if (key < 0 || key >= 400) {
			return;
		}
		
		if (action == GLFW_PRESS) {
			keys[key] = true;
			keyEventLists.get(key).iterator().forEachRemaining((keyEvent)-> keyEvent.execute());
			for (IntConsumer event : generalKeyEvents) {
				event.accept(key);
			}
			//generalKeyEvents.iterator().forEachRemaining((event) -> event.accept(key));
		}
				
		if (action == GLFW_RELEASE) {
			keys[key] = false;
		}
		
	}

}
