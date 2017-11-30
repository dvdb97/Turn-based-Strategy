package core.game;

import core.saves.GameScore;
import graphics.shaders.ShaderManager;
import interaction.PlayerCamera;
import interaction.input.KeyInput;
import map.MapManager;
import math.ProjectionMatrix;
import math.vectors.Vector3f;
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
