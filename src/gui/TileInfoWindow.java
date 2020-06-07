package gui;

import world.AgentAuthority;
import world.BuildingAuthority;
import world.GameBoard;
import world.Tile;
import world.WorldManager;
import world.agents.Agent;
import world.estate.City;
import world.estate.Estate;
import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUIImageBox;
import fundamental.GUITextField;
import gui.tileInfo.TileInfoStringIssuer;
import input.buttons.OptionSet;
import input.buttons.RadioButton;
import interaction.TileSelecter;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import pathfinding.AStarSearch;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

import java.util.ArrayList;
import java.util.List;

import assets.meshes.geometry.Color;
import container.Tab;
import container.TabMenu;

public class TileInfoWindow extends DefaultWindow {
	
	private Tile tile;
	
	private GUITextField tileText;
	
	private Color waterColor;
	private Color grassColor;
	
	int counter = 0;
	int tileIndex1 = -1;
	int tileIndex2 = -1;
	boolean selectTile1 = true;
	
	//**************************** init **************************************
	public TileInfoWindow() {
		super("Tile Information", 5, 90, 500, 300, FlexDirection.COLUMN);
		
		waterColor = ColorPalette.BLUE_1;
		grassColor = ColorPalette.GREEN_1;
		
		TabMenu tabMenu = new TabMenu(90f, 90f, FlexDirection.COLUMN, ColorPalette.WHITE);
		tabMenu.setLocalXPosition(50f);
		tabMenu.setMargin(Direction.TOP, 10);
		tabMenu.setMargin(Direction.BOTTOM, 10);
		
		addChild(tabMenu);

		tabMenu.addDefaultTab("info", initInfoTab());
		tabMenu.addTab("menu", initMenuTab());
		tabMenu.addTab("streets", initStreetsTab());
		tabMenu.addTab("mapmode", initMapModeTab());
	}
	
	//************************ info tab **********************************
	private Tab initInfoTab() {
		
		Tab tab = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		
		// geography
		tileText = new GUITextField("Geography information", "FreeMono", 90f, 90f, 20);
		tileText.setPadding(Direction.ALL, 5);
		tileText.setMargin(Direction.ALL, 5);
				
		// finish
		tab.addChild(tileText);
		
		return tab;
	}
	
	//************************ menu tab ******************************
	private Tab initMenuTab() {
		
		Tab tab = new Tab(ColorPalette.WHITE, FlexDirection.ROW);

		// Button: show estate info
		GUIButton button1 = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 50f);
		button1.setLabel("Show Estate Info", "FreeMono", 20);
		button1.setPadding(Direction.ALL, 5);
		button1.setMargin(Direction.ALL, 5);
		button1.addOnClickListener((e) -> {
			Estate estate = GameBoard.getEstate(TileSelecter.getSelectedTileIndex());
			if (estate != null) {
				GameGUIManager.showEstateInfoWindow(estate);
			}
		});
		
		// Button: show agent window
		GUIButton button2 = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 50f);
		button2.setLabel("Show Agent Info", "FreeMono", 20);
		button2.setPadding(Direction.ALL, 5);
		button2.setMargin(Direction.ALL, 5);
		button2.addOnClickListener((e) -> {
			ArrayList<Agent> a = GameBoard.getAgents(TileSelecter.getSelectedTileIndex());
			if (a.size() > 0) {
				GameGUIManager.showAgentInfoWindow(a.get(0));
			}
		});

		// Button: build city
		GUIButton button3 = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 50f);
		button3.setLabel("Build City", "FreeMono", 20);
		button3.setPadding(Direction.ALL, 5);
		button3.setMargin(Direction.ALL, 5);
		button3.addOnClickListener((e) -> {
			GameGUIManager.showEstateInfoWindow(BuildingAuthority.requestCityOnTile(TileSelecter.getSelectedTileIndex()));
		});
				
		// finish
		tab.addChild(button1);
		tab.addChild(button2);
		tab.addChild(button3);
		
		return tab;
	}
	
	//************************ streets tab *********************************
	private Tab initStreetsTab() {

		Tab tab = new Tab(ColorPalette.WHITE, FlexDirection.ROW);
		
		// TextField: tile selection
		GUITextField text = new GUITextField("Tile 1: "+tileIndex1+"\nTile 2: "+tileIndex2, "FreeMono", 30f, 25f, 20);
		text.setPadding(Direction.ALL, 5);
		text.setMargin(Direction.ALL, 5);
		
		// Button: select tile
		GUIButton selectButton = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 50f);
		selectButton.setLabel("Select Tile", "FreeMono", 20);
		selectButton.setPadding(Direction.ALL, 5);
		selectButton.setMargin(Direction.ALL, 5);
		selectButton.addOnClickListener((e) -> {
			if (selectTile1) {
				tileIndex1 = TileSelecter.getSelectedTileIndex();
				selectTile1 = !selectTile1;
			} else {
				tileIndex2 = TileSelecter.getSelectedTileIndex();
				selectTile1 = !selectTile1;
			}
			text.setText("Tile 1: "+tileIndex1+"\nTile 2: "+tileIndex2);
			}
		);
		
		// Button: build street
		GUIButton buildButton = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 50f);
		buildButton.setLabel("Build Street", "FreeMono", 20);
		buildButton.setPadding(Direction.ALL, 5);
		buildButton.setMargin(Direction.ALL, 5);
		buildButton.addOnClickListener((e) -> {
			List<Integer> pathIndices = AStarSearch.getPath(GameBoard.getGraph(), tileIndex1, tileIndex2);
			if (BuildingAuthority.requestConsecutiveStreets(pathIndices)) {
				tileIndex1 = -1;
				tileIndex2 = -1;
				text.setText("Tile 1: "+tileIndex1+"\nTile 2: "+tileIndex2);
			}}
		);
		
		tab.addChild(text);
		tab.addChild(selectButton);
		tab.addChild(buildButton);
		
		return tab;
	}
	
	//************************ mapmode tab **********************************
	private Tab initMapModeTab() {
		
		Tab tab = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		
		// map mode
		OptionSet<RadioButton> set = new OptionSet<RadioButton>(50, 200, FlexDirection.COLUMN);
		
		RadioButton rb1 = new RadioButton(25);
		rb1.setPadding(Direction.ALL, 5);
		rb1.setMargin(Direction.ALL, 10);
		rb1.addEnableListener((e) -> WorldManager.changeMM(0));
		set.addButton(rb1);
		
		RadioButton rb2 = new RadioButton(25);
		rb2.setPadding(Direction.ALL, 5);
		rb2.setMargin(Direction.ALL, 10);
		rb2.addEnableListener((e) -> WorldManager.changeMM(1));
		set.addButton(rb2);
		
		RadioButton rb3 = new RadioButton(25);
		rb3.setPadding(Direction.ALL, 5);
		rb3.setMargin(Direction.ALL, 10);
		rb3.addEnableListener((e) -> WorldManager.changeMM(2));
		set.addButton(rb3);
		
		RadioButton rb4 = new RadioButton(25);
		rb4.setPadding(Direction.ALL, 5);
		rb4.setMargin(Direction.ALL, 10);
		rb4.addEnableListener((e) -> WorldManager.changeMM(3));
		set.addDefaultButton(rb4);

		tab.addChild(set);
		
		return tab;
	}
	
	
	//************************ get & set *************************************
	public void setTile(Tile tile) {
		if (this.tile == tile) {
			return;
		}

		this.tile = tile;
				
		tileText.setText(TileInfoStringIssuer.getTileInfoString(tile));
				
		if (tile.isWater()) {
			;
		} else {
			;
		}
	}
	
}
