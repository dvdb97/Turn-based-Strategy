package gui;

import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUITextField;
import fundamental.InvisibleContainer;
import interaction.TileSelecter;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;
import world.AgentAuthority;
import world.BuildingAuthority;
import world.estate.City;
import world.estate.Estate;

public class EstateInfoWindow extends DefaultWindow {

	private Estate estate;
	
	private String cityInfoString;
	private GUITextField text;
	private GUIButton button1;
	private GUIButton button2;
	private GUIButton button3;
	
	private InvisibleContainer<GUIButton> cityContainer;
	private InvisibleContainer<GUIButton> mineContainer;
	
	//**************************** init *************************************
	public EstateInfoWindow(Estate estate) {
		super("Estate Information", 5, 395, 500, 300, FlexDirection.COLUMN);
		
		// OUTPUT-CONTAINER
		InvisibleContainer<GUITextField> outputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		text = new GUITextField("Dummy", "FreeMono", 90f, 90f, 20);
		text.setMargin(Direction.ALL, 5);
		text.setPadding(Direction.ALL, 5);
		
		outputContainer.addChild(text);
		addChild(outputContainer);
				
		cityContainer = initCityInfoWindow();
		mineContainer = initMineInfoWindow();
		
		if (estate instanceof City) {
			addChild(cityContainer);
		} else {
			addChild(mineContainer);
		}
		
		changeEstate(estate);
	}

	private InvisibleContainer<GUIButton> initCityInfoWindow() {
		
		// INPUT-CONTAINER
		InvisibleContainer<GUIButton> inputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		button1 = new GUIButton(new GUIQuad(ColorPalette.GIANTS_ORANGE), 30f, 90f);
		button1.setLabel("Build Building", "FreeMono", 20);
		button1.setMargin(Direction.ALL, 5);
		button1.setPadding(Direction.ALL, 5);
		button1.addOnClickListener((e) -> {
			if (estate instanceof City) {
				BuildingAuthority.requestBuildingInCity((City) estate);
			}
			refreshEstateInfo();
		});
		button2 = new GUIButton(new GUIQuad(ColorPalette.GIANTS_ORANGE), 30f, 90f);
		button2.setLabel("Build Mine", "FreeMono", 20);
		button2.setMargin(Direction.ALL, 5);
		button2.setPadding(Direction.ALL, 5);
		button2.addOnClickListener((e) -> {
			if (estate instanceof City) {
				BuildingAuthority.requestMineOnTile(TileSelecter.getSelectedTileIndex(), (City) estate);
				refreshEstateInfo();
			}
		});
		button3 = new GUIButton(new GUIQuad(ColorPalette.GIANTS_ORANGE), 30f, 90f);
		button3.setLabel("Spawn Agent", "FreeMono", 20);
		button3.setMargin(Direction.ALL, 5);
		button3.setPadding(Direction.ALL, 5);
		button3.addOnClickListener((e) -> {
			if (estate instanceof City) {
				GameGUIManager.showAgentInfoWindow(AgentAuthority.requestAgentInCity((City) estate));
			}
		});
		
		
		inputContainer.addChild(button1);
		inputContainer.addChild(button2);
		inputContainer.addChild(button3);
		
		return inputContainer;
	}
	

	private InvisibleContainer<GUIButton> initMineInfoWindow() {
		InvisibleContainer<GUIButton> inputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		return inputContainer;
	}
	
	public void changeEstate(Estate newEstate) {
		
		if (newEstate==null)
			return;
		
		newEstate.setCallbackFunction((e) -> refreshEstateInfo());
		if (this.estate != null) {
			this.estate.setCallbackFunction(null);
			switchLayout(newEstate);
		}
		
		this.estate = newEstate;
		refreshEstateInfo();
	}
	
	//**************************** refresh ***********************************
	
	private void switchLayout(Estate newEstate) {
		
		if ((newEstate.getClass() == this.estate.getClass()))
			return;
		
		if (newEstate instanceof City) {
			removeChild(mineContainer);
			addChild(cityContainer);
		} else {
			removeChild(cityContainer);
			addChild(mineContainer);
		}
	}
	
	private void refreshEstateInfo() {
		text.setText(estate.getInfoString());
	}
	
	
	@Override
	public void close() {
		GameGUIManager.deleteCityInfoWindow();
		super.close();
	}
	
}
