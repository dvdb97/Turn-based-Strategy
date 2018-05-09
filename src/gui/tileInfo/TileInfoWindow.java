package gui.tileInfo;

import elements.TextBox;
import elements.containers.GUIWindow;
import elements.fundamental.GUILabeledElement;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;
import world.gameBoard.Tile;

public class TileInfoWindow extends GUIWindow {
	
	private Tile tile;
	
	private GUILabeledElement test;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(new GUIQuad(), new Vector4f(0.5f, 0.5f, 0.5f, 1f), -0.9f, 0.9f, 0.4f, 0.9f);
		
		test = new TextBox(new Vector4f(0.5f, 0.7f, 0.5f, 1f), 0.2f, -0.2f, 0.6f, 0.2f, "test");
		
		addChild(test);
		
	}
	
	
	//************************ get & set *************************************
	public void setTile(Tile tile) {
		this.tile = tile;
		test.setLabel("index: " + tile.getIndex());
	}
	
}
