package gui.tileInfo;

import elements.containers.GUIWindow;
import elements.displays.GUITextField;
import elements.input.buttons.GUIToggleButton;
import elements.input.menus.GUITabMenu;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;
import world.gameBoard.Tile;

import static gui.tileInfo.TileInfoStringIssuer.getTileInfoString;

public class TileInfoWindow extends GUIWindow {
	
	private static Vector4f GREEN = new Vector4f(0.7f, 0.9f, 0.7f, 1f);
	private static Vector4f BLUE = new Vector4f(0.0f, 0.6f, 0.8f, 1f);
	private static Vector4f GREY = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
	private static Vector4f YELLOW = new Vector4f(0.7f, 0.7f, 0.5f, 1f);
	private static Vector4f WHITE = new Vector4f(1f, 1f, 1f, 1f);
	private static Vector4f BLACK = new Vector4f(0f, 0f, 0f, 1f);
	
	private Tile tile;
	
	private GUITextField infoText;
	
	private GUIToggleButton button;
	
	private GUITabMenu tabMenu;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(new GUIQuad(), GREY, -0.9f, 0.9f, 0.4f, 0.9f);
		
		infoText = new GUITextField(GREEN, 0.1f, -0.25f, 0.8f, 0.65f, "index");
		
	/*	button = new GUIToggleButton(0.1f, -0.1f, 0.8f, 0.1f);
		button.setEnableFunction(  (element) -> enable()  );
		button.setDisableFunction( (element) -> disable() );
		addChild(button);*/
		
		tabMenu = new GUITabMenu(new GUIQuad(), YELLOW, 0.1f, -0.1f, 0.8f, 0.8f);
		addChild(tabMenu);
		
		tabMenu.addTab(BLUE);
		tabMenu.addTab(GREEN);
		tabMenu.addTab(BLACK);
		tabMenu.addTab(WHITE);
		
	}
	
	private void enable() {
		addChild(infoText);
	}
	
	private void disable() {
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
