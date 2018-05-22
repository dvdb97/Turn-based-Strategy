package gui.tileInfo;

import elements.input.buttons.GUIToggleButton;
import work_in_progress.GUIElementMatrix;
import work_in_progress.GUIWindow;
import work_in_progress.Quad;
import work_in_progress.test.TabMenu;
import world.gameBoard.Tile;

import assets.meshes.geometry.Color;

public class TileInfoWindow extends GUIWindow {
	
	private static Color GREEN  = new Color(0.7f, 0.9f, 0.7f, 1f);
	private static Color BLUE   = new Color(0.0f, 0.6f, 0.8f, 1f);
	private static Color GRAY   = new Color(0.5f, 0.5f, 0.5f, 1f);
	private static Color YELLOW = new Color(0.7f, 0.7f, 0.5f, 1f);
	private static Color WHITE  = new Color(1f, 1f, 1f, 1f);
	private static Color BLACK  = new Color(0f, 0f, 0f, 1f);
	
	private static Color TURQUOISE       = new Color( 85, 221, 224, 255);
	private static Color TEAL_BLUE       = new Color( 51, 101, 138, 255);
	private static Color DARK_SLATE_GRAY = new Color( 47,  72,  88, 255);
	private static Color SAFFRON         = new Color(246, 174,  45, 255);
	private static Color GIANTS_ORANGE   = new Color(242, 100,  25, 255);
	
	
	private Tile tile;
	
	//private GUITextField infoText;
	
	private GUIToggleButton button;
	
	private TabMenu tabMenu;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(new Quad(), GREEN, new GUIElementMatrix(0f, 0.2f, 0.4f, 0.9f));
		
	//	infoText = new GUITextField(GREEN.toVector4f(), 0.1f, -0.25f, 0.8f, 0.65f, "index");
		
	/*	button = new GUIToggleButton(0.1f, -0.1f, 0.8f, 0.1f);
		button.setEnableFunction(  (element) -> enable()  );
		button.setDisableFunction( (element) -> disable() );
		addChild(button);*/
		
		tabMenu = new TabMenu(new Quad(), DARK_SLATE_GRAY, new GUIElementMatrix(0.1f, -0.1f, 0.8f, 0.8f));
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
		this.tile = tile;
	//	infoText.setLabel(getTileInfoString(tile));
		if(tile.isWater()) {
	//		infoText.setColor(BLUE.toVector4f());
		} else {
	//		infoText.setColor(GREEN.toVector4f());
		}
	}
	
}
