package package1;
 
import graphics.Graphics;
import rendering.RenderEngine;

import static org.lwjgl.glfw.GLFW.*;
 
public class Game implements Runnable {
	
	//determines whether the game is still running or not.
	//TODO: Put it into the Gamestate class 
	boolean running;
	
	
	//The thread our game runs on
	private final Thread gameLoopThread;
	
	private Graphics graphics;
	
	
	//Starts the main thread of our game
	public Game() {
		gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
		gameLoopThread.start();
	}
	
	
	//initializes all the 
	public void init() {
		
		logics.Logics.initLogics();
		
		graphics = new Graphics();
		
		//reset UI
		Input.resetAll();
	}	
	
	
	public void run() {
		try {
			init();
			gameloop();
		} catch (Exception except) {
			except.printStackTrace();
		}
		
		close();
	}
	
	
	//Our game loop
	//TODO: Make sure it is actually 60 fps!
	public void gameloop() {
		running = true;
		
		while (running) {  
			if (graphics.exitRequested()) {
				logics.Logics.saveGameScore();
				running = false;
			}
			
			graphics.handleUserInput();
			
			logics.Logics.update();
			
			graphics.displayGame();;
			
			//RenderEngine.displayFPS();
		}
	}
	
	
	public void close() {
		graphics.close();
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	
	public static void main(String[] args) {
		new Game();
	}
}