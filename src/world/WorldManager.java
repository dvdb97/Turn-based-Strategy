package world;

import assets.meshes.geometry.Vertex;
import math.vectors.Vector3f;
import models.seeds.noise.TrigonalNoise;
import models.worldModels.BoardModels;
import models.worldModels.ModelCreater;

//TODO: 1. shitty name, 2. try to encapsulate graphic and logic
public class WorldManager {
	
	private static int lengthInTiles, widthInTiles;
	
	private static BoardModels boardModels;
	
	private static Vertex[] vertices;
	private static float[] fertility;
	private static int[] tileCenterIndices;
	
	//*************************** init ******************************
	
	public static void init(int lengthInTiles, int widthInTiles) {
		
		ModelCreater modelCreater = new ModelCreater(lengthInTiles, widthInTiles);
		
		boardModels = modelCreater.createModels();
		
		vertices = boardModels.getVertices();
		tileCenterIndices = boardModels.getTileCenters();
		
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
		
		Vector3f[] hexCenterVectors = new Vector3f[tileCenterIndices.length];
		
		for (int i=0; i<hexCenterVectors.length; i++) {
			
			hexCenterVectors[i] = vertices[tileCenterIndices[i]].getPosition();
			
		}
		
		return hexCenterVectors;
		
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