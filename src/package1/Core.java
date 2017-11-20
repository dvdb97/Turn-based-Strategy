package package1;

import interaction.Window;
import rendering.RenderEngine;

import static org.lwjgl.glfw.GLFW.*;

import input.KeyInput;

public class Core implements Runnable {
	
	//****************************** Constants ******************************
	
	public static final int FULLSCREEN = 0;
	
	public static final int WINDOWED = 1;
	
	
	//****************************** Global Settings ******************************
	
	
	public static boolean debug;
	
	public static String name = "Strategy game ";
	
	public static String version = "v2.4";
	
	
	//****************************** Member variables ******************************
	
	private int mode;
	
	private boolean running = false;
	
	private String title;
		
	
	private Thread gameThread;
	
	private Window window;
	
	
	/*
	 * mode: windowed / fullscreen
	 * 
	 * state: TODO - Maybe for later uses if you want to start at a different point (late game / etc, cheats enabled)
	 * 
	 * debug: true for debug mode 
	 */
	public Core(String title, int mode, int state, boolean debug) {
		
		this.mode = mode;
		
		this.title = title;
		
		Core.debug = debug;
		
		gameThread = new Thread(this, "STRATEGY-GAME");
		gameThread.run();
		
	}

	
	public void run() {
		
		initGraphics();
		
		initializeGUI();
		
		loadAssets();
		
		runGameLoop();
		
	}
	
	
	private void initGraphics() {
		
		Graphics.init(Graphics.WINDOW_MODES.FULLSCREEN, 0, Graphics.LIGHT_MODES.LOW);
		
	}
	
	
	private void loadAssets() {
		//TODO
	}
	
	
	private void initializeGUI() {
		//TODO
	}
	
	
	private void runGameLoop() {
		
		running = true;
		
		while(running) {
			
			RenderEngine.clear();
			
			processInput();
			
			//Update stuff
			
			//Draw Stuff
			
			RenderEngine.swapBuffers();
			
		}
		
		Graphics.close();
		
	}
	
	
	private void processInput() {
		
		if (KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			running = false;
		}
		
	}
	
	
	/*public static void main(String[] args) {
		new Core("Strategy Game", WINDOWED, 0, false);
	}*/
	
}
