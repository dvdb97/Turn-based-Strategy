package map;

import interaction.PlayerCamera;
import assets.light.LightSource;
import assets.material.Material;
import assets.material.StandardMaterial;
import math.MatrixManager;
import graphics.Camera;
import graphics.matrices.Matrices;
import graphics.matrices.ProjectionMatrix;
import graphics.shaders.ShaderManager;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import models.HexagonBorderMesh;
import models.TerrainCol;
import models.TriangleMesh;
import rendering.RenderEngine;
import visualize.CoordinateSystem;
import core.saves.GameScore;
import models.seeds.Terrain;

public class MapManager {
	
	private static TriangleMesh geographicMap;
	
	private static HexagonBorderMesh hexagonBorderMap;
	
	private static CoordinateSystem coordinates;
	
	
	private static Matrix44f geoMapModelMatrix;
	
	
	private static Material mapMaterial;
	
	private static LightSource sun;
	private static Vector3f ambientLight;
	
	
	private boolean hexagonBordersEnabled = true;
	
	
	public static void init(int widthInHex, int heightInHex) {
		
		//Load the 3D Terrain
		Terrain terrain = new Terrain(heightInHex, widthInHex);
		geographicMap = new TriangleMesh(0.1f, terrain.getElevationArray(), new TerrainCol(), new StandardMaterial());
		
		
		//Load the hexagon borders
		hexagonBorderMap = new HexagonBorderMesh(geographicMap, 0, 0, 1);
		
		
		//A coordinate system that is mostly used for testing
		coordinates = new CoordinateSystem(100.0f);
		
		
		//The model matrix for the map (as we only move the map the model matrix won't change)
		geoMapModelMatrix = MatrixManager.generateModelMatrix(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f), 1f);
	
		
		//TODO: Figure out better values for the material
		mapMaterial = new Material(new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.1f, 0.1f, 0.1f), 1f);
		
		//TODO: Figure out a better position / color for this light
		sun = new LightSource(new Vector3f(-0.3f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.3f));
		ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
		
	}
	
	
	public static void init(GameScore save) {
		
		//Load an existing map
		
	}
	
	
	public static void render() {
		
		ShaderManager.useLightShader(geoMapModelMatrix, PlayerCamera.getViewMatrix(), Matrices.getProjectionMatrix(), Camera.getPosition(), sun, ambientLight, mapMaterial);
		
		RenderEngine.draw(geographicMap, null);
		
		ShaderManager.disableLightShader();
		
		
		ShaderManager.useShader(geoMapModelMatrix, PlayerCamera.getViewMatrix(), Matrices.getProjectionMatrix());
		
		RenderEngine.draw(hexagonBorderMap, null);
		
		RenderEngine.draw(coordinates, null);
		
		ShaderManager.disableShader();
		
	}
	

}
