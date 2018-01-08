package gui_core;

public class GUIManager {
	
	private static int idCounter;
	
	
	public static void init() {
		idCounter = 0;
	}
	
	
	public static int generateID() {
		return idCounter++;
	}

}
