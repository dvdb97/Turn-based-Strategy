package gui;

import world.AgentAuthority;
import world.BuildingAuthority;
import world.GameBoard;
import world.Tile;
import world.WorldManager;
import world.agents.Agent;
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

import container.Tab;
import container.TabMenu;

public class TileInfoWindow extends DefaultWindow {
	
	private Tile tile;
	
//	private TabMenu tabMenu;
	
	private GUITextField tileText;
	
	private GUIImageBox imageBox;
	private GUIQuad waterTex;
	private GUIQuad grassTex;
	
	int counter = 0;
	int tileIndex1 = -1;
	int tileIndex2 = -1;
	boolean selectTile1 = true;
	
	//**************************** init **************************************
	public TileInfoWindow() {
		super("Tile Information", 100, 100, 500, 800, FlexDirection.COLUMN);
		
		imageBox = new GUIImageBox("res/PH_Sea.png", 90f, 30f);
		imageBox.setLocalXPosition(50f);
		imageBox.setMargin(Direction.TOP, 10);
		imageBox.setMargin(Direction.BOTTOM, 10);
		addChild(imageBox);
		
		waterTex = new GUIQuad("res/PH_Sea.png");
		grassTex = new GUIQuad("res/PH_Grassland.png");
		
		
		TabMenu tabMenu = new TabMenu(90f, 60f, FlexDirection.COLUMN, ColorPalette.WHITE);
		tabMenu.setLocalXPosition(50f);
		tabMenu.setMargin(Direction.TOP, 10);
		tabMenu.setMargin(Direction.BOTTOM, 10);
		
		addChild(tabMenu);

		tabMenu.addDefaultTab("info", initInfoTab());
		tabMenu.addTab("agents", initAgentTab());
		tabMenu.addTab("buildings", initBuildingTab());
		tabMenu.addTab("city", initCityTab());
	}
	
	//************************ info tab **********************************
	private Tab initInfoTab() {
		
		Tab tab = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		
		// geography
		tileText = new GUITextField("Geography information", "FreeMono", 90f, 40f, 20);
		tileText.setPadding(Direction.ALL, 5);
		tileText.setMargin(Direction.ALL, 5);
		
		// map mode
		OptionSet<RadioButton> set = new OptionSet<RadioButton>(50, 200, FlexDirection.COLUMN);
		
		RadioButton rb1 = new RadioButton(30);
		rb1.setPadding(Direction.ALL, 5);
		rb1.setMargin(Direction.ALL, 10);
		rb1.addEnableListener((e) -> WorldManager.changeMM(0));
		set.addButton(rb1);
		
		RadioButton rb2 = new RadioButton(30);
		rb2.setPadding(Direction.ALL, 5);
		rb2.setMargin(Direction.ALL, 10);
		rb2.addEnableListener((e) -> WorldManager.changeMM(1));
		set.addButton(rb2);
		
		RadioButton rb3 = new RadioButton(30);
		rb3.setPadding(Direction.ALL, 5);
		rb3.setMargin(Direction.ALL, 10);
		rb3.addEnableListener((e) -> WorldManager.changeMM(2));
		set.addButton(rb3);
		
		RadioButton rb4 = new RadioButton(30);
		rb4.setPadding(Direction.ALL, 5);
		rb4.setMargin(Direction.ALL, 10);
		rb4.addEnableListener((e) -> WorldManager.changeMM(3));
		set.addDefaultButton(rb4);
		
		// finish
		tab.addChild(tileText);
		tab.addChild(set);
		
		return tab;
	}
	
	//************************ agent tab *********************************
	private Tab initBuildingTab() {

		Tab tab = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		
		// TextField: tile selection
		GUITextField text = new GUITextField("Tile 1: "+tileIndex1+"\nTile 2: "+tileIndex2, "FreeMono", 90f, 20f, 20);
		text.setLocalXPosition(50f);
		text.setLocalYPosition(10f);
		text.setPadding(Direction.ALL, 5);
		text.setMargin(Direction.ALL, 10);
		
		// Button: select tile
		GUIButton selectButton = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 20f);
		selectButton.setLabel("Select Tile", "FreeMono", 20);
		selectButton.setLocalXPosition(50f);
		selectButton.setPadding(Direction.ALL, 15);
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
		GUIButton buildButton = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 20f);
		buildButton.setLabel("Build Street", "FreeMono", 20);
		buildButton.setLocalXPosition(50f);
		buildButton.setPadding(Direction.ALL, 15);
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
	
	//************************ building tab ******************************
	private Tab initAgentTab() {
		
		Tab tab = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
				
		// Button1: spawn agent
		GUIButton button1 = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 20f);
		button1.setLabel("Spawn Agent", "FreeMono", 20);
		button1.setPadding(Direction.ALL, 15);
		button1.setMargin(Direction.ALL, 15);
		button1.setLocalXPosition(50f);
		button1.addOnClickListener((e) -> {
			AgentAuthority.requestAgentInCity(GameBoard.getCity(TileSelecter.getSelectedTileIndex()));
		});
		
		// Button2: open agent window
		GUIButton button2 = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 20f);
		button2.setLabel("Show Agent Info", "FreeMono", 20);
		button2.setPadding(Direction.ALL, 15);
		button2.setMargin(Direction.ALL, 15);
		button2.setLocalXPosition(50f);
		button2.addOnClickListener((e) -> {
			ArrayList<Agent> a = GameBoard.getAgents(TileSelecter.getSelectedTileIndex());
			if (a.size() > 0) {
				GameGUIManager.showAgentInfoWindow(a.get(0));
			}
		});
		
		// finish
		tab.addChild(button1);
		tab.addChild(button2);
		
		return tab;
	}
	
	//************************ city tab **********************************
	private Tab initCityTab() {
		
		Tab tab = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);

		// TextField: number of cities
		GUITextField numCitiesTextField = new GUITextField("Total number of cities: " + counter, "FreeMono", 90f, 20f, 20);
		numCitiesTextField.setLocalXPosition(50f);
		numCitiesTextField.setLocalYPosition(10f);
		
		// Button: build city
		GUIButton cityButton = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 20f);
		cityButton.setLabel("Build City", "FreeMono", 20);
		
		cityButton.setLocalXPosition(50f);
		cityButton.addOnClickListener((e) -> {
			if (BuildingAuthority.requestCityOnTile(TileSelecter.getSelectedTileIndex()))
				numCitiesTextField.setText("Total number of cities: " + Integer.toString(++counter));
			}
		);

		// finish
		tab.addChild(numCitiesTextField);
		tab.addChild(cityButton);
		
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
			imageBox.setShape(waterTex);
		} else {
			imageBox.setShape(grassTex);
		}
	}
	
}