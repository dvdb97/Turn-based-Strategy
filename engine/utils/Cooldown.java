package utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.util.HashMap;

public class Cooldown {
	
	private static HashMap<String, Cooldown> cooldowns = new HashMap<String, Cooldown>();
	
	private double duration;
	private double start;
	
	/**
	 * 
	 * Starts a cooldown with the given duration.
	 * 
	 * @param duration The duration of the cooldown.
	 */
	public Cooldown(double duration) {
		this.duration = duration;
		start();
	}
	
	
	/**
	 * Starts a cooldown.
	 */
	public void start() {
		this.start = glfwGetTime();
	}
	
	
	/**
	 * 
	 * Starts a cooldown with the given name and duration. Using the name, the
	 * cooldown can be accessed statically.
	 * 
	 * @param name The cooldown's name.
	 * @param duration The cooldown's duraion.
	 */
	public static void start(String name, double duration) {
		if (cooldowns.containsKey(name)) {
			System.out.println("There already is a cooldown with the given name.");
			return;
		}
		
		cooldowns.put(name, new Cooldown(duration));
	}
	
	
	/**
	 * Refreshes the cooldown.
	 */
	public void refresh() {
		start();
	}
	
	
	/**
	 * 
	 * Refreshes the duration of the cooldown with the given name.
	 * 
	 * @param name The name of the cooldown.
	 */
	public static void refresh(String name) {
		if (!cooldowns.containsKey(name)) {
			System.out.println("There is no cooldown with the given name.");
			return;
		}
		
		cooldowns.get(name).refresh();
	}
	
	
	/**
	 * 
	 * @return Returns true if the cooldown has ended.
	 */
	public boolean ended() {
		return glfwGetTime() - start >= duration;
	}
	
	
	/**
	 * 
	 * @param name The name of the cooldown.
	 * @return Returns true if the cooldown has ended.
	 */
	public static boolean ended(String name) {
		if (!cooldowns.containsKey(name)) {
			System.out.println("There is no cooldown with the given name.");
			return true;
		}
		
		return cooldowns.get(name).ended();
	}
	
	
	/**
	 * 
	 * @return Returns the cooldown's duration.
	 */
	public double getDuration() {
		return duration;
	}
	
	
	/**
	 * 
	 * @param name The name of the cooldown. 
	 * @return Returns the cooldown's duration.s
	 */
	public static double getDuration(String name) {
		return cooldowns.remove(name).getDuration();
	}
	
}
