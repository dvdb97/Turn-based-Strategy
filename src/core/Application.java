package core;

import core.game.Game;
import core.saves.StartParams;
import graphics.matrices.Matrices;
import interaction.Window;
import interaction.input.CursorPosInput;
import interaction.input.KeyInput;
import interaction.input.MouseInputManager;
import math.vectors.Vector4f;
import rendering.RenderEngine;

public class Application {
	
	private static Window window;
	
	
	public static void init(StartParams params) {
		
		initWindow(params);
		
		initRenderEngine(params);
		
		start();
		
		close();
		
	}
	
	
	private static void initWindow(StartParams params) {
		
		window = new Window();
		//TODO: Implement the window mode
		window.createFullscreenWindow(params.getTitle() + "-" + params.getVersion());
		
		//Set the input
		window.setKeyInputCallback(new KeyInput());
		window.setMouseInputCallback(new MouseInputManager());
		window.setMousePosInput(new CursorPosInput());
		
	}
	
	
	private static void initRenderEngine(StartParams params) {
		
		RenderEngine.init(window);
		RenderEngine.setClearColor(new Vector4f(0.2f, 0.4f, 1f, 1f));
		RenderEngine.enableDepthTest();
		RenderEngine.setSwapInterval(1);
		
		Matrices.initProjectionMatrix(window);
		
	}
	
	
	private static void start() {
		
		//TODO: Add a main menu
		
		Game game = new Game(1, 300, 300);
		
		close();
		
	}
	
	
	private static void close() {
		
		RenderEngine.close();
		
		//TODO: This method makes the program crash
		//window.close();
		
	}
	
	
	public static int getWindowHeight() {
		
		return window.getHeight();
		
	}
	
	
	public static int getWindowWidth() {
		
		return window.getWidth();
		
	}
	
	
	public static float toOpenglXCoords(double xPos) {
		
		return window.toNormalizedXCoordinates(xPos);
		
	}
	
	
	public static float toOpenglYCoords(double yPos) {
		
		return window.toNormalizedYCoordinates(yPos);
		
	}

}