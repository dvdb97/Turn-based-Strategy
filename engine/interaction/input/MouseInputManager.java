package interaction.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import input.Mouse;


public class MouseInputManager extends GLFWMouseButtonCallback {

	
	private static boolean[] buttons = new boolean[Short.MAX_VALUE];
	
	
	public static boolean isButtonPressed(int button) {
		return buttons[button];
	}
	
	
	public static boolean isLeftMouseButtonPressed() {
		return buttons[GLFW_MOUSE_BUTTON_LEFT];
	}
	
	
	public static boolean isRightMouseButtonPressed() {
		return buttons[GLFW_MOUSE_BUTTON_RIGHT];
	}
	
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		
		if (action == GLFW_PRESS) {
			buttons[button] = true;
		}
				
		if (action == GLFW_RELEASE) {
			buttons[button] = false;
		}
		
	}

}
