package world;

import assets.meshes.geometry.Vertex;
import models.worldModels.BoardModels;
import models.worldModels.ModelCreater;

public class WorldManager {
	
	private static BoardModels boardModels;
	
	
	private static Vertex[] vertices;
	private static float[] fertility;
	private static int[] tileCenterIndices;
	
	//*************************** init *****************************
	
	public static void init(int lengthInHexagons, int widthInHexagons) {
		
		ModelCreater modelCreater = new ModelCreater(lengthInHexagons, widthInHexagons);
		
		boardModels = modelCreater.createModels();
		vertices = boardModels.getVertices();
		tileCenterIndices = boardModels.getTileCenters();
		
	}
	
	//**************************************************************
	
	
}