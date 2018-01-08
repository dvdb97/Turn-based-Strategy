package timer;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Cooldown {
	
	private double duration;
	
	private double start;
	
	private boolean active;
	
	
	public Cooldown(double duration) {
		
	}
	
	
	public void start() {
		this.active = false;
		
		start = glfwGetTime();
	}
	
	
	public boolean isFinished() {
		
		if (!active) {
			return true;
		}
		
		if (glfwGetTime() - start <= 0) {
			active = false;
			return true;
		}
		
		return false;
		
	}
	
	
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	
	public boolean isActive() {
		return active;
	}
	
}
