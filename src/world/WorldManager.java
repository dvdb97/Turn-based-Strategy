package world;

import math.vectors.Vector3f;
import models.seeds.SuperGrid;
import models.seeds.noise.TrigonalNoise;
import models.worldModels.BoardModels;
import models.worldModels.HexagonBorderGrid;
import models.worldModels.ModelCreater;

//TODO: 1. shitty name, 2. try to encapsulate graphic and logic
public class WorldManager {
	
	private static int lengthInTiles, widthInTiles;
	
	private static BoardModels boardModels;
	private static SuperGrid superGrid;
	
	private static float[] fertility;
	
	//*************************** init ******************************
	
	public static void init(int lengthInTiles, int widthInTiles) {
		
		ModelCreater modelCreater = new ModelCreater(lengthInTiles, widthInTiles);
		
		boardModels = modelCreater.createModels();
		superGrid   = modelCreater.getSuperGrid();
		
		WorldManager.lengthInTiles = boardModels.getLength();
		WorldManager.widthInTiles  = boardModels.getWidth();
		
		setUpFertility();
		
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
			if (tileCenterPositions[v].getC() < 0)
				tileCenterPositions[v].setC(0);
			tileCenterPositions[v].plusEQ(new Vector3f(0, 0, HexagonBorderGrid.getZ_SHIFT()));
		}
		
		return tileCenterPositions;
		
	}
	
	//*****************************************************************
	
	private static void setUpFertility() {
		
		TrigonalNoise noise = new TrigonalNoise(lengthInTiles, widthInTiles, 5, 5);
		
		fertility = new float[lengthInTiles*widthInTiles];
		
		for (int x=0; x<lengthInTiles; x++) {
			
			for (int y=0; y<widthInTiles; y++) {
				
				fertility[x + y*lengthInTiles] = noise.getValue(x, y);
			}
			
		}
		
	}
	
	/**
	 * @param i index of requested tile
	 * @return the fertilty of the requested tile
	 */
	public static float getFertility(int i) {
		return fertility[i];
	}
	
}