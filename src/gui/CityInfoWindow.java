package gui;

import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUITextField;
import fundamental.InvisibleContainer;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;
import world.city.City;

public class CityInfoWindow extends DefaultWindow {
	
	private City city;
	private String cityInfoString;
	private GUITextField text;
	private GUIButton button1;
	
	//**************************** init *************************************
	public CityInfoWindow(City city) {
		super("City Information", 610, 410, 300, 300, FlexDirection.COLUMN);

		// INPUT-CONTAINER
		InvisibleContainer<GUIButton> inputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		button1 = new GUIButton(new GUIQuad(ColorPalette.GIANTS_ORANGE), 40f, 90f);
		button1.setLabel("Build Building", "FreeMono", 20);
		button1.setMargin(Direction.ALL, 5);
		button1.setPadding(Direction.ALL, 5);
		button1.addOnClickListener((e) -> {
			//build building
		});
		inputContainer.addChild(button1);
		
		// OUTPUT-CONTAINER
		InvisibleContainer<GUITextField> outputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		text = new GUITextField("Dummy", "FreeMono", 90f, 90f, 20);
		text.setMargin(Direction.ALL, 5);
		text.setPadding(Direction.ALL, 5);
		
		outputContainer.addChild(text);
		
		addChild(inputContainer);
		addChild(outputContainer);
		changeCity(city);
		
	}

	public void changeCity(City city) {
		this.city = city;
		
		if (city==null) {
			cityInfoString = "City not found.";
		} else {
			cityInfoString = city.getCityInfoString();
		}

		text.setText(cityInfoString);
	}
	
}
