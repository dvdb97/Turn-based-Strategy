package gui.tileInfo;

import elements.TextBox;
import elements.containers.GUIWindow;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;
import utils.Algebra;
import world.gameBoard.Tile;

public class TileInfoWindow extends GUIWindow {
	
	private static Vector4f GREEN = new Vector4f(0.7f, 0.9f, 0.7f, 1f);
	private static Vector4f BLUE = new Vector4f(0.0f, 0.6f, 0.8f, 1f);
	private static Vector4f GREY = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
	
	private Tile tile;
	
	private TextBox test;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(new GUIQuad(), GREY, -0.9f, 0.9f, 0.4f, 0.9f);
		
		test = new TextBox(GREEN, 0.1f, -0.1f, 0.8f, 0.8f, "index");
		
		addChild(test);
		
	}
	
	
	//************************ get & set *************************************
	public void setTile(Tile tile) {
		this.tile = tile;
		test.setLabel("index: " + tile.getIndex() + "\n "
				+ "fertility: " + Algebra.round(tile.getFertility().getValue(), 2));
		if(tile.isWater()) {
			test.setColor(BLUE);
		} else {
			test.setColor(GREEN);
		}
	}
	
}
