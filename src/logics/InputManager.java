package logics;

import package1.Input;
import graphics.Camera;
import package1.GameState;

import utils.Const;
import utils.GeometryUtils;
import utils.Clocker;

public class InputManager {
	
	int tilesLength;
	int boardWidth;
	int boardHeight;
	//TODO:maybe temporary
	float relationXY;
	
	float centerOfLastTileX, centerOfLastTileY;
	
	//TODO: in future maybe not used
	int TSPKey;					//TSP = tile selecting process
	
	//clocker (to clock the input to a certain rate)
	boolean changed;
	
	Clocker mouseC;
	Clocker boardControlC;
	Clocker hotKeyC;
	Clocker arrowC;
	
	//TODO: temporary 04.07.17
	boolean updateMT;
	
	public void updateMarkedTile() {
		
		if (updateMT) {
			
			int[] temp = GeometryUtils.getAccordingTile(Input.mousePosX, Input.mousePosY);
			
			//make sure only existing tiles get marked
			if (temp[0] < 0 || temp[0] >= boardWidth) {
				temp[0] = GameState.markedTile[0];
			}
			if (temp[1] < 0 || temp[1] >= boardHeight) {
				temp[1] = GameState.markedTile[1];
			}
			
			GameState.markedTile = temp;
			
		}
		
	}
	
	public void mouseInput() {
		
		if (mouseC.elapsedTimeLegit()) {
			
			changed = false;
			
			if (Input.mouseLeftClick) {
				
				//if any tile marked, select
				if (GameState.anyTileMarked()){
					endTileSelectingProcess();
				}
				
				//if any tile is selected, deselect
				else if (GameState.anyTileSelected()) {
					GameState.resetSelectedTile();
				}
				
				//if neither a tile is marked nor selected, mark one arbitrary tile
				//									(its getting updated by updateMarkedTile() quickly)
				else {
					startTileSelectingProcess(0);
				}
				
				changed = true;
				
			}
			
			if (Input.mouseRightClick) {
				
				//TODO
				changed = true;
				
			}
			
			//reset clocker
			if (changed) {
				mouseC.setCurrentTime();
			}
			
		}
	}
	
	public void boardControl() {
		
		if (boardControlC.elapsedTimeLegit()) {
			
			changed = false;
			
			if (Input.aPressed) {
				
				Camera.move(-0.3f, 0, 0);
				changed = true;
			}
			if (Input.dPressed) {
				
				Camera.move(+0.3f, 0, 0);
				changed = true;
			}
			if (Input.wPressed) {
				
				Camera.move(0, +0.3f, 0);
				changed = true;
			}
			if (Input.sPressed) {
				
				Camera.move(0, -0.3f, 0);
				changed = true;
			}
			if (Input.rPressed) {
				
				Camera.move(0, 0, +0.3f);
				changed = true;
			}
			if (Input.fPressed) {
				
				Camera.move(0, 0, -0.3f);
				changed = true;
			}
			
			
			if (Input.tPressed) {
				Camera.incrPitch(+0.03f);
				changed = true;
			}
			if (Input.gPressed) {
				Camera.incrPitch(-0.03f);
				changed = true;
			}
			
			if (Input.cPressed) {
				Camera.incrYaw(+0.01f);
				changed = true;
			}
			if (Input.vPressed) {
				Camera.incrYaw(-0.01f);
				changed = true;
			}
			if (Input.bPressed) {
		//		Camera.incrRoll(+0.01f);
				changed = true;
			}
			if (Input.mPressed) {
		//		Camera.incrRoll(-0.01f);
				changed = true;
			}
			
			//reset clocker
			if (changed) {
				boardControlC.setCurrentTime();
			}
			
		}	
	}
	
