package core.game;

import core.saves.GameScore;
<<<<<<< HEAD
import gui.font.FontCollection;
import interaction.PlayerCamera;
=======
import graphics.shaders.ShaderManager;
import gui.GameGUIManager;
>>>>>>> gui_changes
import gui_core.GUIManager;
import interaction.TileSelecter;
import interaction.input.KeyInput;
import rendering.RenderEngine;
import world.WorldManager;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Game {
	
	private boolean running;
	
	
	//Start a completely new game
<<<<<<< HEAD
	public Game(int numberOfAgents, int boardLength, int boardWidth) {
		//Init the camera
		PlayerCamera.init();
=======
	public Game(int boardLength, int boardWidth) {
		
		ShaderManager.init();
>>>>>>> gui_changes
		
		WorldManager.init(boardLength, boardWidth);
		
<<<<<<< HEAD
		//Init tile selecter
		TileSelecter.init();
		
		//Load all fonts. TODO: Init it somewhere else (maybe as a bundle together with other gui stuff)
		FontCollection.init();
=======
		CameraOperator.init();
		
		TileSelecter.init();
		
		GameGUIManager.init();
>>>>>>> gui_changes
		
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
<<<<<<< HEAD
		PlayerCamera.update();
		
		TileSelecter.processInput();
=======
>>>>>>> gui_changes
		
		if (KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			running = false;
		}
<<<<<<< HEAD
=======

		CameraOperator.update();
		
		if(!GUIManager.processInput()) {
			TileSelecter.processInput();
		}

>>>>>>> gui_changes
	}
	
	
	private void update() {
		WorldManager.update();
<<<<<<< HEAD
=======
		GameGUIManager.update();
		GUIManager.update();
		
>>>>>>> gui_changes
	}
	
	
	private void render() {
		WorldManager.render();
<<<<<<< HEAD
		//Draw the gui
		GUIManager.update();
=======
		GUIManager.render();
>>>>>>> gui_changes
	}
	
	
	public void close() {
		
	}

}
