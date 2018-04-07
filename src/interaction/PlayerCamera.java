package interaction;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_G;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;

import assets.cameras.Camera;
import classes.CameraOperator;
import interaction.input.KeyInput;
import math.matrices.Matrix44f;
import math.matrices.advanced.Determinant;
import math.vectors.Vector3f;

public class PlayerCamera {
	
	private static Camera camera;
	
	//The Map shouldn't move in some cases. For example when you are in a menu
	private static boolean cameraMovementDisabled;	
	
	//TODO: The value is temporary
	private static float cameraMovementSpeed = 0.1f;
	private static float cameraRotationSpeed = 0.1f;

	
	//Init the camera
	public static void init() {
		cameraMovementDisabled = false;	
		camera = new CameraOperator();
	}
	
	
	//Processes the player input and translate it to camera movement
	public static void update() {
		
		if (cameraMovementDisabled) {
			return;
		}
		
		
		processCameraMovement();
		
		processZoom();
		
	}
	
	
	//Checks the input and checks whether the player wants to move the map
	private static void processCameraMovement() {
		
		//Temporary: Move the map with W-A-S-D
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationForward())) {
			
			camera.move(0f, cameraMovementSpeed, 0f);
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationBackward())) {
			
			camera.move(0f, -cameraMovementSpeed, 0f);
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationRight())) {
			
			camera.move(cameraMovementSpeed, 0f, 0f);
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationLeft())) {
			
			camera.move(-cameraMovementSpeed, 0f, 0f);
			
		}
		
	}
	
	
	private static void processZoom() {
		
		//Get Mouse wheel input
		
		//Move the Camera downwards and tilt it a bit. TODO: A function that processes the matrix for that
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationDown())) {
			
			camera.move(0f, 0f, -cameraMovementSpeed);
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationUp())) {
			
			camera.move(0f, 0f, cameraMovementSpeed);
			
		}
		
		
		if (KeyInput.keyPressed(GLFW_KEY_T)) {
			
			camera.pitch(0.1f * cameraRotationSpeed);
			
		}
		
		
		if (KeyInput.keyPressed(GLFW_KEY_G)) {
			
			camera.pitch(-0.1f * cameraRotationSpeed);
			
		}
		
	}
	
	
	public static void lookAt(Vector3f point) {
		camera.lookAt(point);
	}
	
	
	public static void lookAt(Vector3f position, Vector3f point) {
		camera.lookAt(position, point);
	}
	
	
	public static void disableCameraMovement(boolean b) {
		
		cameraMovementDisabled = b;
		
	}
	
	
	public static boolean isCameraMovementDisabled() {
		
		return cameraMovementDisabled;
		
	}
	
	
	public static Matrix44f getViewMatrix() {
		
		return camera.getViewMatrix();
		
	}
	
	
	public static Matrix44f getInvertedMatrix() {
		
		return camera.getInvertedViewMatrix();
		
	}
	
	
	public static Vector3f getCameraPosition() {
		return camera.getPosition();
	}
	
	
	public static Camera getCamera() {
		return camera;
	}

}
