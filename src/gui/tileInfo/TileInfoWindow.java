package gui.tileInfo;

import elements.displays.GUITextField;
import elements.input.buttons.GUIToggleButton;
import elements.input.menus.GUITabMenu;
import rendering.shapes.implemented.GUIQuad;
import work_in_progress.GUIElementMatrix;
import work_in_progress.GUIWindow;
import work_in_progress.Quad;
import world.gameBoard.Tile;

import static gui.tileInfo.TileInfoStringIssuer.getTileInfoString;

import assets.meshes.geometry.Color;

public class TileInfoWindow extends GUIWindow {
	
	private static Color GREEN = new Color(0.7f, 0.9f, 0.7f, 1f);
	private static Color BLUE = new Color(0.0f, 0.6f, 0.8f, 1f);
	private static Color GREY = new Color(0.5f, 0.5f, 0.5f, 1f);
	private static Color YELLOW = new Color(0.7f, 0.7f, 0.5f, 1f);
	private static Color WHITE = new Color(1f, 1f, 1f, 1f);
	private static Color BLACK = new Color(0f, 0f, 0f, 1f);
	
	private Tile tile;
	
	private GUITextField infoText;
	
	private GUIToggleButton button;
	
	private GUITabMenu tabMenu;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(new Quad(), GREY, new GUIElementMatrix(-0.9f, 0.9f, 0.4f, 0.9f));
		
		infoText = new GUITextField(GREEN.toVector4f(), 0.1f, -0.25f, 0.8f, 0.65f, "index");
		
	/*	button = new GUIToggleButton(0.1f, -0.1f, 0.8f, 0.1f);
		button.setEnableFunction(  (element) -> enable()  );
		button.setDisableFunction( (element) -> disable() );
		addChild(button);*/
		
		tabMenu = new GUITabMenu(new GUIQuad(), YELLOW.toVector4f(), 0.1f, -0.1f, 0.8f, 0.8f);
	//	children.add(tabMenu);
		
		tabMenu.addTab(BLUE.toVector4f());
		tabMenu.addTab(GREEN.toVector4f());
		tabMenu.addTab(BLACK.toVector4f());
		tabMenu.addTab(WHITE.toVector4f());
		
	}
	
	private void enable() {
	//	children.add(infoText);
	}
	
	private void disable() {
		children.remove(infoText);
	}

	
	
	//************************ get & set *************************************
	public void setTile(Tile tile) {
		this.tile = tile;
		infoText.setLabel(getTileInfoString(tile));
		if(tile.isWater()) {
			infoText.setColor(BLUE.toVector4f());
		} else {
			infoText.setColor(GREEN.toVector4f());
		}
	}
	
}
