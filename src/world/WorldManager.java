package world;

import assets.meshes.geometry.Vertex;
import math.vectors.Vector3f;
import models.worldModels.BoardModels;
import models.worldModels.ModelCreater;

//TODO: 1. shitty name, 2. try to encapsulate graphic and logic
public class WorldManager {
	
	private static BoardModels boardModels;
	
	private static Vertex[] vertices;
	private static float[] fertility;
	private static int[] tileCenterIndices;
	
	//*************************** init ******************************
	
	public static void init(int lengthInHexagons, int widthInHexagons) {
		
		ModelCreater modelCreater = new ModelCreater(lengthInHexagons, widthInHexagons);
		
		boardModels = modelCreater.createModels();
		
		vertices = boardModels.getVertices();
		tileCenterIndices = boardModels.getTileCenters();
		
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
	
}