	public void hotKeys() {
		
	//	if (hotKeyC.elapsedTimeLegit()) {
	/*	if (false) {	

			changed = false;
			
			//--------------------     select a tile     -----------------
			if (Input.mPressed) {
				
				
				changed = true;
				
			}
			
			//--------------------------   city    ----------------------
			//if a certain tile is selected and there is no special interest(TSPKey==TSP_NONE), go on
			if (Input.cPressed && TSPKey == Const.TSP_NONE) {
				
				//request: {TSPKey, position}
				int[] request = players[0].creatingCityRequested(GameState.selectedTile[0] + GameState.selectedTile[1]*boardWidth);
				
				//if c.C.R.() requests something (to select a paying city)
				//start a new TSP, with the according TSPKey, ...else delete selection
				if (request[0] != Const.TSP_NONE) {
					startTileSelectingProcess(request[0], request[1]);
				} else {
					GameState.resetSelectedTile();
				}
				
				changed = true;
				
			}
			
			//--------------------------    temp     ----------------------
			if (Input.vPressed) {
				
				System.out.println(GeometryUtils.getLocalCoordsOfPixelX(Input.mousePosX) +"  "+GeometryUtils.getLocalCoordsOfPixelY(Input.mousePosY));
				
				changed = true;
				
			}
			
			
			if (Input.vPressed) {
				
				if (updateMT) {
			//		GameState.markedTile = 5;
					updateMT = false;
				} else {
					updateMT = true;
				}
			}
			
			//TODO: hotkey (v for example) to open TileMenu /CityMenu
			
			//reset clocker
			if (changed) {
				hotKeyC.setCurrentTime();
			}
		}*/
	}
	
	public void arrowKeys() {
			
		if (arrowC.elapsedTimeLegit()) {
			
			changed = false;
			
			if (Input.leftArrow) {
				//
				changed = true;
			}
			if (Input.rightArrow) {
				//
				changed = true;
			}
			if (Input.upArrow) {
				
				GameState.seaLevel += 0.02f;
				
				changed = true;
			}
			if (Input.downArrow) {
				
				GameState.seaLevel -= 0.02f;
				
				changed = true;
			}
		
			//reset clocker
			if (changed) {
				arrowC.setCurrentTime();
			}
			
		}
		
	}
	
	//------------------------------ constructor --------------------------
	public InputManager(int boardWidth, int boardHeight) {
		
		//the real value is set soon by the graphics
		relationXY = 16.0f/9.0f;
		
		tilesLength = boardWidth*boardHeight;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		
		centerOfLastTileX = (float) (Const.SQRT3*Const.radiusOfHexagons*(0.5+boardWidth+(boardHeight%2)/2));
		centerOfLastTileY = (float) (Const.radiusOfHexagons*(1.5*boardHeight + 1));
		
		TSPKey = Const.TSP_NONE;
		
		//create Clocker
		mouseC			= new Clocker(0.2);
		boardControlC	= new Clocker(0.017);
		hotKeyC			= new Clocker(0.1);
		arrowC			= new Clocker(0.1);
		
		updateMT = false;
		
	}
	
	//------------------------------   methods   --------------------------
	private void startTileSelectingProcess( int key, int initialTile) {
		
		//mark the given tile
		//...except the already selected tile shall remain in this state
		GameState.markedTile[0] = initialTile % boardWidth;
		GameState.markedTile[1] = initialTile / boardWidth;
		
		//if TSP_payingCity, the now selected tile has to be selected, if else not
		if (key == Const.TSP_payingCity) {
			
		} else {
			package1.GameState.resetSelectedTile();
		}
		
		//set TSPKey
		TSPKey = key;
		
		//make sure the position of the currently marked tile gets updated
		updateMT = true;
		
	}
	private void startTileSelectingProcess(int initialTile) {
		
		GameState.markedTile[0] = initialTile % boardWidth;
		GameState.markedTile[1] = initialTile / boardWidth;
		package1.GameState.resetSelectedTile();
		
		TSPKey = Const.TSP_NONE;
		
		//make sure the position of the currently marked tile gets updated
		updateMT = true;
		
	}
	
	private void endTileSelectingProcess() {
		
		//execute TSPKey
		if (TSPKey == Const.TSP_payingCity) {
			GameState.resetSelectedTile();
			
		} else {	//e.g. TSPKey == Const.TSP_NONE
			GameState.selectedTile[0] = GameState.markedTile[0];
			GameState.selectedTile[1] = GameState.markedTile[1];
		}
		
		//reset markedTile
		GameState.resetMarkedTile();
		
		//reset TSPKey
		TSPKey = Const.TSP_NONE;
		
		updateMT = false;
		
	}
	
}
