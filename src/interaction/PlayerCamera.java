package interaction;

import interaction.input.KeyInput;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.MatrixManager;


import static org.lwjgl.glfw.GLFW.*;

import java.awt.RenderingHints.Key;

import graphics.Camera;
import graphics.matrices.ViewMatrix;


public class PlayerCamera {
	
	//The Map shouldn't move in some cases. For example when you are in a menu
	private static boolean cameraMovementDisabled;
	
	
	private static ViewMatrix viewMatrix;
	
	
	//TODO: The value is temporary
	private static float cameraMovementSpeed = 0.1f;
	private static float cameraRotationSpeed = 0.1f;
	
	
	//Init the camera
	public static void init() {
		
		cameraMovementDisabled = false;		
		
		viewMatrix = new ViewMatrix();
		
	}
	
	
	//Init the camera with a starting position.
	public static void init(Vector3f startingPosition, Vector3f startingRotation, float startingZoom) {
		
		cameraMovementDisabled = false;
		
		
		viewMatrix = new ViewMatrix();
		
	}
	
	
	//Processes the player input and translate it to camera movement
	public static void update() {
		
		if (cameraMovementDisabled) {
			return;
		}
		
		
		processCameraMovement();
		
		processZoom();
		
		
		viewMatrix.refresh();
		
	}
	
	
	//Checks the input and checks wether the player wants to move the map
	private static void processCameraMovement() {
		
		//Temporary: Move the map with W-A-S-D
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationForward())) {
			
			Camera.move(0f, cameraMovementSpeed, 0f);
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationBackward())) {
			
			Camera.move(0f, -cameraMovementSpeed, 0f);
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationRight())) {
			
			Camera.move(cameraMovementSpeed, 0f, 0f);
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationLeft())) {
			
			Camera.move(-cameraMovementSpeed, 0f, 0f);
			
		}
		
	}
	
	
	private static void processZoom() {
		
		//Get Mouse wheel input
		
		//Move the Camera downwards and tilt it a bit. TODO: A function that processes the matrix for that
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationDown())) {
			
			Camera.move(0f, 0f, -cameraMovementSpeed);
			
		}
		
		
		if (KeyInput.keyPressed(KeyBindings.getMapTranslationUp())) {
			
			Camera.move(0f, 0f, cameraMovementSpeed);
			
		}
		
		
		if (KeyInput.keyPressed(GLFW_KEY_G)) {
			
			Camera.incrPitch(0.1f * cameraRotationSpeed);
			
		}
		
		
		if (KeyInput.keyPressed(GLFW_KEY_J)) {
			
			Camera.incrPitch(-0.1f * cameraRotationSpeed);
			
		}
		
	}
	
	
	public static void disableCameraMovement(boolean b) {
		
		cameraMovementDisabled = b;
		
	}
	
	
	public static boolean isCameraMovementDisabled() {
		
		return cameraMovementDisabled;
		
	}
	
	
	public static ViewMatrix getViewMatrix() {
		
		return viewMatrix;
		
	}
	
	
	public static Matrix44f getInvertedMatrix() {
		
		return viewMatrix.getMultiplicativeInverse();
		
	}
	
}
