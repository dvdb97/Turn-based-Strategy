package logics;

import world.*;

import java.io.FileReader;
import java.io.IOException;

import package1.GameState;
import package1.Input;
import utils.Const;

//fields of this class are static

public class Logics {
	
	static InputManager inputManager;
	static SeedGenerator generator;
	
	//TODO: later not public
	public static GameBoard board;
	
	//TODO: later this will be set by the user or by a save file
	static int WIDTH = 30, HEIGHT = 30;
	public static int NUM_PLAYERS = 2;
	
	public static void initLogics() {
		
		//TODO: later the user should decide
		executeMenuSelection(Const.MM_NEWGAME);
		
		//create input manager
		inputManager = new InputManager(board.WIDTH, board.HEIGHT);
		
	}
	
	//TODO: temporary
	private static void startNewGame() {
		
		//create random board seed
		generator = new SeedGenerator();
		byte[] seed = generator.generatedSeed(WIDTH,  HEIGHT);
		
		//create game board
		board = new GameBoard(WIDTH, HEIGHT, seed);
		GameState.setTiles(board.tiles, WIDTH, HEIGHT);
		
		
		System.out.println("created a game");
	}
	
	//TODO: private
	public static void saveGameScore() {
		
		utils.FileUtils.saveGameScore(WIDTH, HEIGHT, board.seed);
		
	}
	
	private static void loadGameScore() {
		
		FileReader fr = null;
		long temp;
		byte[] seed;
		
		try {
			
			fr = new FileReader( "save0" );
			//TODO: check if the file has correct structure
			temp = fr.skip(14);
			//getting size of the board
			WIDTH = fr.read();
			HEIGHT = fr.read();
			temp = fr.skip(8);
			//getting length of tile array
			temp = fr.read()*256 + fr.read();
			seed = new byte[(int)temp];
			//reading seed
			for (int i=0; i<seed.length; i++) {
				int c = fr.read();
				seed[i] = (byte) c;
			}
			
			board = new GameBoard(WIDTH, HEIGHT, seed);
			GameState.setTiles(board.tiles, WIDTH, HEIGHT);
			
		} catch (IOException e) {
			System.err.println("An error has occured while rading the file");
		} finally {
			if (fr != null) {
				try { fr.close(); } catch (IOException e) { System.err.println(e); }
			}
		}
		
	}
	
	//called by Game.gameloop()
	public static void update() {
		
		//TODO: in future logics don't read GameState
		if (GameState.boardVisible) {
			
			inputManager.updateMarkedTile();
			inputManager.mouseInput();
			inputManager.boardControl();
			inputManager.hotKeys();
			inputManager.arrowKeys();
			
		}
		
		if (Input.menuSelect != Const.NO_MENU_SELECT) {
			executeMenuSelection(Input.menuSelect);
		}
	}
	
	public static void executeMenuSelection(int selectedOption) {
		
		switch (selectedOption) {
		case Const.MM_NEWGAME: 	startNewGame();
								break;
		case Const.MM_LOADGAME: loadGameScore();
								break;
		case Const.MM_SETTINGS:	;
								break;
		case Const.MM_CREDITS:	;
								break;
		case Const.MM_EXIT:		;
								break;
		}
		
	}
	
	public static boolean selectionLegit(int selectedOption) {
		
		if (selectedOption >> 4 == Const.MAIN_MENU_ID) {
			return true;
		} else {		
			return false;
		}
	}
	
	//set relationXY
	public static void setRXY(float relationXY) {
		inputManager.relationXY = relationXY;
	}
	
}
