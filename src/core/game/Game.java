package core.game;

import core.saves.GameScore;
import graphics.shaders.ShaderManager;
import gui.font.FontCollection;
import interaction.CameraOperator;
import interaction.input.KeyInput;
import interaction.tileSelection.TileSelecter;
import map.MapManager;
import rendering.RenderEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;


public class Game {
	
	
	private boolean running;
	
	
	//Start a completely new game
	public Game(int numberOfAgents, int boardWidth, int boardHeight) {
		
		//Init the shaders
		ShaderManager.init();
		
		//Init the maps
		MapManager.init(boardWidth, boardHeight);
		
		//Init the camera
		CameraOperator.init();
		
		TileSelecter.init(MapManager.getTileCenterVertices());
		
		//Load all fonts. TODO: Init it somewhere else (maybe as a bundle together with other gui stuff)
		FontCollection.init();
		
		//Init Agents etc
		
		
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
		
		CameraOperator.update();
		
		TileSelecter.processInput();
		
		if (KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			running = false;
		}
		
	}
	
	
	private void update() {
		
		
		
	}
	
	
	private void render() {
		MapManager.render();
	}
	
	
	public void close() {
		
		
		
	}

}
