package models.gameboard;

import assets.light.DirectionalLight;
import assets.meshes.Transformable;
import assets.meshes.geometry.Color;
import assets.scene.Scene;
import interaction.PlayerCamera;
import interaction.TileSelecter;
import mapModes.MapMode;
import math.vectors.Vector3f;
import models.meeples.CityModel;
import rendering.matrices.transformation.TransformationMatrix;

public class GameBoardModel {
	
	//measurements
	private int lengthInTiles;
	private int widthInTiles;
	
	//models
	private TriangleGrid terrain;
	
	private HexagonBorderGrid tileBorders;
	
	private TriangleGrid sea;
	
	private HexagonGrid hex;
	
	public Transformable transformable;
	
	//others
	private static DirectionalLight sun;
	
	private static Scene scene;
	
	
	private CityModel TEST;
	
	//***************************** constructor ********************************
	
	/**
	 * 
	 * @param terrain
	 * @param tileBorders
	 * @param sea
	 * @param coSystem
	 */
	public GameBoardModel(TriangleGrid terrain, HexagonBorderGrid tileBorders, TriangleGrid sea,
			HexagonGrid hex) {
		
		transformable = new Transformable();
		
		this.terrain = terrain;
		this.tileBorders = tileBorders;
		this.sea = sea;
		this.hex = hex;
		
		terrain.transformable.setParent(transformable);
		tileBorders.transformable.setParent(transformable);
		sea.transformable.setParent(transformable);
		hex.transformable.setParent(transformable);
		
		this.TEST = new CityModel(transformable);
		TEST.transformable.setScaling(0.3f);
		
		lengthInTiles = tileBorders.getLength();
		widthInTiles = tileBorders.getWidth();
		
		hardCode();
		
		transformable = new Transformable();
		
	}
	
	//*****************************************
	
	
	private void hardCode() {
		
		//TODO: no hard coding!
		sun = new DirectionalLight(new Vector3f(0.2f, 0.2f, -1f), new Vector3f(0.5f, 0.5f, 0.3f), 4000, 4000);
		sun.fitToBoundingBox(terrain);
		
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
		
		TEST.render(scene);
		
	}
	
	private void renderBordersSeaCOS() {
		
		tileBorders.displayAll();

		tileBorders.render(scene);
		
		sea.render(scene);
		
		hex.render(scene);
		
	}
	
	private void renderHoveredTile() {
		
		tileBorders.display(TileSelecter.getHoveredTileIndex());

		tileBorders.render(scene, new Color(1f, 1f, 0f, 1f));
		
	}
	
	private void renderSelectedTile() {
		
		tileBorders.display(TileSelecter.getSelectedTileIndex());
		
		tileBorders.render(scene, new Color(1f, 0f, 0f, 1f));
		
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
