package work_in_progress;

import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import output.ColorBox;
import stbFont.TTFBox;

import static utils.ColorPalette.*;

import assets.meshes.geometry.Color;

public class ColorPickWindow extends GUIWindow {
	
	private ColorBox colorBox;
	
	private TTFBox redText;
	private TTFBox greenText;
	private TTFBox blueText;
	
	private Slider redSlider;
	private Slider greenSlider;
	private Slider blueSlider;
	
	//************************* constructor **************************************
	
	public ColorPickWindow() {
		super(GRAY, new GUIElementMatrix(0f, 0.5f, 0.5f, 1f));
		
		setUpColorBox();
		
		SetUpButton();
		
		setUpTextBoxes();
		
		setUpSliders();
		
	}
	
	
	
	//********************************************************************************
	
	private void setUpColorBox() {
		colorBox = new ColorBox(WHITE, new GUIElementMatrix(0.03f, -0.03f, 0.94f, 0.235f));
		children.add(colorBox);
	}

	private void SetUpButton() {
		TestPushButton button = new TestPushButton(YELLOW_1, new GUIElementMatrix(0.03f, -0.265f, 0.94f, 0.1f));
		button.setFunction( (e) -> {redSlider.setValue((float)Math.random());greenSlider.setValue((float)Math.random());blueSlider.setValue((float)Math.random());} );
		children.add(button);
	}

	private void setUpTextBoxes() {
		redText   = new TTFBox(0.0833f, -0.4f, 0.07f, "255", BLACK);
		greenText = new TTFBox(0.4167f, -0.4f, 0.07f, "255", BLACK);
		blueText  = new TTFBox(0.7500f, -0.4f, 0.07f, "255", BLACK);
		
		children.add(redText);
		children.add(greenText);
		children.add(blueText);
	}
	
	private void setUpSliders() {
		
		redSlider   = new Slider(RED, new GUIElementMatrix(0.0833f, -0.535f, 0.1667f, 0.435f));
		greenSlider = new Slider(GREEN, new GUIElementMatrix(0.4167f, -0.535f, 0.1667f, 0.435f));
		blueSlider  = new Slider(BLUE, new GUIElementMatrix(0.7500f, -0.535f, 0.1667f, 0.435f));
		
		redSlider.setFunction(   (slider) -> setColor(new Color(slider.getValue()           , colorBox.getColor().getGreen(), colorBox.getColor().getBlue(), colorBox.getColor().getAlpha())) );
		greenSlider.setFunction( (slider) -> setColor(new Color(colorBox.getColor().getRed(), slider.getValue()             , colorBox.getColor().getBlue(), colorBox.getColor().getAlpha())) );
		blueSlider.setFunction(  (slider) -> setColor(new Color(colorBox.getColor().getRed(), colorBox.getColor().getGreen(), slider.getValue()            , colorBox.getColor().getAlpha())) );
		
		children.add(redSlider);
		children.add(greenSlider);
		children.add(blueSlider);
		
	}
	
	//********************************************************************************
	
	private void setColor(Color color) {
		
		colorBox.setColor(color);
		
		redText.changeTextTo(Integer.toString(color.getRedInt()));
		greenText.changeTextTo(Integer.toString(color.getGreenInt()));
		blueText.changeTextTo(Integer.toString(color.getBlueInt()));
		
	//	redSlider.setValue(color.getRed());
	//	greenSlider.setValue(color.getGreen());
	//	blueSlider.setValue(color.getBlue());
		
	}
	
}
