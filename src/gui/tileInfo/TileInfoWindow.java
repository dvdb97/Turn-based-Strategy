package gui.tileInfo;

import elements.GUITextField;
import elements.containers.GUIWindow;
import elements.input.GUIToggleButton;
import gui.test.TestToggleButton;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;
import world.gameBoard.Tile;

import static gui.tileInfo.TileInfoStringIssuer.getTileInfoString;

public class TileInfoWindow extends GUIWindow {
	
	private static Vector4f GREEN = new Vector4f(0.7f, 0.9f, 0.7f, 1f);
	private static Vector4f BLUE = new Vector4f(0.0f, 0.6f, 0.8f, 1f);
	private static Vector4f GREY = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
	
	private Tile tile;
	
	private GUITextField infoText;
	
	private GUIToggleButton button;
	
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(new GUIQuad(), GREY, -0.9f, 0.9f, 0.4f, 0.9f);
		
		infoText = new GUITextField(GREEN, 0.1f, -0.25f, 0.8f, 0.65f, "index");
		
		button = new GUIToggleButton(0.1f, -0.1f, 0.8f, 0.1f);
		button.setEnableFunction(  (element) -> enable()  );
		button.setDisableFunction( (element) -> disable() );
		addChild(button);
		
	}
	
	public void enable() {
		addChild(infoText);
	}
	
	public void disable() {
		removeChild(infoText);
	}

	
	
	//************************ get & set *************************************
	public void setTile(Tile tile) {
		this.tile = tile;
		infoText.setLabel(getTileInfoString(tile));
		if(tile.isWater()) {
			infoText.setColor(BLUE);
		} else {
			infoText.setColor(GREEN);
		}
	}
	
}
