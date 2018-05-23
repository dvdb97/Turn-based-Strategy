package gui.tileInfo;

import work_in_progress.test.TabMenu;
import world.gameBoard.Tile;
import assets.meshes.geometry.Color;
import container.GUIWindow;
import dataType.GUIElementMatrix;
import output.TextBox;
import rendering.shapes.GUIQuad;

import static utils.ColorPalette.*;

public class TileInfoWindow extends GUIWindow {
	
	private Tile tile;
	
	private TextBox infoText;
	
	//private GUIToggleButton button;
	
	private TabMenu tabMenu;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(new GUIQuad(), GREEN, new GUIElementMatrix(-0.9f, 0.2f, 0.4f, 0.9f));
		
		infoText = new TextBox(new GUIQuad(), GREEN, new GUIElementMatrix(0.05f, -0.5f, 0.9f, 0.45f), "index");
		
		
		
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
		children.add(infoText);
	}
	
	private void enable() {
	//	children.add(infoText);
	}
	
	private void disable() {
	//	children.remove(infoText);
	}

	
	
	//************************ get & set *************************************
	public void setTile(Tile tile) {
		this.tile = tile;
		infoText.setLabel(TileInfoStringIssuer.getTileInfoString(tile));
		if(tile.isWater()) {
			infoText.setColor(BLUE);
		} else {
			infoText.setColor(GREEN);
		}
	}
	
}
