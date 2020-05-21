package core.game;

import core.saves.GameScore;
import gui.GameGUIManager;
import gui_core.GUIManager;
import interaction.TileSelecter;
import interaction.input.KeyInputHandler;
import rendering.Renderer;
import rendering.SceneManager;
import world.WorldManager;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Game {
	
	private boolean running;
	
	
	//Start a completely new game
	public Game(int boardLength, int boardWidth) {
		SceneManager.init();
		WorldManager.init(boardLength, boardWidth);
		TileSelecter.init();
		GameGUIManager.init();
		
		run();
	}
	
	
	//Start the game with an already existing game
	public Game(GameScore score) {	
		run();
	}
	
	
	private void run() {
		running = true;
		
		while (running) {
			
			Renderer.clear();
			
			update();
			
			render();
			
			processInput();
			
			Renderer.swapBuffers();
			
		}
		
		close();
	}
	
	
	private void processInput() {
		KeyInputHandler.pollEvents();
		
		if (KeyInputHandler.keyPressed(GLFW_KEY_ESCAPE)) {
			running = false;
		}

		if(!GUIManager.processInput()) {
			TileSelecter.processInput();
		}
	}
	
	
	private void update() {
		WorldManager.update();
		GameGUIManager.update();
	}
	
	
	private void render() {
		WorldManager.render();
		GUIManager.render();
	}
	
	
	public void close() {
		
	}

}
