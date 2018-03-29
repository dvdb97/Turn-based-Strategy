package interaction.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import interaction.Window;

public class CursorPosInput extends GLFWCursorPosCallback {
		
	private static Window window;

	private static double xPos;
	
	private static double yPos;
	
	
	public CursorPosInput(Window window) {
		CursorPosInput.window = window;
	}
	
	
	@Override
	public void invoke(long window, double x, double y) {
		xPos = x;
		yPos = y;
	}
	
	
	public static double getXPos() {
		return xPos;
	}
	
	
	public static double getYPos() {
		return yPos;
	}	
	
	
	public static float getXPosAsOpengl() {
		return window.toNormalizedXCoordinates(getXPos());
	}
	
	
	public static float getYPosAsOpengl() {
		return window.toNormalizedYCoordinates(getYPos());
	}
	
	
	public static float getXPosAsOpenglCoord() {
		return window.toNormalizedXCoordinates(getXPos());
	}
	
	
	public static float getYPosAsOpenglCoord() {
		return window.toNormalizedYCoordinates(getYPos());
	}

}
