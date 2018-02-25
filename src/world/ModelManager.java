package world;

import interaction.CameraOperator;
import interaction.input.CursorPosInput;
import interaction.input.MouseInputManager;
import interaction.tileSelection.TileSelecter;
import assets.light.LightSource;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.geometry.Color;
import math.MatrixManager;
import graphics.Camera;
import graphics.matrices.Matrices;
import graphics.shaders.ShaderManager;
import gui.test.ImplementedWindow;
import gui_core.GUIManager;
import gui_core.GUIShaderCollection;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import models.TerrainCol;
import rendering.RenderEngine;
import rendering.shapes.implemented.GUIQuad;
import visualize.CoordinateSystem;
import visualize.FontTest;
import visualize.TextureTests;
import core.Application;
import core.saves.GameScore;
import models.seeds.ColorFunction;
import models.seeds.Terrain;
import models.worldModels.HexagonBorderMesh;
import models.worldModels.TriangleMesh;
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
	private static TriangleMesh geographicMap;
	
	//TODO: Merge both hexagonBorderMaps
	private static HexagonBorderMesh hexagonBorderMap;
	
	private static TriangleMesh seaModel;
	
	private static CoordinateSystem coordinates;
	
	
	private static Vector4f hoveredTileColor;
	
	private static Vector4f selectedTileColor;
	
	private static ImplementedWindow window;
	
	
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
		System.out.println("hexLW   : " + hexLength + " x " + hexWidth);
		System.out.println("triLW   : " + triLength + " x " + triLength);
		
		//Load the 3D Terrain
		Terrain terrain = new Terrain(triLength, triWidth);
		geographicMap = new TriangleMesh(hexEdgeLength, terrain.getElevationArray(), new TerrainCol(), new StandardMaterial());
		
		//Load the the model to display the sea:
		float[][] seaLevel = new float[widthInHex][lengthInHex];
		for (int i=0; i<widthInHex; i++) {
			for (int j=0; j < lengthInHex; j++) {
				seaLevel[i][j] = 0;
			}
		}
		ColorFunction seaColor = new ColorFunction() {
			@Override
			public Color color(int x, int y, float height) {
				return new Color(0, 0.2f, 0.7f, 0.8f);
			}
		};
		seaModel = new TriangleMesh(0.1f, seaLevel, seaColor, new StandardMaterial());
		initSea();
		
		//Load the hexagon borders
		createHexBorders();

		
		//The color of the tile the mouse cursor is on
		hoveredTileColor = new Vector4f(1f, 1f, 0f, 1f);
		
		//The color of the currently selected tile
		selectedTileColor = new Vector4f(1f, 0f, 0f, 1f);
		
		window = new ImplementedWindow(new Vector4f(1f, 0f, 0f, 1f), -0.8f, 0f, 0.8f, 0.8f);

		
		//A coordinate system that is mostly used for testing
		coordinates = new CoordinateSystem(100.0f);
		
		
		TextureTests.init("res/fonts/Font.png");
		
		
		
		//The model matrix for the map (as we only move the map the model matrix won't change)	
		geoMapModelMatrix = MatrixManager.generateModelMatrix(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f), 1f);
	
		hexagonBorderModelMatrix = MatrixManager.generateModelMatrix(new Vector3f(0f, 0f, 0.01f), new Vector3f(0f, 0f, 0f), 1f);
		
		//TODO: Figure out better values for the material
		mapMaterial = new Material(new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.1f, 0.1f, 0.1f), 1f);
		
		//TODO: Figure out a better position / color for this light
		sun = new LightSource(new Vector3f(-0.3f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.3f));
		ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
		
		
		
	//	setupGameBoard();	out-commented, because NUllPointerException in line 290
		
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
		
		
		ShaderManager.useFontShader(new Matrix44f(), true);
		
		RenderEngine.draw(TextureTests.getTestModel(), TextureTests.getTexture());
		
		ShaderManager.disableFontShader();
		
		
		hexagonBorderMap.displayAll();
		
		//Draw the gui
		GUIManager.update();
				
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
	
	//----------------------------------------------------------------------------
	private static void setupGameBoard() {
		
		Tile[] tiles = new Tile[lengthInHexagons*widthInHexagons];
		
		for (int t=0; t<tiles.length; t++) {
			tiles[t] = createTile(t);
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
		
		hexagonBorderMap = new HexagonBorderMesh(geographicMap, halfXOffset, halfYOffset, log2EdgeLengthRelation);
		
		hexMeshVertices = new Vector3f[hexagonBorderMap.getVerticesLength()];
		
		hexagonBorderMap.getVertices(hexMeshVertices);
		
		
	}
	
	private static void initSea() {
		
		float[][] seaLevel = new float[triLength][triWidth];
		for (int i=0; i<triLength; i++) {
			for (int j=0; j<triWidth; j++) {
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
		
		
	}
	
	private static Tile createTile(int index) {
		
		int x = index/lengthInHexagons;
		int y = index%lengthInHexagons;
		
		//get height data
		int[] indices = hexagonBorderMap.getHexagonIndexArray(x, y, y%2);
		float[] heights = new float[indices.length];
		for (int i=0; i<indices.length; i++) {
			int z = indices[i];
			if (z<hexMeshVertices.length) {
				
				if (hexMeshVertices[z] == null) {
					System.out.println("createTile: hexMeshVertices[" + z + "] == null");
				}
				
				float temp = hexMeshVertices[z].getC();		//NullPointerException
				heights[i] = temp;
			}
		}
		
		
		float avgHeight = Calc.average(heights);
		
		float heightSTDV = Calc.standardDeviation(heights, avgHeight);
		
		return new Tile(index, avgHeight, heightSTDV);
		
	}
	
}
