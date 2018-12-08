package gui.tileInfo;

import work_in_progress.test.Tab;
import work_in_progress.test.TabMenu;
import world.gameBoard.Tile;
import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import output.GUITexture;
import rendering.shapes.GUIQuad;
import stbFont.TTFBox;

import static utils.ColorPalette.*;

import java.util.ArrayList;

import core.Application;

public class TileInfoWindow extends GUIWindow {
	
	private Tile tile;
	
	private TTFBox infoText;
	
	//private GUIToggleButton button;
	
	private TabMenu tabMenu;
	private ArrayList<Tab> tabList;
	
	private GUITexture waterTex;
	private GUITexture grassTex;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super(GREEN_1, new GUIElementMatrix(-0.9f, 0.2f, 0.4f, 0.9f));
		
		infoText = new TTFBox(0.05f, -0.05f, 0.05f, "index", GIANTS_ORANGE);
		
		waterTex = new GUITexture("res/PH_Sea.png", new GUIElementMatrix(0, 0, 1, 0.4f/0.9f/3f*Application.getWindow().getProportions()));
		grassTex = new GUITexture("res/PH_Grassland.png", new GUIElementMatrix(0, 0, 1, 0.4f/0.9f/3f*Application.getWindow().getProportions()));
		
		children.add(waterTex);
		
		tabMenu = new TabMenu(DARK_SLATE_GRAY, new GUIElementMatrix(0f, -0.3f, 1f, 0.4f));
		children.add(tabMenu);
		tabList = new ArrayList<>(4);
		tabMenu.addTab(TURQUOISE, "one", tabList);
		tabList.get(0).addElement(new TTFBox(0, 0, 0.05f, "turquoise fucks!", TURQUOISE));
		tabMenu.addTab(GIANTS_ORANGE, "two", tabList);
		tabList.get(1).addElement(infoText);
		tabMenu.addTab(TEAL_BLUE, "three", tabList);
		tabMenu.addTab(SAFFRON, "four", tabList);
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
