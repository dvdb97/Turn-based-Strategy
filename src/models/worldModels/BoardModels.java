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
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import models.TerrainCol;
import models.seeds.ColorFunction;
import rendering.RenderEngine;
import visualize.CoordinateSystem;

public class BoardModels {
	
	//measurements
	private int lengthInHexagons;
	private int widthInHexagons;
	
	//models
	private TriangleGrid terrain;
	
	private HexagonBorderGrid tileBorders;
	
	private TriangleGrid sea;
	
	private CoordinateSystem coSystem;
	
	//matrices
	private TransformationMatrix boardModelMatrix;
	
	//others
	private static Material mapMaterial;
	
	private static LightSource sun;
	private static Vector3f ambientLight;
	
	private static Color hoveredTileColor;
	
	private static Color selectedTileColor;
	
	private Vertex[] vertices;
	
	//***************************** constructor ********************************
	
	/**
	 * 
	 * @param terrain
	 * @param tileBorders
	 * @param sea
	 * @param coSystem
	 */
	public BoardModels(TriangleGrid terrain, HexagonBorderGrid tileBorders, TriangleGrid sea,
			CoordinateSystem coSystem) {
		
		this.terrain = terrain;
		this.tileBorders = tileBorders;
		this.sea = sea;
		this.coSystem = coSystem;
		
		hardCode();
		
		createVertexArray();
		
		boardModelMatrix = new TransformationMatrix();
		
	}

	
	//***************************** render ********************************
	
	/**
	 * renders the game board models
	 */
	public void render() {
		
		//render terrain
		ShaderManager.useLightShader(boardModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), Camera.getPosition(), sun, ambientLight, mapMaterial);
		
		RenderEngine.draw(terrain, null);
		
		ShaderManager.disableLightShader();
		
		renderSelectedTile();
		
		renderHoveredTile();
		
		tileBorders.displayAll();
		
		//render hexagon borders, sea, and co-system
		ShaderManager.useShader(boardModelMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), false, null);
		
		RenderEngine.draw(tileBorders, null);
		
		RenderEngine.draw(sea, null);
		
		RenderEngine.draw(coSystem, null);
		
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
	
	//**********************************************************************
	
	private void createVertexArray() {
		
		Vector3f[] positions = terrain.getPosArray();
		ColorFunction terrainCol = new TerrainCol();
		
		vertices = new Vertex[positions.length];
		for (int v=0; v<vertices.length; v++) {
			vertices[v] = new Vertex(positions[v], terrainCol.color(0,0,positions[v].getC()));
		}
		
	}
	
	private void hardCode() {
		
		//TODO: no hard coding!
		mapMaterial = new Material(new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.1f, 0.1f, 0.1f), 1f);
		
		sun = new LightSource(new Vector3f(-0.3f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.3f));
		ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
		
		hoveredTileColor = new Color(1f, 1f, 0f, 1f);
		
		selectedTileColor = new Color(1f, 0f, 0f, 1f);
		
	}
	
	//**************************** get *************************************
	
	/**
	 * @return an array containing all terrains vertices
	 */
	public Vertex[] getVertices() {
		
		return vertices;
		
	}
	
	/**
	 * @return an array containing the vertex-array-indices of all tile's center
	 */
	public int[] getTileCenters() {
		
		return tileBorders.getHexCenterIndices();
		
	}
}
