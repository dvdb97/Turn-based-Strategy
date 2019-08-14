package interaction.input;

import org.lwjgl.glfw.*;

public class MouseInputHandler {
	
	/**
	 * 
	 * @author Dario
	 *
	 * Helper class to abstract all mouse input (both cursor position and mouse key input) into one class.
	 * 
	 */
	private class CursorPosInput implements GLFWCursorPosCallbackI {
		
		private MouseInputHandler mouseInputHandler;
		
		public CursorPosInput(MouseInputHandler mouseInputHandler) {
			this.mouseInputHandler = mouseInputHandler;
		}
		
		@Override
		public void invoke(long window, double xpos, double ypos) {
			mouseInputHandler.invokeMouseMovementEvent(xpos, ypos);	
		}
	}
	
	
	/**
	 * 
	 * @author Dario
	 *
	 * Helper class to abstract all mouse input (both cursor position and mouse key input) into one class.
	 * 
	 */
	private class MouseButtonInput implements GLFWMouseButtonCallbackI {

		private MouseInputHandler mouseInputHandler;
		
		public MouseButtonInput(MouseInputHandler mouseInputHandler) {
			this.mouseInputHandler = mouseInputHandler;
		}
		
		@Override
		public void invoke(long window, int button, int action, int mods) {
			mouseInputHandler.invokeMouseButtonEvent(button, action, mods);		
		}
	}
	
	
	private CursorPosInput cursorPosInput;
	private MouseButtonInput mouseButtonInput;
	
	
	public MouseInputHandler() {
		this.cursorPosInput = new CursorPosInput(this);
		this.mouseButtonInput = new MouseButtonInput(this);
	}
	
	
	private void invokeMouseMovementEvent(double xpos, double ypos) {
		//TODO
	}
	
	
	private void invokeMouseButtonEvent(int button, int action, int mods) {
		//TODO
	}


	public CursorPosInput getCursorPosInputHandler() {
		return cursorPosInput;
	}


	public MouseButtonInput getMouseButtonInputHandler() {
		return mouseButtonInput;
	}
	
}
