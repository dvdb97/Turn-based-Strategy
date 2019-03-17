package gui.tileInfo;

import world.WorldManager;
import world.gameBoard.Tile;
import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import stbFont.TTFBox;
import work_in_progress.TestPushButton;

import static utils.ColorPalette.*;

import java.util.ArrayList;

import container.RadioButtons;
import container.Tab;
import container.TabMenu;
import core.Application;

public class TileInfoWindow extends GUIWindow {
	
	private Tile tile;
	
	private TTFBox infoText;
	
	//private GUIToggleButton button;
	
	private TabMenu tabMenu;
	private ArrayList<Tab> tabList;
	
	private GUITexture waterTex;
	private GUITexture grassTex;
	
	int counter = 0;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(GREEN_1, new GUIElementMatrix(-0.9f, 0.2f, 0.4f, 0.9f));
		
		infoText = new TTFBox(0.05f, -0.05f, 0.05f, "index", GIANTS_ORANGE);
		
		float textureHeight = 0.4f/0.9f/3f*Application.getWindow().getAspectRatio();
		waterTex = new GUITexture("res/PH_Sea.png", new GUIElementMatrix(0, 0, 1, textureHeight));
		grassTex = new GUITexture("res/PH_Grassland.png", new GUIElementMatrix(0, 0, 1, textureHeight));
		
		children.add(waterTex);
		
		tabMenu = new TabMenu(DARK_SLATE_GRAY, 0.1f, new GUIElementMatrix(0f, -textureHeight, 1f, 1 - textureHeight));
		children.add(tabMenu);
		tabList = new ArrayList<>(4);
		tabMenu.addTab(TURQUOISE, "overview", tabList);
		tabList.get(0).addElement(new TTFBox(0, 0, 0.05f, "turquoise fucks!", TURQUOISE));
		tabMenu.addTab(GIANTS_ORANGE, "geopgraphy", tabList);
		tabList.get(1).addElement(infoText);
		//**************************************************************
		tabMenu.addTab(TEAL_BLUE, "buildings", tabList);
		TTFBox ttfBox = new TTFBox(0.1f, -0.1f, 0.05f, Integer.toString(counter), BLACK);
		tabList.get(2).addElement(ttfBox);
		TestPushButton button = new TestPushButton(GREEN_1, new GUIElementMatrix(0.1f, -0.2f, 0.3f, 0.1f));
		tabList.get(2).addElement(button);
		button.setFunction((e) -> ttfBox.changeTextTo(Integer.toString(++counter)));
		//**************************************************************
		tabMenu.addTab(SAFFRON, "map mode", tabList);
		RadioButtons rb = new RadioButtons(WHITE, 0.05f, new GUIElementMatrix(0.1f, -0.1f, 0.8f, 0.5f));
		tabList.get(3).addElement(rb);
		rb.addButton("zero", SAFFRON, (e) -> WorldManager.changeMM(0));
		rb.addButton("one", BLACK, (e) -> WorldManager.changeMM(1));
		rb.addButton("two", RED, (e) -> WorldManager.changeMM(2));
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
			if (!children.contains(waterTex)) {
				children.remove(grassTex);
				children.add(waterTex);
			}
		} else {
			color = GREEN_1;
			if (!children.contains(grassTex)) {
				children.remove(waterTex);
				children.add(grassTex);
			}

		}
	}
	
}
