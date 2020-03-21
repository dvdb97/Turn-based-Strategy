package assets.cameras;

import math.vectors.Vector3f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import assets.meshes.Transformable;
import interaction.Window;
import interaction.input.CursorPosInput;
import interaction.input.KeyInput;
import interaction.input.KeyInputHandler;
import interaction.input.Keys;

import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;

public class FirstPersonController {
	
	private Camera camera;
	
	private float lastCursorX, lastCursorY;
	private float speed = 1;
	private float sensitivity = 1f;
	
	private Vector3f rotation;
	
	public FirstPersonController(Vector3f position) {
		camera = new Camera(position);
		rotation = new Vector3f(0f, 0f, 0f);
		lastCursorX = CursorPosInput.getXPosAsOpenglCoord();
		lastCursorY = CursorPosInput.getYPosAsOpenglCoord();
		glfwSetInputMode(Window.main.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}
	
	
	private void increasePitch(float pitch) {
		System.out.println(rotation);
		
		if (rotation.getA() + pitch > 89f * Transformable._1_DEGREE) {
			rotation.setA(89f * Transformable._1_DEGREE);
			return;
		}
		
		if (rotation.getA() + pitch < -89f * Transformable._1_DEGREE) {
			rotation.setA(-89f * Transformable._1_DEGREE);
			return;
		}
		
		rotation.increaseA(pitch);
	}
	
	
	private void increaseYaw(float yaw) {
		rotation.increaseB(yaw);
	}
	
	
	public void rotateCamera(float pitch, float yaw) {
		increasePitch(pitch);
		increaseYaw(yaw);
		
		float x = (float)cos(rotation.getA()) * (float)cos(rotation.getB());
		float y = (float)sin(rotation.getA());
		float z = (float)cos(rotation.getA()) * (float)sin(rotation.getB());
		
		camera.lookInDir(new Vector3f(x, y, z));
	}
	
	
	public void moveForward() {
		camera.forward(speed);
	}
	
	
	public void moveBackward() {
		camera.backward(speed);
	}
	
	
	public void moveLeft() {
		camera.leftwards(speed);
	}
	
	
	public void moveRight() {
		camera.rightwards(speed);
	}
	
	
	public void update() {
		float cursorX = CursorPosInput.getXPosAsOpenglCoord();
		float cursorY = CursorPosInput.getYPosAsOpenglCoord();
		
		float xoffset = (cursorX - lastCursorX) * sensitivity;
		float yoffset = (cursorY - lastCursorY) * sensitivity;
		
		lastCursorX = cursorX;
		lastCursorY = cursorY;
		
		this.rotateCamera(yoffset, xoffset);
		
		if (KeyInputHandler.keyPressed(Keys.KEY_W)) {
			moveForward();
		}
		
		if (KeyInputHandler.keyPressed(Keys.KEY_S)) {
			moveBackward();
		}
		
		if (KeyInputHandler.keyPressed(Keys.KEY_A)) {
			moveLeft();
		}
		
		if (KeyInputHandler.keyPressed(Keys.KEY_D)) {
			moveRight();
		}
	}
	
	
	public Camera getCamera() {
		return camera;
	}
	
	
	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}
	
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
