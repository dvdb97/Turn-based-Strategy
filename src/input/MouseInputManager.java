package input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWMouseButtonCallback;


public class MouseInputManager extends GLFWMouseButtonCallback {

	private static boolean[] buttons = new boolean[Short.MAX_VALUE];
	
	public static boolean isButtonPressed(int button) {
		return buttons[button];
	}
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		buttons[button] = action == GLFW_RELEASE;
	}

}
