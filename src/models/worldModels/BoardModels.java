package models.worldModels;

import assets.light.DirectionalLight;
import assets.scene.Scene;
import interaction.PlayerCamera;
import interaction.TileSelecter;
import mapModes.MapMode;
import math.vectors.Vector3f;
import rendering.matrices.transformation.TransformationMatrix;

public class BoardModels {
	
	//measurements
	private int lengthInTiles;
	private int widthInTiles;
	
	//models
	private TriangleGrid terrain;
	
	private HexagonBorderGrid tileBorders;
	
	private TriangleGrid sea;
	
	private HexagonGrid hex;
	
	//matrices
	private TransformationMatrix boardModelMatrix;
	
	//others
	private static DirectionalLight sun;
	
	private static Scene scene;
	
	//***************************** constructor ********************************
	
	/**
	 * 
	 * @param terrain
	 * @param tileBorders
	 * @param sea
	 * @param coSystem
	 */
	public BoardModels(TriangleGrid terrain, HexagonBorderGrid tileBorders, TriangleGrid sea,
			HexagonGrid hex) {
		
		this.terrain = terrain;
		this.tileBorders = tileBorders;
		this.sea = sea;
		
		this.hex = hex;
		
		lengthInTiles = tileBorders.getLength();
		widthInTiles = tileBorders.getWidth();
		
		hardCode();
		
		boardModelMatrix = new TransformationMatrix();
		
	}
	
	//*****************************************
	
	
	private void hardCode() {
		
		//TODO: no hard coding!
		sun = new DirectionalLight(new Vector3f(0.2f, 0.2f, -1f), new Vector3f(0.5f, 0.5f, 0.3f), 4000, 4000);
		sun.fitToBoundingBox(terrain);
		
	//	hoveredTileColor = new Color(1f, 1f, 0f, 1f);
		
	//	selectedTileColor = new Color(1f, 0f, 0f, 1f);
		
		scene = new Scene(PlayerCamera.getCamera(), sun, null);
	}	
	
	//***************************** render ********************************
	
	/**
	 * renders the game board models
	 */
	public void render() {
		
		renderTerrain();
		
		renderBordersSeaCOS();
		
		renderHoveredTile();
		
		renderSelectedTile();
		
	}
	
	//*********************************
	
	private void renderTerrain() {
		
		terrain.render(scene);
		
	}
	
	private void renderBordersSeaCOS() {
		
		tileBorders.displayAll();

		tileBorders.render(scene);
		
		sea.render(scene);
		
		hex.render(scene);
		
	}
	
	private void renderHoveredTile() {
		
		tileBorders.display(TileSelecter.getHoveredTileIndex());

		tileBorders.render(scene);
		
	}
	
	private void renderSelectedTile() {
		
		tileBorders.display(TileSelecter.getSelectedTileIndex());
		
		tileBorders.render(scene);
		
	}
	
	
	
	//**************************** get *************************************
		
	/**
	 * @return the game board's length in tiles
	 */
	public int getLength() {
		return lengthInTiles;
	}
	
	/**
	 * @return the game board's width in tiles
	 */
	public int getWidth() {
		return widthInTiles;
	}
	
	//**************************** set ***************************************
	
	public void setHexColor(MapMode mapMode) {
		hex.setColor(mapMode);
	}
	
}
