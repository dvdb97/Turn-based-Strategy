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
	private int lengthInHex;
	private int widthInHex;
	
	private int triLength;
	private int triWidth;
	
	private int xOffset;
	private int yOffset;
	
	private int elr;		//edgeLengthRelation
	
	
	//hard code
	private static float hexEdgeLength = 0.1f;
	
	private static final int halfXOffset = 2;
	private static final int halfYOffset = 2;
	
	private static final int log2EdgeLengthRelation = 1;
	
	
	//models
	private TriangleGrid terrain;
	
	private HexagonBorderGrid tileBorders;
	
	private TriangleGrid sea;
	
	private CoordinateSystem coSystem;
	
	//others
	private Vector3f[] hexMeshVertices;
	
	//********************************** initialization ************************
	
	/**
	 * @param lengthInHexagons length of the board in hexagons
	 * @param widthInHexagons width of the board inhexagons
	 */
	public ModelCreater(int lengthInHexagons, int widthInHexagons) {
		
		lengthInHex = lengthInHexagons;
		widthInHex = widthInHexagons;
		
		calculations();
		
	}
	
	
	//********************************** ergon *********************************
	
	/**
	 * creates all models needed to render the game board
	 * namely: terrain, sea, tile borders and an coordinate system
	 */
	public BoardModels createModels() {
		
		createTerrain();
		createTileBorders();
		createSea();
		createCoSystem();
		
		return new BoardModels(terrain, tileBorders, sea, coSystem);
		
	}
	
	//********************************* prime methods **************************
	
	private void createTerrain() {
		
		ElevationMap terrainData = new ElevationMap(triLength, triWidth);
		terrain = new TriangleGrid(hexEdgeLength, terrainData.getElevationArray(), new TerrainCol(), new StandardMaterial());

	}
	
	private void createTileBorders() {
		
		tileBorders = new HexagonBorderGrid(terrain, halfXOffset, halfYOffset, log2EdgeLengthRelation);
		
		hexMeshVertices = tileBorders.getVertices();
		
	}
	
	private void createSea() {
		
		sea = new TriangleGrid(0.1f, new float[triLength][triWidth], new Color(0, 0.2f, 0.7f, 0.7f), new StandardMaterial());
		
	}
	
	private void createCoSystem() {
		
		coSystem = new CoordinateSystem(100.0f);
		
	}
	
	//******************************** calculations *******************************
	
	private void calculations() {
		
		xOffset = 2 * halfXOffset;
		yOffset = 2 * halfYOffset;
		
		elr = (int)Math.pow(2, log2EdgeLengthRelation);
		
		triLength = (((lengthInHex + 1) * 2)-1)*elr + 2*xOffset + 1;
		triWidth  = (widthInHex*3/2 + 1)*elr + 2*yOffset;
		
	}
	
}
