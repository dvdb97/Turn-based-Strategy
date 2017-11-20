package core;

import map.MapManager;
import rendering.RenderEngine;
import save.GameScore;

public class Game {
	
	
	private boolean running;
	
	
	//Start a completely new game
	public Game(int numberOfAgents, int boardWidth, int boardHeight) {
		
		MapManager.init(boardWidth, boardHeight);
		
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
			
			//Process input
			
			//update
			
			//render stuff
			
			RenderEngine.swapBuffers();
			
		}
		
		close();
		
	}
	
	
	private void render() {
		
		
		
	}
	
	
	public void close() {
		
	}

}
