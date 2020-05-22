package world;

import java.util.ArrayList;
import java.util.List;

import assets.meshes.Mesh3D;
import mapModes.MapModesCreater;
import mapModes.MapModesManager;
import math.vectors.Vector3f;
import models.gameboard.GameBoardModel;
import models.gameboard.ModelCreater;
import models.meeples.CityModel;
import models.seeds.SuperGrid;
import models.seeds.noise.TrigonalNoise;
import utils.Statistics;
import utils.Percentage;
import utils.ProvisionalUI;

//TODO: 1. shitty name, 2. try to encapsulate graphic and logic
public class WorldManager {
	
	private static int lengthInTiles, widthInTiles;
	private static int NUM_TILES;
	
	private static SuperGrid superGrid;
	private static GameBoardModel gameBoardModel;
	private static List<Mesh3D> meepleModels;
	
	private static float[] fertility;
	
	private static ProvisionalUI ui;
	
	//*************************** init ******************************
	
	public static void init(int lengthInTiles, int widthInTiles) {
		
		WorldManager.lengthInTiles = lengthInTiles;
		WorldManager.widthInTiles  = widthInTiles;
		
		NUM_TILES = lengthInTiles*widthInTiles;
		
		meepleModels = new ArrayList<>();
		
		initModels();
		initFertility();
		initGameBoard();
		initMapModes();
		
		BuildingAuthority.init(gameBoardModel, meepleModels, superGrid);
		AgentAuthority.init(gameBoardModel, meepleModels, superGrid);
		
	}
	
	private static void initModels() {
		
		ModelCreater modelCreater = new ModelCreater(lengthInTiles, widthInTiles);
		
		gameBoardModel = modelCreater.createModels();
		superGrid   = modelCreater.getSuperGrid();
		
	}
	
	private static void initFertility() {
		
		TrigonalNoise noise = new TrigonalNoise(lengthInTiles, widthInTiles, 2, 2);
		
		fertility = new float[lengthInTiles*widthInTiles];
		
		for (int x=0; x<lengthInTiles; x++) {
			
			for (int y=0; y<widthInTiles; y++) {
				
				fertility[x + y*lengthInTiles] = noise.getValue(x, y);
				
			}
			
		}
		
	}
	
	private static void initGameBoard() {
		
		Tile[] tiles = new Tile[NUM_TILES];
		for (int t=0; t<NUM_TILES; t++) {
			
			float[] heights = getHeights(t);
			
			float avgHeight = Statistics.averageOf(heights);
			float heightSTDV = Statistics.standardDeviationOf(heights, avgHeight);
			
			tiles[t] = new Tile(t, avgHeight, heightSTDV, new Percentage(fertility[t]));
			
		}
		GameBoard.setTiles(tiles, lengthInTiles, widthInTiles);
	}
	
	private static void initMapModes() {
		MapModesManager mmm = new MapModesManager(MapModesCreater.getMapModes(), gameBoardModel);
		ui = new ProvisionalUI(mmm);
	}
	
	//TODO
	public static void changeMM(int m) {
		ui.mmm.changeModeTo(m);
	}
	
	
	//*************************** utils  ****************************
	
	private static float[] getHeights(int indexOfTile) {
		Vector3f center = superGrid.getHexCenter(indexOfTile);
		Vector3f[] border = superGrid.getHexBorder(indexOfTile);
		
		float[] heights = new float[7];
		heights[0] = center.getC();
		for (int i=1; i<heights.length; i++) {
			heights[i] = border[i-1].getC();
		}
		
		return heights;
	}
	
	
	//*************************** update ****************************
	
	public static void update() {
		
		ui.processInput();
		
	}
	
	//*************************** render ****************************
	
	public static void render() {
		
		gameBoardModel.render();
		
		for (Mesh3D meeple : meepleModels) {
			meeple.render(GameBoardModel.scene);
		}
		
	}
	
	//**************************** get ******************************
	
	public static Vector3f[] getTileCenterPos() {
		
		Vector3f[] tileCenterPositions = new Vector3f[lengthInTiles*widthInTiles];
		
		for (int v=0; v<tileCenterPositions.length; v++) {
			tileCenterPositions[v] = superGrid.getHexCenter(v);
		}
		
		SuperGrid.adjustToTerrainAndSea(tileCenterPositions);
		
		return tileCenterPositions;
		
	}
	
	//*****************************************************************
	
	
	
	/**
	 * @return the number of tiles on the game board
	 */
	public static int getNumTiles() {
		return NUM_TILES;
	}
	
	/**
	 * @return the length in tiles
	 */
	public static int getLengthInTiles() {
		return lengthInTiles;
	}

	/**
	 * @return the width in tiles
	 */
	public static int getWidthInTiles() {
		return widthInTiles;
	}

	/**
	 * @param i index of requested tile
	 * @return the fertility of the requested tile
	 */
	public static float getFertility(int i) {
		return fertility[i];
	}
	
}