package performance;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.util.HashMap;

public class SpeedTester {
	
	private static HashMap<String, Double> timers = new HashMap<String, Double>();
	
	
	/**
	 * 
	 * Runs the program an measures the time it took to
	 * complete.
	 * 
	 * @param programm The program to run.
	 */
	public static void assessSpeed(SpeedTesting program) {
		start("Speed test");
		program.run();
		end("Speed test");
	}
	
	
	/**
	 * 
	 * Starts a timer. Call end to stop the timer and log the
	 * time that passed.
	 * 
	 * @param name The name of the timer. It has to be unique.
	 */
	public static void start(String name) {
		if (timers.containsKey(name)) {
			System.out.println("The timer '" + name + "' already exists.");
			return;
		}
		
		timers.put(name, glfwGetTime());
	}
	
	
	/**
	 * 
	 * Logs the time that passed between the calls of "start"
	 * and "end".
	 * 
	 * @param name The name of the timer.
	 * @return Returns the time that passed.
	 */
	public static double timestamp(String name) {
		if (!timers.containsKey(name)) {
			System.out.println("Timer " + "'name' doesn't exist.");
			return 0.0;
		}
		
		double time = glfwGetTime() - timers.get(name);
		System.out.println("Timestamp '" + name + "': " + time + " sec");
		
		return time;
	}
	
	
	/**
	 * 
	 * Logs the time that passed between the calls of "start"
	 * and "end". It also stops the timer.
	 * 
	 * @param name The name of the timer.
	 * @return Returns the time that passed.
	 */
	public static double end(String name) {
		if (!timers.containsKey(name)) {
			System.out.println("Timer " + "'name' doesn't exist.");
			return 0.0;
		}
		
		double time = glfwGetTime() - timers.remove(name);
		System.out.println("End '" + name + "': " + time + " sec");
		
		return time;
	}
}
