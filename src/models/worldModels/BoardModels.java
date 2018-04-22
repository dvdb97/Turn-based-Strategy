package models.worldModels;

import assets.light.LightSource;
import assets.material.Material;
import assets.meshes.fileLoaders.OBJ_FileLoader;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Illuminated_Model;
import assets.textures.Texture2D;
import graphics.matrices.Matrices;
import interaction.PlayerCamera;
import interaction.TileSelecter;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import models.TerrainCol;
import models.seeds.ColorFunction;
import rendering.RenderEngine;
import rendering.lightRenderer.LightRenderer;
import rendering.matrices.transformation.TransformationMatrix;
import rendering.shaders.ShaderManager;
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
	
	private Illuminated_Model shadowTest;
	
	//matrices
	private TransformationMatrix boardModelMatrix;
	
	//others
	private static Material mapMaterial;
	
	private static LightSource sun;
	private static Vector3f ambientLight;
	
	private static Matrix44f lightViewMatrix;
	
	private static Color hoveredTileColor;
	
	private static Color selectedTileColor;
	
	private Vertex[] vertices;
	
	private float t = 0.0f;
	
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
		
		lengthInTiles = tileBorders.getLength();
		widthInTiles = tileBorders.getWidth();
		
		hardCode();
		
		createVertexArray();
		
		boardModelMatrix = new TransformationMatrix();
		
	}
	
	//*****************************************
	
	
	private void hardCode() {
		
		//TODO: no hard coding!
		mapMaterial = new Material(new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1.0f, 1.0f, 1.0f), 1f);
		
		sun = new LightSource(new Vector3f(-1f, 0f, -1f), new Vector3f(0.5f, 0.5f, 0.3f));
		
		ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
		
		lightViewMatrix = new TransformationMatrix(new Vector3f(1f, 1f, 1f), sun.getDirection(), 1f);
		
		hoveredTileColor = new Color(1f, 1f, 0f, 1f);
		
		selectedTileColor = new Color(1f, 0f, 0f, 1f);
		
		//TODO: Temp
		shadowTest = OBJ_FileLoader.loadOBJ_File("res/models/Suzanne.obj", mapMaterial, new Color(1.0f, 0.0f, 0.0f, 1.0f));
		shadowTest.setTransformationMatrix(boardModelMatrix);
		shadowTest.setMaterial(mapMaterial);
		TextureRenderer.init();
		
	}

	private void createVertexArray() {
		
		Vector3f[] positions = terrain.getPosArray();
		ColorFunction terrainCol = new TerrainCol();
		
		vertices = new Vertex[positions.length];
		for (int v=0; v<vertices.length; v++) {
			vertices[v] = new Vertex(positions[v], terrainCol.color(0,0,positions[v].getC()));
		}
		
	}

	
	
	
	//***************************** render ********************************
	
	/**
	 * renders the game board models
	 */
	public void render() {
		
		renderTerrain();
		
		renderSelectedTile();
		
		renderHoveredTile();
		
		renderBordersSeaCOS();
		
	}
	
	//*********************************
	
	
	private void renderTerrain() {
		
		LightRenderer.render(shadowTest, boardModelMatrix, sun, ambientLight, PlayerCamera.getCamera(), true);
		
		sun.setDirection(new Vector3f(cos(t), 0.3f, sin(t)));
		
		t += PI / 120; 
		
	}
	
	private void renderBordersSeaCOS() {
		
		tileBorders.displayAll();
		
		ShaderManager.useShader(boardModelMatrix, PlayerCamera.getViewMatrix(), Matrices.getPerspectiveProjectionMatrix(), false, null);
		
		RenderEngine.render(tileBorders, null);
		
		RenderEngine.render(sea, null);
		
		RenderEngine.render(coSystem, null);
		
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
	public Vertex[] getVertices() {
		
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
	
}
