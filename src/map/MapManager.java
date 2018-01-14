package map;

import interaction.CameraOperator;
import interaction.tileSelection.TileSelecter;
import assets.light.LightSource;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.geometry.Color;
import math.MatrixManager;
import graphics.Camera;
import graphics.matrices.Matrices;
import graphics.matrices.ProjectionMatrix;
import graphics.matrices.ViewMatrix;
import graphics.shaders.ShaderManager;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import models.TerrainCol;
import rendering.RenderEngine;
import visualize.CoordinateSystem;
import visualize.FontTest;
import visualize.TextureTests;
import core.saves.GameScore;
import elements.Tile;
import fontRendering.texture.FontTexture;
import models.seeds.ColorFunction;
import models.seeds.Terrain;
import models.worldModels.HexagonBorderMesh;
import models.worldModels.TriangleMesh;


import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.CGL.CGLErrorString;


public class MapManager {
	
	private static TriangleMesh geographicMap;
	
	//TODO: Merge both hexagonBorderMaps
	private static HexagonBorderMesh hexagonBorderMap;
	
	private static TriangleMesh seaModel;
	
	private static CoordinateSystem coordinates;
	
	
	private static Vector4f hoveredTileColor;
	
	private static Vector4f selectedTileColor;
	
	
	private static Matrix44f geoMapModelMatrix;
	
	private static Matrix44f hexagonBorderModelMatrix;
	
	
	
	private static Material mapMaterial;
	
	private static LightSource sun;
	private static Vector3f ambientLight;
	
	
	public static void init(int widthInHex, int heightInHex) {
		
		//Load the 3D Terrain
		Terrain terrain = new Terrain(widthInHex, heightInHex);
		geographicMap = new TriangleMesh(0.1f, terrain.getElevationArray(), new TerrainCol(), new StandardMaterial());
		
		//Load the the model to display the sea:
		float[][] seaLevel = new float[widthInHex][heightInHex];
		for (int i=0; i<widthInHex; i++) {
			for (int j=0; j<heightInHex; j++) {
				seaLevel[i][j] = 0;
			}
		}
		ColorFunction seaColor = new ColorFunction() {
			@Override
			public Color color(int x, int y, float height) {
				return new Color(0, 0.2f, 0.7f, 0.7f);
			}
		};
		seaModel = new TriangleMesh(0.1f, seaLevel, seaColor, new StandardMaterial());
		
		//Load the hexagon borders
		hexagonBorderMap = new HexagonBorderMesh(geographicMap, 0, 0, 1);
		
		
		//The color of the tile the mouse cursor is on
		hoveredTileColor = new Vector4f(1f, 1f, 0f, 1f);
		
		//The coloe of the currently selected tile
		selectedTileColor = new Vector4f(1f, 0f, 0f, 1f);

		
		//A coordinate system that is mostly used for testing
		coordinates = new CoordinateSystem(100.0f);
		
		
		FontTest.init("The quick brown fox \njumps over the lazy dog!");
		
		
		
		//The model matrix for the map (as we only move the map the model matrix won't change)	
		geoMapModelMatrix = MatrixManager.generateModelMatrix(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f), 1f);
	
		hexagonBorderModelMatrix = MatrixManager.generateModelMatrix(new Vector3f(0f, 0f, 0.01f), new Vector3f(0f, 0f, 0f), 1f);
		
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
		
		ShaderManager.useLightShader(geoMapModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), Camera.getPosition(), sun, ambientLight, mapMaterial);
		
		RenderEngine.draw(geographicMap, null);
		
		ShaderManager.disableLightShader();
		
		
		ShaderManager.useShader(geoMapModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), false, null);
		
		RenderEngine.draw(hexagonBorderMap, null);
		
		RenderEngine.draw(seaModel, null);
		
		RenderEngine.draw(coordinates, null);
		
		ShaderManager.disableShader();
		
		
		ShaderManager.useShader(hexagonBorderModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), true, hoveredTileColor);
		
		hexagonBorderMap.display(TileSelecter.getHoveredTileIndex());
		RenderEngine.draw(hexagonBorderMap, null);
		
		ShaderManager.disableShader();
		
		
		ShaderManager.useShader(hexagonBorderModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), true, selectedTileColor);
		
		hexagonBorderMap.display(TileSelecter.getSelectedTileIndex());
		RenderEngine.draw(hexagonBorderMap, null);
		
		ShaderManager.disableShader();
		
		
		ShaderManager.useFontShader(new Matrix44f());
		
		RenderEngine.draw(FontTest.getModel(), FontTest.getTexture());
		
		ShaderManager.disableTexturedMeshShader();
		
		hexagonBorderMap.displayAll();
		
	}
	
	
	public static Matrix44f getMapModelMVPMatrix() {
		
		return geoMapModelMatrix.times(CameraOperator.getViewMatrix()).times(Matrices.getProjectionMatrix());
		
	}
	
	
	public static Matrix44f getModelMatrix() {
		
		return geoMapModelMatrix;
	
	}
	
	
	public static Vector3f[] getTileCenterVertices() {
		
		return hexagonBorderMap.getCenterVertices();
	
	}
	

}
