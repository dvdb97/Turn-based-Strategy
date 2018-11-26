package models.worldModels;

import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.VertexLegacy;
import assets.shaders.ShaderManager;
import assets.textures.Texture2D;
import graphics.matrices.Matrices;
import interaction.PlayerCamera;
import interaction.TileSelecter;
import math.matrices.Matrix44f;
import mapModes.MapMode;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import models.TerrainCol;
import models.seeds.ColorFunction;
import rendering.RenderEngine;
import rendering.matrices.transformation.TransformationMatrix;
import testing.TextureRenderer;
import visualize.CoordinateSystem;
import world.WorldManager;

import static math.Trigonometry.*;

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
	
	private static DirectionalLight sun;
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
		mapMaterial = new Material(new Color(0f, 0f, 0f, 0f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1.0f, 1.0f, 1.0f), 1f);
		
		sun = new DirectionalLight(new Vector3f(0.5f, 0.5f, 0.3f));
		
		ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
		
		hoveredTileColor = new Color(1f, 1f, 0f, 1f);
		
		selectedTileColor = new Color(1f, 0f, 0f, 1f);
		
	}

	private void createVertexArray() {
		
		Vector3f[] positions = terrain.getPosArray();
		ColorFunction terrainCol = new TerrainCol();
		
		vertices = new VertexLegacy[positions.length];
		for (int v=0; v<vertices.length; v++) {
			vertices[v] = new VertexLegacy(positions[v], terrainCol.color(0,0,positions[v].getC()));
		}
		
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
	
	
	private void renderBordersSeaCOS() {
		
		tileBorders.displayAll();
		
		ShaderManager.useShader(boardModelMatrix, PlayerCamera.getViewMatrix(), Matrices.getPerspectiveProjectionMatrix(), false, null);
		
		RenderEngine.render(tileBorders, null);
		
		RenderEngine.render(sea, null);
		
		RenderEngine.render(coSystem, null);
		
		RenderEngine.draw(hex, null);
		
		ShaderManager.disableShader();
		
	}
	
	private void renderHoveredTile() {
		
		tileBorders.display(TileSelecter.getHoveredTileIndex());
		
		ShaderManager.useShader(boardModelMatrix, PlayerCamera.getViewMatrix(), Matrices.getPerspectiveProjectionMatrix(), true, hoveredTileColor);
		
		RenderEngine.render(tileBorders, null);
		
		ShaderManager.disableShader();
		
	}
	
	private void renderSelectedTile() {
		
		tileBorders.display(TileSelecter.getSelectedTileIndex());
		
		ShaderManager.useShader(boardModelMatrix, PlayerCamera.getViewMatrix(), Matrices.getPerspectiveProjectionMatrix(), true, selectedTileColor);
		
		RenderEngine.render(tileBorders, null);
		
		ShaderManager.disableShader();
		
	}
	
	
	
	//**************************** get *************************************
	
	/**
	 * @return an array containing all terrains vertices
	 */
	public VertexLegacy[] getVertices() {
		
		return vertices;
		
	}
	
	/**
	 * @return an array containing the vertex-array-indices of all tile's center
	 */
	public int[] getTileCenters() {
		
		return tileBorders.getHexCenterIndices();
		
	}
	
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
