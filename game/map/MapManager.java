package map;

import assets.material.StandardMaterial;
import assets.models.Element_Model;
import assets.models.Illuminated_Model;
import models.HexagonBorderMesh;
import models.TriangleMesh;
import save.GameScore;
import terrain.Terrain;

public class MapManager {
	
	private static TriangleMesh geographicMap;
	
	private static HexagonBorderMesh hexagonBorderMap;
	
	//Current map mode
	
	
	public static void init(int widthInHex, int heightInHex) {
		
		//Load the 3D Terrain
		Terrain terrain = new Terrain(heightInHex, widthInHex);
		geographicMap = new TriangleMesh(0.1f, terrain.getElevationArray(), new StandardMaterial());
		
		
		//Load the hexagon borders
		hexagonBorderMap = new HexagonBorderMesh(geographicMap, 0, 0, 1);
		
	}
	
	
	public static void init(GameScore save) {
		
		//Load an existing map
		
	}
	
	
	public static void render() {
		
		//Display the maps that are currently active	
		
	}
	

}
