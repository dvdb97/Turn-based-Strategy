package interaction;

import static org.lwjgl.glfw.GLFW.*;

public class KeyBindings {
	
	//Set the key bindings. E.g.:
	private static int mainMenuButton = GLFW_KEY_ESCAPE;
	
	private static int mapTranslationLeft = GLFW_KEY_A;
	
	private static int mapTranslationRight = GLFW_KEY_D;
	
	private static int mapTranslationForward = GLFW_KEY_W;
	
	private static int mapTranslationBackward = GLFW_KEY_S;
	
	private static int mapTranslationUp = GLFW_KEY_R;
	
	private static int mapTranslationDown = GLFW_KEY_F;


	public static void setMapZoomOut(int mapZoomOut) {
		KeyBindings.mapTranslationUp = mapZoomOut;
	}



	public static void setMapZoomIn(int mapZoomIn) {
		KeyBindings.mapTranslationDown = mapZoomIn;
	}


	public static void setMainMenu(int mainMenu) {
		KeyBindings.mainMenuButton = mainMenu;
	}
	

	public static void setMapTranslationLeft(int mapTranslationLeft) {
		KeyBindings.mapTranslationLeft = mapTranslationLeft;
	}
	

	public static void setMapTranslationRight(int mapTranslationRight) {
		KeyBindings.mapTranslationRight = mapTranslationRight;
	}

	
	public static void setMapTranslationForward(int mapTranslationUp) {
		KeyBindings.mapTranslationForward = mapTranslationUp;
	}

	
	public static void setMapTranslationBackward(int mapTranslationDown) {
		KeyBindings.mapTranslationBackward = mapTranslationDown;
	}


	public static int getMainMenuButton() {
		return mainMenuButton;
	}


	public static int getMapTranslationLeft() {
		return mapTranslationLeft;
	}


	public static int getMapTranslationRight() {
		return mapTranslationRight;
	}


	public static int getMapTranslationForward() {
		return mapTranslationForward;
	}


	public static int getMapTranslationBackward() {
		return mapTranslationBackward;
	}
	
	
	public static int getMapTranslationUp() {
		return mapTranslationUp;
	}


	public static int getMapTranslationDown() {
		return mapTranslationDown;
	}

}
