package models.worldModels;

import java.awt.List;

import assets.light.LightSource;
import assets.material.Material;
import assets.meshes.geometry.Vertex;
import graphics.Camera;
import graphics.matrices.Matrices;
import graphics.matrices.TransformationMatrix;
import graphics.shaders.ShaderManager;
import interaction.CameraOperator;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import models.TerrainCol;
import models.seeds.ColorFunction;
import rendering.RenderEngine;
import visualize.CoordinateSystem;
import visualize.FontTest;

public class BoardModels {
	
	//measurements
	private int lengthInHexagons;
	private int widthInHexagons;
	
	private int xOffset;
	private int yOffset;
	private int edgeLengthRelation;
	
	//models
	private TriangleGrid terrain;
	
	private HexagonBorderGrid tilBorders;
	
	private TriangleGrid sea;
	
	private CoordinateSystem coSystem;
	
	//matrices
	private TransformationMatrix boardMatrix;
	
	//others
	private static Material mapMaterial;
	
	private static LightSource sun;
	private static Vector3f ambientLight;
	
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
		this.tilBorders = tileBorders;
		this.sea = sea;
		this.coSystem = coSystem;
		
		//TODO: no hard coding!
		mapMaterial = new Material(new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.1f, 0.1f, 0.1f), 1f);
		
		sun = new LightSource(new Vector3f(-0.3f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.3f));
		ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
		
		createVertexArray();
		
	}

	
	//***************************** render ******************************
	
	/**
	 * renders the game board models
	 */
	public void render() {
		
		//render terrain
		ShaderManager.useLightShader(boardMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), Camera.getPosition(), sun, ambientLight, mapMaterial);
		
		RenderEngine.draw(terrain, null);
		
		ShaderManager.disableLightShader();
		
		
		//render hexagon borders, sea, and co-system
		ShaderManager.useShader(boardMatrix, CameraOperator.getViewMatrix(), Matrices.getProjectionMatrix(), false, null);
		
		RenderEngine.draw(tilBorders, null);
		
		RenderEngine.draw(sea, null);
		
		RenderEngine.draw(coSystem, null);
		
		ShaderManager.disableShader();
		
		
		//render font-test
		ShaderManager.useFontShader(new Matrix44f());
		
		RenderEngine.draw(FontTest.getModel(), FontTest.getTexture());
		
		ShaderManager.disableTexturedMeshShader();
		
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
		
		return tilBorders.getHexCenterIndices();
		
	}
}
