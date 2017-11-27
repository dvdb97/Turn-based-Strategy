package map;

import Interaction.PlayerCamera;
import assets.light.LightSource;
import assets.material.Material;
import assets.material.StandardMaterial;
import graphics.Camera;
import graphics.ProjectionMatrix;
import graphics.shaders.ShaderManager;
import lwlal.Matrix44f;
import lwlal.Vector3f;
import models.HexagonBorderMesh;
import models.TriangleMesh;
import rendering.RenderEngine;
import save.GameScore;
import terrain.Terrain;


/*
 * This class manages all the maps and mapmodes. Its responsible for:
 * 
 * - Handling all the map modes (e.g. political map mode / economic map mode / etc.)
 * 
 * - Displaying the maps that are currently active
 * 
 */
public class MapManager {
	
	private static TriangleMesh geographicMap;
	
	private static HexagonBorderMesh hexagonBorderMap;
	
	
	private static Matrix44f modelMatrix;
	
	private static LightSource sun; 
	
	private static Material worldMaterial;
	
	
	public static void init(int widthInHex, int heightInHex) {
		
		//Load the 3D Terrain
		Terrain terrain = new Terrain(heightInHex, widthInHex);
		geographicMap = new TriangleMesh(0.1f, terrain.getElevationArray(), new StandardMaterial());
		
		
		//Load the hexagon borders
		hexagonBorderMap = new HexagonBorderMesh(geographicMap, 0, 0, 1);
		
		
		//The model matrix for the map (as we only move the map the model matrix won't change)
		modelMatrix = new Matrix44f();
		
		//The position of the sun (Might be changed while during runtime when we decide to implement sunrises / sunsets). TODO: Assign better values to it 
		sun = new LightSource(new Vector3f(0.4f, -0.7f, -0.5f), new Vector3f(1f, 1f, 0f));
		
		//The material for the computation of light on the map
		worldMaterial = new Material(new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.1f, 0.1f, 0.1f), 1.0f);	
		
	}
	
	
	public static void init(GameScore save) {
		
		//Load an existing map
		
	}
	
	
	public static void render() {
		
		ShaderManager.useLightShader(modelMatrix, PlayerCamera.getViewMatrix(), ProjectionMatrix.getProjectionMatrix(), Camera.getPosition(), sun, worldMaterial);
		
		RenderEngine.draw(geographicMap, null);
		
		ShaderManager.disableLightShader();
		
		
		ShaderManager.useShader(modelMatrix, PlayerCamera.getViewMatrix(), ProjectionMatrix.getProjectionMatrix());
		
		RenderEngine.draw(hexagonBorderMap, null);
		
		ShaderManager.disableShader();
		
	}
	

}
