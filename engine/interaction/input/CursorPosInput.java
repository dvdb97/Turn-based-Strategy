package interaction.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorPosInput extends GLFWCursorPosCallback {

	private static double xPos;
	
	private static double yPos;
	
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
	

}
