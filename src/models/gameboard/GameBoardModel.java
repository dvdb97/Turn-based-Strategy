package models.gameboard;

import assets.meshes.Transformable;
import assets.meshes.geometry.Color;
import assets.meshes.specialized.Quad;
import assets.scene.Scene;
import interaction.TileSelecter;
import mapModes.MapMode;
import math.vectors.Vector3f;
import rendering.Renderer;
import rendering.SceneManager;

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
	
	public Quad quad;
	
	
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
		this.quad = new Quad();
		
		Renderer.addMeshToRenderQueue(terrain);
		Renderer.addMeshToRenderQueue(tileBorders);
		Renderer.addMeshToRenderQueue(sea);
		Renderer.addMeshToRenderQueue(hex);
		
		terrain.transformable.setParent(transformable);
		tileBorders.transformable.setParent(transformable);
		sea.transformable.setParent(transformable);
		hex.transformable.setParent(transformable);
		
		lengthInTiles = tileBorders.getLength();
		widthInTiles = tileBorders.getWidth();
		
		transformable = new Transformable();
		
	}

	
	//***************************** render ********************************
	
	/**
	 * renders the game board models
	 */
	public void render() {
		
		computeShadows();
		
		renderTerrain();
		
		renderBordersSeaCOS();
		
		renderHoveredTile();
		
		renderSelectedTile();
		
	}
	
	//*********************************
	
	
	private void computeShadows() {
		Scene scene = SceneManager.getScene();
		
		scene.getLightSource().startShadowMapPass();
		scene.getLightSource().passToShadowMap(terrain);
		scene.getLightSource().endShadowMapPass();
		
		quad.setTexture(scene.getLightSource().getShadowMap().getDepthTexture());
		quad.getTransformable().setScaling(0.25f);
		quad.getTransformable().setTranslation(new Vector3f(0.75f, 0.75f, -1f));
		quad.useTextureColor();
		quad.render(scene);
	}
	
	
	private void renderTerrain() {
		Scene scene = SceneManager.getScene();
		
		terrain.render(scene);
	}
	
	private void renderBordersSeaCOS() {
		Scene scene = SceneManager.getScene();
		
		tileBorders.displayAll();

		tileBorders.render(scene);
		
		sea.render(scene);
		
		hex.render(scene);
		
	}
	
	private void renderHoveredTile() {
		Scene scene = SceneManager.getScene();
		
		tileBorders.display(TileSelecter.getHoveredTileIndex());

		tileBorders.render(scene, new Color(1f, 1f, 0f, 1f));
		
	}
	
	private void renderSelectedTile() {
		Scene scene = SceneManager.getScene();
		
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
