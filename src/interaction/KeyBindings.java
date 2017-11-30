package interaction;

import static org.lwjgl.glfw.GLFW.*;

public class KeyBindings {
	
	//Set the key bindings. E.g.:
	private static int mainMenuButton = GLFW_KEY_ESCAPE;
	
	private static int mapTranslationLeft = GLFW_KEY_A;
	
	private static int mapTranslationRight = GLFW_KEY_D;
	
	private static int mapTranslationUp = GLFW_KEY_W;
	
	private static int mapTranslationDown = GLFW_KEY_S;
	
	private static int mapZoomOut = GLFW_KEY_R;
	
	private static int mapZoomIn = GLFW_KEY_F;


	public static void setMapZoomOut(int mapZoomOut) {
		KeyBindings.mapZoomOut = mapZoomOut;
	}



	public static void setMapZoomIn(int mapZoomIn) {
		KeyBindings.mapZoomIn = mapZoomIn;
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

	
	public static void setMapTranslationUp(int mapTranslationUp) {
		KeyBindings.mapTranslationUp = mapTranslationUp;
	}

	
	public static void setMapTranslationDown(int mapTranslationDown) {
		KeyBindings.mapTranslationDown = mapTranslationDown;
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


	public static int getMapTranslationUp() {
		return mapTranslationUp;
	}


	public static int getMapTranslationDown() {
		return mapTranslationDown;
	}
	
	
	public static int getMapZoomOut() {
		return mapZoomOut;
	}


	public static int getMapZoomIn() {
		return mapZoomIn;
	}

}
