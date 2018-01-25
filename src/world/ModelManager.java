package world;

import interaction.CameraOperator;
import interaction.TileSelecter;
import assets.light.LightSource;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.geometry.Color;
import math.MatrixManager;
import graphics.Camera;
import graphics.matrices.Matrices;
import graphics.shaders.ShaderManager;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import models.TerrainCol;
import rendering.RenderEngine;
import visualize.CoordinateSystem;
import visualize.FontTest;
import core.saves.GameScore;
import models.seeds.ColorFunction;
import models.seeds.ElevationMap;
import models.seeds.noise.TrigonalNoise;
import models.worldModels.HexagonBorderGrid;
import models.worldModels.TriangleGrid;
import utils.ArrayUtil;
import utils.Calc;


public class ModelManager {
	
	//measurements
	private static int lengthInHexagons;
	private static int widthInHexagons;
	
	private static int hexLength;		//this is not the length/width measured in hexagons, but
	private static int hexWidth;		//	the l/w of the hex mesh measured in vertices
	
	private static int triLength;
	private static int triWidth;
	
	private static int xOffset;
	private static int yOffset;
	
	private static int elr;
	
	
	//hard code
	private static float hexEdgeLength = 0.1f;
	
	private static final int halfXOffset = 2;
	private static final int halfYOffset = 2;
	
	private static final int log2EdgeLengthRelation = 1;
	
	
	//models
	private static TriangleGrid geographicMap;
	
	//TODO: Merge both hexagonBorderMaps
	private static HexagonBorderGrid hexagonBorderMesh;
	
	private static TriangleGrid seaModel;
	
	private static CoordinateSystem coordinates;
	
	private static TrigonalNoise fertilityNoise;
	
	private static Vector4f hoveredTileColor;
	
	private static Vector4f selectedTileColor;
	
	
	//matrices
	private static Matrix44f geoMapModelMatrix;
	
	private static Matrix44f hexagonBorderModelMatrix;
	
	
	//others
	private static Material mapMaterial;
	
	private static LightSource sun;
	private static Vector3f ambientLight;
	
	private static Vector3f[] hexMeshVertices;
	
	public static void init(int lengthInHex, int widthInHex) {
		
		lengthInHexagons = lengthInHex;
		widthInHexagons  = widthInHex;
		
		calculations();
		
		System.out.println("hexagons: " + lengthInHexagons + " x " + widthInHexagons);
		
		//Load the 3D Terrain
		ElevationMap terrain = new ElevationMap(triLength, triWidth);
		geographicMap = new TriangleGrid(hexEdgeLength, terrain.getElevationArray(), new TerrainCol(), new StandardMaterial());
		
		initSea();
		
		//Load the hexagon borders
		createHexBorders();

		
		//The color of the tile the mouse cursor is on
		hoveredTileColor = new Vector4f(1f, 1f, 0f, 1f);
		
		//The color of the currently selected tile
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
		
		setupGameBoard();
		
	}
	
	
	public static void init(GameScore save) {
		
		//Load an existing map
		
	}
	
	
	public static void render() {
		
		ShaderManager.useLightShader(geoMapModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), Camera.getPosition(), sun, ambientLight, mapMaterial);
		
		RenderEngine.draw(geographicMap, null);
		
		ShaderManager.disableLightShader();
		
		
		ShaderManager.useShader(geoMapModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), false, null);
		
		RenderEngine.draw(hexagonBorderMesh, null);
		
		RenderEngine.draw(seaModel, null);
		
		RenderEngine.draw(coordinates, null);
		
		ShaderManager.disableShader();
		
		//TODO: temporary: I want to visualize the fertility of a tile
		hoveredTileColor = new Color(1, 0, GameBoard.getTile(TileSelecter.getHoveredTileIndex()).getFertility(), 1);
		
		
		ShaderManager.useShader(hexagonBorderModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), true, hoveredTileColor);
		
		hexagonBorderMesh.display(TileSelecter.getHoveredTileIndex());
		RenderEngine.draw(hexagonBorderMesh, null);
		
		ShaderManager.disableShader();
		
		
		ShaderManager.useShader(hexagonBorderModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), true, selectedTileColor);
		
		hexagonBorderMesh.display(TileSelecter.getSelectedTileIndex());
		RenderEngine.draw(hexagonBorderMesh, null);
		
		ShaderManager.disableShader();
		
		
		ShaderManager.useFontShader(new Matrix44f());
		
		RenderEngine.draw(FontTest.getModel(), FontTest.getTexture());
		
		ShaderManager.disableTexturedMeshShader();
		
		hexagonBorderMesh.displayAll();
		
	}
	
	
	public static Matrix44f getMapModelMVPMatrix() {
		
		return geoMapModelMatrix.times(CameraOperator.getViewMatrix()).times(Matrices.getProjectionMatrix());
		
	}
	
	
	public static Matrix44f getModelMatrix() {
		
		return geoMapModelMatrix;
	
	}
	
	
	public static Vector3f[] getTileCenterVertices() {
		
		return hexagonBorderMesh.getCenterVertices();
	
	}
	
	//----------------------------------------------------------------------------
	private static void setupGameBoard() {
		
		//actually we need a hexagonal noise
		fertilityNoise = new TrigonalNoise(lengthInHexagons, widthInHexagons, 4, 6);
		
		Tile[] tiles = new Tile[lengthInHexagons*widthInHexagons];
		
		for (int t=0; t<tiles.length; t++) {
			float fertility = fertilityNoise.getValue(t%lengthInHexagons, t/lengthInHexagons);
			tiles[t] = createTile(t, fertility);
		}
		
		GameBoard.setTiles(tiles);
		
	}
	
	//------------------------------------------------------------------------------
	private static void calculations() {
		
		hexLength = (lengthInHexagons + 1) * 2;
		hexWidth = widthInHexagons + 1;
		
		xOffset = 2 * halfXOffset;
		yOffset = 2 * halfYOffset;
		
		elr = (int)Math.pow(2, log2EdgeLengthRelation);
		
		triLength = (hexLength-1)*elr + 2*xOffset + 1;
		triWidth  = ((hexWidth-1)*3/2 + 1)*elr + 2*yOffset;
		
	}
	
	private static void createHexBorders() {
		
		hexagonBorderMesh = new HexagonBorderGrid(geographicMap, halfXOffset, halfYOffset, log2EdgeLengthRelation);
		
		hexMeshVertices = hexagonBorderMesh.getVertices();
		
	}
	
	private static void initSea() {
		
		seaModel = new TriangleGrid(0.1f, new float[triLength][triWidth], new Color(0, 0.2f, 0.7f, 0.7f), new StandardMaterial());
		
	}
	
	private static Tile createTile(int index, float fertility) {
		
		int x = index/lengthInHexagons;
		int y = index%lengthInHexagons;
		
		//get height data
		int[] indices = hexagonBorderMesh.getHexagonIndexArray(x, y, y%2);
		float[] heights = new float[indices.length];
		for (int i=0; i<indices.length; i++) {
			int z = indices[i];
			if (z<hexMeshVertices.length) {
				
				float temp = hexMeshVertices[z].getC();
				heights[i] = temp;
			}
		}
		
		
		float avgHeight = Calc.average(heights);
		
		float heightSTDV = Calc.standardDeviation(heights, avgHeight);
		
		return new Tile(index, avgHeight, heightSTDV, fertility);
		
	}
	
}
