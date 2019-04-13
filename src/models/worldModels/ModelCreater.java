package models.worldModels;

import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.geometry.Color;
import math.vectors.Vector3f;
import models.TerrainCol;
import models.seeds.ElevationMap;
import models.seeds.SuperGrid;

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
	
	private HexagonGrid hexagons;
	
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
		createHexagons();
		
		return new BoardModels(terrain, tileBorders, sea, hexagons);
		
	}
	
	//********************************* prime methods **************************
	
	private void createSuperGrid() {
		
		terrainData = new ElevationMap(triLength, triWidth);
		superGrid = new SuperGrid(lengthInHex, widthInHex, xOffset, yOffset, triEdgeLength, elr, terrainData.getElevationArray());
		
	}
	
	private void createTerrain() {
		
		Material mat = new Material(Color.WHITE, Vector3f.ZERO, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.2f, 0.2f, 0.2f), 256f);
		terrain = new TriangleGrid(superGrid, new TerrainCol(), mat, false);
		
	}
	
	private void createTileBorders() {
		
		tileBorders = new HexagonBorderGrid(superGrid, new Material(new Color(0f, 0f, 0f, 1f)));
	}
	
	private void createSea() {
		
		sea = new TriangleGrid(superGrid, new Color(0, 0.2f, 0.7f, 0.7f), new StandardMaterial(), true);
		
	}
	
	private void createHexagons() {
		
		Color[] colors = randomColors(lengthInHex, widthInHex);
		hexagons = new HexagonGrid(superGrid, colors);
		
	}
	
	//******************************** calculations *******************************
	
	private void calculations() {
		
		triLength = (lengthInHex*2 + 2)*elr + 2*xOffset;
		triWidth  = (widthInHex*3/2 + 1)*elr + 1 + 2*yOffset;
				
	}
	
	private Color[] randomColors(int length, int width) {
		
		Color[] colors = new Color[length*width];
		
		for (int i=0; i<colors.length; i++) {
			colors[i] = new Color(0.2f, (float)Math.random(), (float)Math.random(), 0.1f);
		}
		
		return colors;
	}
		
	//******************************* get ******************************************
	
	public SuperGrid getSuperGrid() {
		
		return superGrid;
		
	}
}
