package core.game;

import core.saves.GameScore;
import gui.font.FontCollection;
import interaction.PlayerCamera;
import gui_core.GUIManager;
import interaction.TileSelecter;
import interaction.input.KeyInput;
import rendering.RenderEngine;
import world.WorldManager;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Game {
	
	
	private boolean running;
	
	
	//Start a completely new game
	public Game(int numberOfAgents, int boardLength, int boardWidth) {
		//Init the camera
		PlayerCamera.init();
		
		//Init world
		WorldManager.init(boardLength, boardWidth);
		
		//Init tile selecter
		TileSelecter.init();
		
		//Load all fonts. TODO: Init it somewhere else (maybe as a bundle together with other gui stuff)
		FontCollection.init();
		
		run();
	}
	
	
	//Start the game with an already existing game
	public Game(GameScore score) {	
		run();
	}
	
	
	private void run() {
		running = true;
		
		while (running) {
			
			RenderEngine.clear();
			
			processInput();
			
			update();
			
			render();
			
			RenderEngine.swapBuffers();
			
		}
		
		close();
	}
	
	
	private void processInput() {
		PlayerCamera.update();
		
		TileSelecter.processInput();
		
		if (KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			running = false;
		}
	}
	
	
	private void update() {
		WorldManager.update();
	}
	
	
	private void render() {
		WorldManager.render();
		//Draw the gui
		GUIManager.update();
	}
	
	
	public void close() {
		
	}

}
