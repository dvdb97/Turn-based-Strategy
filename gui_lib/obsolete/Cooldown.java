package obsolete;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/*
 * A class that manages cooldowns. When started it can be used to
 * track whether the cooldown is still active or has finished.
 */
public class Cooldown {
	
	private double duration;
	
	private double start;
	
	private boolean active;
	
	
	public Cooldown(double duration) {
		
		this.duration = duration;
		
	}
	
	
	/**
	 * Starts the cooldown if there is no other other cooldown running.
	 */
	public void start() {
		
		if (!isFinished()) {
			return;
		}
		
		this.active = true;
		
		start = glfwGetTime();
	}
	
	
	/**
	 * 
	 * Returns whether the cooldown is already finished. 
	 * 
	 * @return Returns whether the cooldown is already finished.
	 */
	public boolean isFinished() {
		
		if (!active) {
			return true;
		}
		
		if (glfwGetTime() - start >= duration) {
			active = false;
			
			return true;
		}
		
		return false;
		
	}
	
	
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	
	public double getDuration() {
		return duration;
	}
	
}
