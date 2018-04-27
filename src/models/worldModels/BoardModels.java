package models.worldModels;

import assets.light.LightSource;
import assets.material.Material;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import graphics.Camera;
import graphics.matrices.Matrices;
import graphics.matrices.TransformationMatrix;
import graphics.shaders.ShaderManager;
import interaction.CameraOperator;
import interaction.TileSelecter;
import mapModes.MapMode;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import models.TerrainCol;
import models.seeds.ColorFunction;
import rendering.RenderEngine;
import visualize.CoordinateSystem;
import world.WorldManager;

public class BoardModels {
	
	//measurements
	private int lengthInTiles;
	private int widthInTiles;
	
	//models
	private TriangleGrid terrain;
	
	private HexagonBorderGrid tileBorders;
	
	private TriangleGrid sea;
	
	private CoordinateSystem coSystem;
	
	private HexagonGrid hex;
	
	//matrices
	private TransformationMatrix boardModelMatrix;
	
	//others
	private static Material mapMaterial;
	
	private static LightSource sun;
	private static Vector3f ambientLight;
	
	private static Color hoveredTileColor;
	
	private static Color selectedTileColor;
	
	//***************************** constructor ********************************
	
	/**
	 * 
	 * @param terrain
	 * @param tileBorders
	 * @param sea
	 * @param coSystem
	 */
	public BoardModels(TriangleGrid terrain, HexagonBorderGrid tileBorders, TriangleGrid sea,
			CoordinateSystem coSystem, HexagonGrid hex) {
		
		this.terrain = terrain;
		this.tileBorders = tileBorders;
		this.sea = sea;
		this.coSystem = coSystem;
		
		this.hex = hex;
		
		lengthInTiles = tileBorders.getLength();
		widthInTiles = tileBorders.getWidth();
		
		hardCode();
		
		boardModelMatrix = new TransformationMatrix();
		
	}
	
	//*****************************************
	
	
	private void hardCode() {
		
		//TODO: no hard coding!
		mapMaterial = new Material(new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.1f, 0.1f, 0.1f), 1f);
		
		sun = new LightSource(new Vector3f(-0.3f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.3f));
		ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
		
		hoveredTileColor = new Color(1f, 1f, 0f, 1f);
		
		selectedTileColor = new Color(1f, 0f, 0f, 1f);
		
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
		
		ShaderManager.useLightShader(boardModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), Camera.getPosition(), sun, ambientLight, mapMaterial);
		
		RenderEngine.draw(terrain, null);
		
		ShaderManager.disableLightShader();
		
	}
	
	private void renderBordersSeaCOS() {
		
		tileBorders.displayAll();
		
		ShaderManager.useShader(boardModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), false, null);
		
		RenderEngine.draw(tileBorders, null);
		
		RenderEngine.draw(sea, null);
		
		RenderEngine.draw(coSystem, null);
		
		RenderEngine.draw(hex, null);
		
		ShaderManager.disableShader();
		
	}
	
	private void renderHoveredTile() {
		
		tileBorders.display(TileSelecter.getHoveredTileIndex());
		
		ShaderManager.useShader(boardModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), true, hoveredTileColor);
		
		RenderEngine.draw(tileBorders, null);
		
		ShaderManager.disableShader();
		
	}
	
	private void renderSelectedTile() {
		
		tileBorders.display(TileSelecter.getSelectedTileIndex());
		
		ShaderManager.useShader(boardModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), true, selectedTileColor);
		
		RenderEngine.draw(tileBorders, null);
		
		ShaderManager.disableShader();
		
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
