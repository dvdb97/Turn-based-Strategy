package utils;

import org.lwjgl.glfw.GLFW;

public class Clocker {
	
	double clockTime;
	double setTime;
	
	public Clocker(double clockTime) {
		
		this.clockTime = clockTime;
		
	}
	
	public void setCurrentTime() {
		
		setTime = GLFW.glfwGetTime();
		
	}
	
	public boolean elapsedTimeLegit() {
		
		return GLFW.glfwGetTime() - setTime > clockTime;
		
	}
	
}
