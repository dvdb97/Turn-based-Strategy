package interaction.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;


public class KeyInputHandler extends GLFWKeyCallback {
	
	
	private static boolean[] keys = new boolean[400];	
	
	
	public static boolean keyPressed(int key) {
		return keys[key];
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
