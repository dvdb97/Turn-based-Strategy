package elements.containers.layouts;

public abstract class GUILayout {
	
	private static int windowWidth, windowHeight;
	
	
	/**
	 * 
	 * Initializes the static section of this class
	 * 
	 * @param windowWidth The width of the window that is 
	 * @param windowHeight
	 */
	public static void init(int windowWidth, int windowHeight) {
		GUILayout.windowWidth = windowWidth;
		GUILayout.windowHeight = windowHeight;
	}
	
	
	/**
	 * 
	 * Converts the x position of a pixel on the screen to a OpenGL coordinate 
	 * 
	 * @param x The x position of the pixel
	 * @return Returns the OpenGL x coord of the given pixel
	 */
	protected static float convertXPos(int x) {
		return 2f * ((float)x / (float)windowWidth) - 1f; 
	}
	
	
	/**
	 * 
	 * Converts the y position of a pixel on the screen to a OpenGL coordinate 
	 * 
	 * @param y The y position of the pixel
	 * @return Returns the OpenGL y coord of the given pixel
	 */
	protected static float convertYPos(int y) {
		return 2f * ((float)y / (float)windowHeight) - 1f;
	}
	
	
	/**
	 * 
	 * Converts the width in pixel on the screen to a OpenGL values
	 * 
	 * @param width The width in pixel
	 * @return Returns the width converted to OpenGL values
	 */
	protected static float convertWidth(int width) {
		return 2f * ((float)width / (float)windowWidth);
	}
	
	
	/**
	 * 
	 * Converts the height in pixel on the screen to a OpenGL values
	 * 
	 * @param height The height in pixel
	 * @return Returns the height converted to OpenGL values
	 */
	protected static float convertHeight(int height) {
		return 2f * ((float)height / (float)windowHeight);
	}
	
	
}
