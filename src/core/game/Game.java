package core.game;

import core.saves.GameScore;
import graphics.shaders.ShaderManager;
import gui.GameGUIManager;
import gui_core.GUIManager;
import interaction.CameraOperator;
import interaction.TileSelecter;
import interaction.input.KeyInput;
import rendering.RenderEngine;
import world.WorldManager;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Game {
	
	private boolean running;
	
	
	//Start a completely new game
	public Game(int boardLength, int boardWidth) {
		
		ShaderManager.init();
		
		WorldManager.init(boardLength, boardWidth);
		
		CameraOperator.init();
		
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
			
			RenderEngine.clear();
			
			update();
			
			render();
			
			processInput();
			
			RenderEngine.swapBuffers();
			
		}
		
		close();
		
	}
	
	
	private void processInput() {
		
		if (KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			running = false;
		}

		CameraOperator.update();
		
		if(!GUIManager.processInput()) {
			TileSelecter.processInput();
		}

	}
	
	
	private void update() {
		
		WorldManager.update();
		GameGUIManager.update();
		GUIManager.update();
		
	}
	
	
	private void render() {
		WorldManager.render();
		GUIManager.render();
	}
	
	
	public void close() {
		
	}

}
