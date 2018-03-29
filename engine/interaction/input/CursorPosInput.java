package interaction.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import interaction.Window;

public class CursorPosInput extends GLFWCursorPosCallback {
	
<<<<<<< HEAD
	private static Window window;	
=======
	private static Window window;
>>>>>>> master

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
<<<<<<< HEAD
	}	
	
	
	public static float getXPosAsOpengl() {
		return window.toNormalizedXCoordinates(getXPos());
	}
	
	
	public static float getYPosAsOpengl() {
=======
	}
	
	
	public static float getXPosAsOpenglCoord() {
		return window.toNormalizedXCoordinates(getXPos());
	}
	
	
	public static float getYPosAsOpenglCoord() {
>>>>>>> master
		return window.toNormalizedYCoordinates(getYPos());
	}

}
