package map;

import Interaction.PlayerCamera;
import assets.material.StandardMaterial;
import assets.models.Element_Model;
import assets.models.Illuminated_Model;
import graphics.Camera;
import graphics.ProjectionMatrix;
import graphics.shaders.ShaderManager;
import lwlal.Matrix44f;
import models.HexagonBorderMesh;
import models.TriangleMesh;
import rendering.RenderEngine;
import save.GameScore;
import terrain.Terrain;

public class MapManager {
	
	private static TriangleMesh geographicMap;
	
	private static HexagonBorderMesh hexagonBorderMap;
	
	
	private static  Matrix44f modelMatrix;
	
	
	private boolean hexagonBordersEnabled = true;
	
	
	public static void init(int widthInHex, int heightInHex) {
		
		//Load the 3D Terrain
		Terrain terrain = new Terrain(heightInHex, widthInHex);
		geographicMap = new TriangleMesh(0.1f, terrain.getElevationArray(), new StandardMaterial());
		
		
		//Load the hexagon borders
		hexagonBorderMap = new HexagonBorderMesh(geographicMap, 0, 0, 1);
		
		
		//The model matrix for the map (as we only move the map the model matrix won't change)
		modelMatrix = new Matrix44f();
		
	}
	
	
	public static void init(GameScore save) {
		
		//Load an existing map
		
	}
	
	
	public static void render() {
		
		ShaderManager.useLightShader(modelMatrix, PlayerCamera.getViewMatrix(), ProjectionMatrix.getProjectionMatrix(), Camera.getPosition(), light, mat);
		
		RenderEngine.draw(geographicMap, null);
		
		ShaderManager.disableLightShader();
		
		
		ShaderManager.useShader(modelMatrix, PlayerCamera.getViewMatrix(), ProjectionMatrix.getProjectionMatrix());
		
		RenderEngine.draw(hexagonBorderMap, null);
		
		ShaderManager.disableShader();
		
	}
	

}
