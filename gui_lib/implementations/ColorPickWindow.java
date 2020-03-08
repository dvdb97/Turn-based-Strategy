package implementations;

import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUITextField;
import input.Slider;
import layout.IGUILayoutNode.FlexDirection;
import output.GUIColorBox;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

import assets.meshes.geometry.Color;

public class ColorPickWindow extends DefaultWindow {
	
	private GUIColorBox colorBox;
	
	private GUITextField redText;
	private GUITextField greenText;
	private GUITextField blueText;
	
	private Slider redSlider;
	private Slider greenSlider;
	private Slider blueSlider;
	
	//************************* constructor **************************************
	
	public ColorPickWindow() {
		super("Color Picker", 100, 100, 500, 500, FlexDirection.COLUMN);
		
		setUpColorBox();
		
		SetUpButton();
		
		setUpTextBoxes();
		
		setUpSliders();
		
	}
	
	
	//********************************************************************************
	
	private void setUpColorBox() {
		colorBox = new GUIColorBox(80f, 20f, ColorPalette.WHITE);
		colorBox.setLocalXPosition(50f);
		colorBox.setLocalYPosition(10f);
		
		addChild(colorBox);
	}
	

	private void SetUpButton() {
		GUIButton button = new GUIButton(new GUIQuad(ColorPalette.BLACK), 20f, 10f);
		button.setLocalXPosition(50f);
		
		button.addOnClickListener(e -> {
			redSlider.setValue((float)Math.random() * 100f);
			greenSlider.setValue((float)Math.random() * 100f);
			blueSlider.setValue((float)Math.random() * 100f);
		});

		addChild(button);
	}
	

	private void setUpTextBoxes() {
		redText   = new GUITextField("Red:", "FreeMono", 20f, 10f, 30);
		greenText = new GUITextField("Red:", "FreeMono", 20f, 10f, 30);
		blueText  = new GUITextField("Red:", "FreeMono", 20f, 10f, 30);
		
		addChild(redText);
		addChild(greenText);
		addChild(blueText);
	}
	
	
	private void setUpSliders() {
		redSlider   = new Slider(new GUIQuad(ColorPalette.GRAY), new GUIQuad(ColorPalette.BLACK), 80f, 10f, FlexDirection.ROW);
		greenSlider = new Slider(new GUIQuad(ColorPalette.GRAY), new GUIQuad(ColorPalette.BLACK), 80f, 10f, FlexDirection.ROW);
		blueSlider  = new Slider(new GUIQuad(ColorPalette.GRAY), new GUIQuad(ColorPalette.BLACK), 80f, 10f, FlexDirection.ROW);
		
		redSlider.addEventListener(e -> updateColor());
		greenSlider.addEventListener(e -> updateColor());
		blueSlider.addEventListener(e -> updateColor());
		
		addChild(redSlider);
		addChild(greenSlider);
		addChild(blueSlider);
	}
	
	
	private void updateColor() {
		Color color = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), 1f);
		
		colorBox.setColor(color);
	}
	
}
