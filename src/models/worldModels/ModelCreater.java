package models.worldModels;

import assets.material.StandardMaterial;
import assets.meshes.geometry.Color;
import math.vectors.Vector3f;
import models.TerrainCol;
import models.seeds.ElevationMap;
import visualize.CoordinateSystem;

public class ModelCreater {
	
	//********************************* fields *********************************
	
	//measurements
	private static int lengthInHex;
	private static int widthInHex;
	
	private static int triLength;
	private static int triWidth;
	
	private static int xOffset;
	private static int yOffset;
	
	private static int elr;		//edgeLengthRelation
	
	
	//hard code
	private static float hexEdgeLength = 0.1f;
	
	private static final int halfXOffset = 2;
	private static final int halfYOffset = 2;
	
	private static final int log2EdgeLengthRelation = 1;
	
	
	//models
	private static TriangleGrid terrain;
	
	private static HexagonBorderGrid tileBorders;
	
	private static TriangleGrid sea;
	
	private static CoordinateSystem coSystem;
	
	//others
	private static Vector3f[] hexMeshVertices;
	
	//********************************** initialization ************************
	
	public static void init(int lengthInHexagons, int widthInHexagons) {
		
		lengthInHex = lengthInHexagons;
		widthInHex = widthInHexagons;
		
		calculations();
		
	}
	
	
	//********************************** ergon *********************************
	
	public static BoardModels createModels() {
		
		createTerrain();
		createTileBorders();
		createSea();
		createCoSystem();
		
		return new BoardModels(terrain, tileBorders, sea, coSystem);
		
	}
	
	public static void setupGameBoard() {
		
	}
	
	//********************************* prime methods **************************
	
	private static void createTerrain() {
		
		ElevationMap terrainData = new ElevationMap(triLength, triWidth);
		terrain = new TriangleGrid(hexEdgeLength, terrainData.getElevationArray(), new TerrainCol(), new StandardMaterial());

	}
	
	private static void createTileBorders() {
		
		tileBorders = new HexagonBorderGrid(terrain, halfXOffset, halfYOffset, log2EdgeLengthRelation);
		
		hexMeshVertices = tileBorders.getVertices();
		
	}
	
	private static void createSea() {
		
		sea = new TriangleGrid(0.1f, new float[triLength][triWidth], new Color(0, 0.2f, 0.7f, 0.7f), new StandardMaterial());
		
	}
	
	private static void createCoSystem() {
		
		coSystem = new CoordinateSystem(100.0f);
		
	}
	
	//******************************** calculations *******************************
	
	private static void calculations() {
		
		xOffset = 2 * halfXOffset;
		yOffset = 2 * halfYOffset;
		
		elr = (int)Math.pow(2, log2EdgeLengthRelation);
		
		triLength = (((lengthInHex + 1) * 2)-1)*elr + 2*xOffset + 1;
		triWidth  = (widthInHex*3/2 + 1)*elr + 2*yOffset;
		
	}
	
}
