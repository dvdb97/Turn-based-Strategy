package gui.tileInfo;

import work_in_progress.test.TabMenu;
import world.gameBoard.Tile;
import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import output.TextBox;
import rendering.shapes.GUIQuad;
import stbFont.TTFBox;

import static utils.ColorPalette.*;

public class TileInfoWindow extends GUIWindow {
	
	private Tile tile;
	
	private TTFBox infoText;
	
	//private GUIToggleButton button;
	
	private TabMenu tabMenu;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(new GUIQuad(), GREEN_1, new GUIElementMatrix(-0.9f, 0.2f, 0.4f, 0.9f));
		
		//infoText = new TextBox(new GUIQuad(), GREEN_1, new GUIElementMatrix(0.05f, -0.5f, 0.9f, 0.45f), "index");
		infoText = new TTFBox(0.05f, -0.5f, 0.05f, "index", GIANTS_ORANGE);
		children.add(infoText);
		
	/*	button = new GUIToggleButton(0.1f, -0.1f, 0.8f, 0.1f);
		button.setEnableFunction(  (element) -> enable()  );
		button.setDisableFunction( (element) -> disable() );
		addChild(button);*/
		
		tabMenu = new TabMenu(new GUIQuad(), DARK_SLATE_GRAY, new GUIElementMatrix(0.05f, -0.05f, 0.9f, 0.4f));
		children.add(tabMenu);
		
		tabMenu.addTab(TURQUOISE);
		tabMenu.addTab(GIANTS_ORANGE);
		tabMenu.addTab(TEAL_BLUE);
		tabMenu.addTab(SAFFRON);
	}
	
	private void enable() {
	//	children.add(infoText);
	}
	
	private void disable() {
	//	children.remove(infoText);
	}

	
	
	//************************ get & set *************************************
	public void setTile(Tile tile) {
		if (this.tile == tile) {
			return;
		}
		this.tile = tile;
		infoText.changeTextTo(TileInfoStringIssuer.getTileInfoString(tile));
		if(tile.isWater()) {
			color = BLUE_1;
		} else {
			color = GREEN_1;
		}
	}
	
}
