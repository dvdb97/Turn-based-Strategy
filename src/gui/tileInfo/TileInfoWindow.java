package gui.tileInfo;

import world.BuildingAuthority;
import world.WorldManager;
import world.gameBoard.Tile;
import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUIImageBox;
import fundamental.GUITextField;
import input.buttons.OptionSet;
import input.buttons.RadioButton;
import interaction.TileSelecter;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

import container.Tab;
import container.TabMenu;

public class TileInfoWindow extends DefaultWindow {
	
	private Tile tile;
	
	//private GUIToggleButton button;
	
	private TabMenu tabMenu;
	
	private GUITextField tileText;
	
	private GUIImageBox imageBox;
	private GUIQuad waterTex;
	private GUIQuad grassTex;
	
	int counter = 0;
	
	//**************************** init *************************************
	public TileInfoWindow() {
		super("Tile Information", 100, 100, 500, 800, FlexDirection.COLUMN);
		
		imageBox = new GUIImageBox("res/PH_Sea.png", 90f, 30f);
		imageBox.setLocalXPosition(50f);
		imageBox.setMargin(Direction.TOP, 10);
		imageBox.setMargin(Direction.BOTTOM, 10);
		addChild(imageBox);
		
		waterTex = new GUIQuad("res/PH_Sea.png");
		grassTex = new GUIQuad("res/PH_Grassland.png");
		
		
		tabMenu = new TabMenu(90f, 60f, FlexDirection.COLUMN, ColorPalette.WHITE);
		tabMenu.setLocalXPosition(50f);
		tabMenu.setMargin(Direction.TOP, 10);
		tabMenu.setMargin(Direction.BOTTOM, 10);
		addChild(tabMenu);
		
		//**************************************************************
		
		Tab tab1 = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		GUITextField text1 = new GUITextField("turquoise fucks!", "FreeMono", 90f, 90f, 20);
		text1.setLocalXPosition(50f);
		text1.setLocalYPosition(50f);
		
		tab1.addChild(text1);
		
		tabMenu.addDefaultTab("overview", tab1);
		
		//**************************************************************
		
		Tab tab2 = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		GUITextField text2 = new GUITextField("Geography information", "FreeMono", 90f, 90f, 20);
		text2.setLocalXPosition(50f);
		text2.setLocalYPosition(50f);
		
		tileText = text2;
		tab2.addChild(text2);
		
		tabMenu.addTab("geography", tab2);
		
		//**************************************************************
		
		Tab tab3 = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		GUITextField text3 = new GUITextField("Total number of cities: " + counter, "FreeMono", 90f, 60f, 20);
		text3.setLocalXPosition(50f);
		text3.setLocalYPosition(10f);
		
		GUIButton button = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 20f);
		button.setLabel("Build City", "FreeMono", 20);
		
		button.setLocalXPosition(50f);
		button.addOnClickListener((e) -> {
			if (BuildingAuthority.requestCityOnTile(TileSelecter.getSelectedTileIndex()))
				text3.setText("total number of cities: " + Integer.toString(++counter));
			}
		);
		
		tab3.addChild(text3);
		tab3.addChild(button);
		
		tabMenu.addTab("buildings", tab3);
		
		//**************************************************************
		
		Tab tab4 = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		
		OptionSet<RadioButton> set = new OptionSet<RadioButton>(50, 150, FlexDirection.COLUMN);
		set.setLocalXPosition(50f);
		
		RadioButton rb1 = new RadioButton(30);
		rb1.setMargin(Direction.ALL, 10);
		rb1.addEnableListener((e) -> WorldManager.changeMM(0));
		set.addDefaultButton(rb1);
		
		RadioButton rb2 = new RadioButton(30);
		rb2.setMargin(Direction.ALL, 10);
		rb2.addEnableListener((e) -> WorldManager.changeMM(1));
		set.addButton(rb2);
		
		RadioButton rb3 = new RadioButton(30);
		rb3.setMargin(Direction.ALL, 10);
		rb3.addEnableListener((e) -> WorldManager.changeMM(2));
		set.addButton(rb3);
		
		tab4.addChild(set);
		tabMenu.addTab("map modes", tab4);
		
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
				
		tileText.setText(TileInfoStringIssuer.getTileInfoString(tile));
				
		if (tile.isWater()) {
			imageBox.setShape(waterTex);
		} else {
			imageBox.setShape(grassTex);
		}
	}
	
}
