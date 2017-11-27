package core;

import Interaction.PlayerCamera;
import graphics.ProjectionMatrix;
import graphics.shaders.ShaderManager;
import map.MapManager;
import rendering.RenderEngine;
import save.GameScore;

public class Game {
	
	
	private boolean running;
	
	
	//Start a completely new game
	public Game(int numberOfAgents, int boardWidth, int boardHeight) {
		
		//Init the shaders
		ShaderManager.init();
		
		//Init the maps
		MapManager.init(boardWidth, boardHeight);
		
		//Init the camera
		PlayerCamera.init();
		
		//Init the Projection Matrix:
		ProjectionMatrix.init();
		
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
		
		PlayerCamera.update();
		
	}
	
	
	private void update() {
		
		
		
	}
	
	
	private void render() {
		
		
		
	}
	
	
	public void close() {
		
		
		
	}

}
