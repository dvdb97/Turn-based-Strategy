package interaction;

import interaction.input.KeyInput;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.MatrixManager;


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
	private static float zoom;
	
	//Was there input recently that requires us to update the viewMatrix?
	private static boolean changes;
	
	//TODO: The value is temporary
	private static float cameraSpeed = 0.1f;
	
	
	private static Vector3f cameraPosition;
	
	
	//Init the camera
	public static void init() {
		
		cameraMovementDisabled = false;
		changes = true;
		
		
		translation = new Vector3f(0f, 0f, 0f);
		
		rotation = new Vector3f(0f, 0f, 0f);
		
		zoom = 1f;
		
		
		viewMatrix = MatrixManager.generateViewMatrix(translation, rotation, 1.0f);
		
		
		//TODO: Has to be changed eventually
		cameraPosition = new Vector3f(0f, 0f, 1f);
		
	}
	
	
	//Init the camera with a starting position.
	public static void init(Vector3f startingPosition, Vector3f startingRotation, float startingZoom) {
		
		cameraMovementDisabled = false;
		changes = true;
		
		
		translation = startingPosition.copyOf();
		
		rotation = startingRotation.copyOf();
		
		zoom = startingZoom;
		
		
		viewMatrix = MatrixManager.generateViewMatrix(translation, rotation, 1.0f);
		
		
		//TODO: Has to be changed eventually
		cameraPosition = new Vector3f(0f, 0f, 1f);
		
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
		
		if (KeyInput.keyPressed(KeyBindings.getMapZoomIn())) {
			zoom += cameraSpeed * zoom;
			
			changes = true;
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapZoomOut())) {
			zoom -= cameraSpeed * zoom;
			
			changes = true;
		}
		
		
		if (KeyInput.keyPressed(GLFW_KEY_G)) {
			rotation.increaseA(-0.1f * cameraSpeed);
			
			changes = true;
		}
		
		
		if (KeyInput.keyPressed(GLFW_KEY_J)) {
			rotation.increaseA(0.1f * cameraSpeed);
			
			changes = true;
		}
		
		
		if (zoom < 0.02f) {
			zoom = 0.02f;	
		}
		
		System.out.println(zoom);
		
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
	
	
	public static Vector3f getCameraPosition() {
		
		return cameraPosition;
		
	}

}
