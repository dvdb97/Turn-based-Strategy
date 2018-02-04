package models.worldModels;

import assets.material.StandardMaterial;
import assets.meshes.geometry.Color;
import math.vectors.Vector3f;
import models.TerrainCol;
import models.seeds.ElevationMap;
import models.seeds.SuperGrid;
import visualize.CoordinateSystem;

public class ModelCreater {
	
	//********************************* fields *********************************
	
	//measurements
	private int lengthInHex;
	private int widthInHex;
	
	private int triLength;
	private int triWidth;
	
	
	//hard code
	private final int xOffset = 2;
	private final int yOffset = 2;
	
	private final int elr = 2;			//edgeLengthRelation
	
	private static float triEdgeLength = 0.1f;
	
	//seed
	private ElevationMap terrainData;
	
	private SuperGrid superGrid;
	
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
	 * @param widthInHexagons width of the board in hexagons
	 */
	public ModelCreater(int lengthInHexagons, int widthInHexagons) {
		
		lengthInHex = lengthInHexagons;
		widthInHex = widthInHexagons;
		
		calculations();
		
	}
	
	
	//********************************** ergon *********************************
	
	/**
	 * creates all models needed to render the game board.
	 * namely: terrain, sea, tile borders and an coordinate system
	 */
	public BoardModels createModels() {
		
		createSuperGrid();
		createTerrain();
		createTileBorders();
		createSea();
		createCoSystem();
		
		return new BoardModels(terrain, tileBorders, sea, coSystem);
		
	}
	
	//********************************* prime methods **************************
	
	private void createSuperGrid() {
		
		terrainData = new ElevationMap(triLength, triWidth);
		superGrid = new SuperGrid(lengthInHex, widthInHex, xOffset, yOffset, triEdgeLength, elr, terrainData.getElevationArray());
		
	}
	
	private void createTerrain() {
		
		terrain = new TriangleGrid(triEdgeLength, terrainData.getElevationArray(), new TerrainCol(), new StandardMaterial());
		
	}
	
	private void createTileBorders() {
		
		tileBorders = new HexagonBorderGrid(terrain, xOffset, yOffset, elr);
		
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
		
		triLength = (lengthInHex*2 + 2)*elr + 2*xOffset;
		triWidth  = (widthInHex*3/2 + 1)*elr + 1 + 2*yOffset;
		
		
	//	triLength = (lengthInHex*2 + 1)*elr + 2*xOffset + 1;
	//	triWidth  = (widthInHex*3/2 + 1)*elr + 2*yOffset;
		
	}
	
	//******************************* get ******************************************
	
	public SuperGrid getSuperGrid() {
		
		return superGrid;
		
	}
}
