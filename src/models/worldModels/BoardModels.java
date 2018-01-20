package models.worldModels;

import assets.light.LightSource;
import assets.material.Material;
import graphics.Camera;
import graphics.matrices.Matrices;
import graphics.matrices.TransformationMatrix;
import graphics.shaders.ShaderManager;
import interaction.CameraOperator;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
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

	
	//***************************** constructor ********************************
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
		
	}

	
	//***************************** render ******************************
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
		
		
		//render fonttest
		ShaderManager.useFontShader(new Matrix44f());
		
		RenderEngine.draw(FontTest.getModel(), FontTest.getTexture());
		
		ShaderManager.disableTexturedMeshShader();
		
	}
	
	
}
