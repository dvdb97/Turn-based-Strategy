package core.game;

import core.saves.GameScore;
import graphics.shaders.ShaderManager;
import gui.font.FontCollection;
import gui_core.GUIManager;
import interaction.CameraOperator;
import interaction.TileSelecter;
import interaction.input.KeyInput;
import math.matrices.Matrix44f;
import rendering.RenderEngine;
import visualize.FontTest;
import world.WorldManager;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;


public class Game {
	
	
	private boolean running;
	
	
	//Start a completely new game
	public Game(int numberOfAgents, int boardLength, int boardWidth) {
		
		//Init the shaders
		ShaderManager.init();
		
		//Init world
		WorldManager.init(boardLength, boardWidth);
		
		//Init the camera
		CameraOperator.init();
		
		//Init tile selecter
		TileSelecter.init();
		
		//Load all fonts. TODO: Init it somewhere else (maybe as a bundle together with other gui stuff)
		FontCollection.init();
		FontTest.init("The quick brown fox \njumps over the lazy dog!");
		
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
