package performance;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class SpeedTester {
	
	public static void assessSpeed(SpeedTesting programm) {
		
		double startTime = glfwGetTime();
		
		programm.run();
		
		System.out.println("The method took " + (glfwGetTime() - startTime) + "");
		
	}

}
