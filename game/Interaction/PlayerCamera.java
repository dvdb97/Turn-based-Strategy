package Interaction;

import interaction.input.KeyInput;
import lwlal.Matrix44f;
import lwlal.Vector3f;
import maths.MatrixManager;


import static org.lwjgl.glfw.GLFW.*;

import java.awt.RenderingHints.Key;


public class PlayerCamera {
	
	//The Map shouldn't move in some cases. For example when you are in a menu
	private static boolean cameraMovementDisabled;
	
	
	private static Matrix44f viewMatrix;
	
	//The translation of the camera
	private static Vector3f translation;
	
	//The rotation of the camera.
	private static Vector3f rotation;
	
	//The input for the function that slowly moves the camera towards the map and tilts it.
	private static int zoom;
	
	//Was there input recently that requires us to update the viewMatrix?
	private static boolean changes;
	
	//TODO: The value is temporary
	private static float cameraSpeed = 0.1f;
	
	
	//Init the camera
	public static void init() {
		
		cameraMovementDisabled = false;
		changes = true;
		
		
		translation = new Vector3f(0f, 0f, 0f);
		
		rotation = new Vector3f(0f, 0f, 0f);
		
		zoom = 0;
		
		
		viewMatrix = MatrixManager.generateViewMatrix(translation, rotation, 1.0f);
		
	}
	
	
	//Init the camera with a starting position.
	public static void init(Vector3f startingPosition) {
		
		cameraMovementDisabled = false;
		changes = true;
		
		
		translation = startingPosition.copyOf();
		
		rotation = new Vector3f(0f, 0f, 0f);
		
		zoom = 0;
		
		
		viewMatrix = MatrixManager.generateViewMatrix(translation, rotation, 1.0f);
		
	}
	
	
	//Processes the player input and translate it to camera movement
	public static void update() {
		
		if (cameraMovementDisabled) {
			return;
		}
		
		
		processCameraMovement();
		
		processZoom();
		
		
		if (changes) {
			viewMatrix = MatrixManager.generateViewMatrix(translation, rotation, zoom);
			
			changes = false;
		}
		
	}
	
	
	//Checks the input and checks wether the player wants to move the map
	private static void processCameraMovement() {
		
		//Temporary: Move the map with W-A-S-D
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationUp())) {
			
			translation.increaseB(cameraSpeed);
			
			changes = true;
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationDown())) {
			
			translation.increaseB(-cameraSpeed);
			
			changes = true;
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationRight())) {
			
			translation.increaseA(cameraSpeed);
			
			changes = true;
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationLeft())) {
			
			translation.increaseA(-cameraSpeed);
			
			changes = true;
			
		}
		
	}
	
	
	private static void processZoom() {
		
		//Get Mouse wheel input
		
		//Move the Camera downwards and tilt it a bit. TODO: A function that processes the matrix for that
		
	}
	
	
	public static void disableCameraMovement(boolean b) {
		
		cameraMovementDisabled = b;
		
	}
	
	
	public static boolean isCameraMovementDisabled() {
		
		return cameraMovementDisabled;
		
	}
	
	
	public static Matrix44f getViewMatrix() {
		
		return viewMatrix;
		
	}
	
	
	public static float getCameraX() {
		
		return translation.getA();
		
	}
	
	
	public static float getCameraY() {
		
		return translation.getB();
		
	}
	
	
	public static float getCameraZ() {
		
		return translation.getC();
		
	}

}
