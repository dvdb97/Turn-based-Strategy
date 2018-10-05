package interaction;


import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;


public class Window {
	
	public static Window main;
	
	//****************************** screen information ******************************
	
	private int screenWidth;
	private int screenHeight;
	
	//******************************  needed for UI   ********************************
	
	//doubleBuffers needed for glfwGetMousePos() to save the data in
	private DoubleBuffer xPos;
	private DoubleBuffer yPos;
	
	private IntBuffer width;
	private IntBuffer height;
	
	
	//****************************** member variables ********************************
	
	
	private long windowID;
	
	private int windowWidth;
	private int windowHeight;
	
	private GLFWVidMode vidmode;
	
	
	//****************************** window creation stuff ******************************
	
	
	public Window() {
		
		if (main == null)
			main = this;
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		screenWidth = vidmode.width();
		screenHeight = vidmode.height();
		
		//create doubleBuffers needed for UI
		xPos = BufferUtils.createDoubleBuffer(1);
		yPos = BufferUtils.createDoubleBuffer(1);
		
		width = BufferUtils.createIntBuffer(1);
		height = BufferUtils.createIntBuffer(1);
		
	}
	
	
	public boolean createFullscreenWindow(String title) {
		return createWindow(screenWidth, screenHeight, title, glfwGetPrimaryMonitor());
	}
	
	
	public boolean createWindowedWindow(String title) {
		return createWindow(screenWidth / 2, screenHeight / 2, title, NULL);
	}
	
	
	private boolean createWindow(int width, int height, String title, long monitor) {
		
		windowWidth = width;
		windowHeight = height;
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		windowID = glfwCreateWindow(width, height, title, monitor, NULL);
		if (windowID == NULL) {
			return false;
		}
		
		glfwMakeContextCurrent(windowID);
		
		return true;
		
	}
	
	
	public void close() {
		glfwSetWindowShouldClose(windowID, true);;
		glfwDestroyWindow(windowID);
	}
	
	
	//****************************** user input ******************************
	
	
	public void setKeyInputCallback(GLFWKeyCallback inputCallback) {
		glfwSetKeyCallback(this.getWindowID(), inputCallback);
	}
	
	
	public void setMouseInputCallback(GLFWMouseButtonCallback mouseInputCallback) {
		glfwSetMouseButtonCallback(windowID, mouseInputCallback);
	}
	
	
	public void setMousePosInput(GLFWCursorPosCallback mousePosCallback) {
		glfwSetCursorPosCallback(windowID, mousePosCallback);
	}
	
	
	public boolean isExitRequested() {
		return glfwWindowShouldClose(windowID);
	}
	
	
	public void exitRequested(boolean value) {
		glfwSetWindowShouldClose(windowID, value);
	}
	
	
	public boolean keyPressed(int key) {
		return glfwGetKey(windowID, key) == GLFW_PRESS;
	}
	
	
	public double getMouseXPos() {
		glfwGetCursorPos(windowID, xPos, yPos);
		
		return xPos.get(0);
	}
	
	
	public double getMouseYPos() {
		glfwGetCursorPos(windowID, xPos, yPos);
		
		return yPos.get(0);
	}
	
	
	/** 
	 * @return Returns the width of the framebuffer in pixel.
	 */
	public int getFrameBufferWidth() {		
		glfwGetFramebufferSize(windowID, width, height);
		
		return width.get(0);
	}
	
	
	/**
	 * @return Returns the height of the framebuffer in pixel.
	 */
	public int getFrameBufferHeight() {
		glfwGetFramebufferSize(windowID, width, height);
		
		return height.get(0);
	}
	
	
	//mouse click
	public boolean isMouseLeftClicked() {
		if (glfwGetMouseButton(windowID, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean isMouseRightClicked() {
		if (glfwGetMouseButton(windowID, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
			return true;
		} else {
			return false;
		}
	}
	
	//TODO: add mouse click and scroll
	
	//****************************** Utility functions ******************************
	
	
	public float toNormalizedXCoordinates(double xPosInPixel) {
		return (float)(2.0f * xPosInPixel) / this.screenWidth - 1.0f;
	}
	
	
	public float toNormalizedYCoordinates(double yPosInPixel) {
		return (float)(-2.0f * yPosInPixel) / this.screenHeight + 1.0f;
	}
	
	
	//****************************** Getters & Setters ******************************
	
	//TODO: calculation may be incorrect
	public float getProportions() {
		return (float)windowWidth / (float)windowHeight;
	}
	
	
	public long getWindowID() {
		return windowID;
	}
	
	
	public int getWidth() {
		return windowWidth;
	}
	
	
	public int getHeight() {
		return windowHeight;
	}

}
