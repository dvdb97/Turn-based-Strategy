package world;

import mapModes.MapModesCreater;
import mapModes.MapModesManager;
import math.vectors.Vector3f;
import models.seeds.SuperGrid;
import models.seeds.noise.TrigonalNoise;
import models.worldModels.BoardModels;
import models.worldModels.ModelCreater;
import utils.ProvisionalUI;

//TODO: 1. shitty name, 2. try to encapsulate graphic and logic
public class WorldManager {
	
	private static int lengthInTiles, widthInTiles;
	private static int NUM_TILES;
	
	private static BoardModels boardModels;
	private static SuperGrid superGrid;
	
	private static float[] fertility;
	
	private static ProvisionalUI ui;
	
	//*************************** init ******************************
	
	public static void init(int lengthInTiles, int widthInTiles) {
		
		WorldManager.lengthInTiles = lengthInTiles;
		WorldManager.widthInTiles  = widthInTiles;
		
		NUM_TILES = lengthInTiles*widthInTiles;
		
		setUpFertility();

		ModelCreater modelCreater = new ModelCreater(lengthInTiles, widthInTiles);
		
		boardModels = modelCreater.createModels(fertility);
		superGrid   = modelCreater.getSuperGrid();
		
		MapModesManager mmm = new MapModesManager(MapModesCreater.getMapModes(), boardModels);
		ui = new ProvisionalUI(mmm);
		
	}
	
	//*************************** update ****************************
	
	public static void update() {
		
		ui.processInput();
		
	}
	
	//*************************** render ****************************
	
	public static void render() {
		
		boardModels.render();
		
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
	
	private static void setUpFertility() {
		
		TrigonalNoise noise = new TrigonalNoise(lengthInTiles, widthInTiles, 2, 2);
		
		fertility = new float[lengthInTiles*widthInTiles];
		
		for (int x=0; x<lengthInTiles; x++) {
			
			for (int y=0; y<widthInTiles; y++) {
				
				fertility[x + y*lengthInTiles] = noise.getValue(x, y);
			}
			
		}
		
		
	}
	
	/**
	 * @return the number of tiles on the game board
	 */
	public static int getNumTiles() {
		return NUM_TILES;
	}
	
	/**
	 * @param i index of requested tile
	 * @return the fertilty of the requested tile
	 */
	public static float getFertility(int i) {
		return fertility[i];
	}
	
}