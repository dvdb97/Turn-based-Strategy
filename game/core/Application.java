package core;

import core.settings.StartParams;
import interaction.Window;
import interaction.input.CursorPosInput;
import interaction.input.KeyInput;
import interaction.input.MouseInputManager;
import rendering.RenderEngine;

public class Application {
	
	private Window window;
	
	
	public Application(StartParams params) {
		
		initWindow(params);
		
		initRenderEngine(params);
		
		start();
		
		close();
		
	}
	
	
	private void initWindow(StartParams params) {
		
		window = new Window();
		//TODO: Implement the window mode
		window.createWindowedWindow(params.getTitle() + "-" + params.getVersion());
		
		
		//Set the input
		window.setKeyInputCallback(new KeyInput());
		window.setMouseInputCallback(new MouseInputManager());
		window.setMousePosInput(new CursorPosInput());
		
	}
	
	
	private void initRenderEngine(StartParams params) {
		
		RenderEngine.init(window);
		RenderEngine.setClearColor(0.0f, 0.0f, 1.0f, 0.0f);
		RenderEngine.enableDepthTest();
		RenderEngine.setSwapInterval(1);
		
	}
	
	
	private void start() {
		
		//TODO: Add a main menu
		
		Game game = new Game(1, 100, 100);
		
	}
	
	
	private void close() {
		
		RenderEngine.close();
		
		window.close();
		
	}
	
	
	public static void main(String[] args) {
		
		new Application(new StartParams("Settings/StartParameter"));
		
	}
	

}
