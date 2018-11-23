package work_in_progress.test;

import container.GUIWindow;
import dataType.GUIElementMatrix;
import function.Function;
import input.PushButton;
import output.ColorBox;
import output.TextBox;
import rendering.shapes.GUIQuad;

import static utils.ColorPalette.*;

import assets.meshes.geometry.Color;

public class ColorPickWindow extends GUIWindow {
	
	private ColorBox colorBox;
	
	private TextBox redText;
	private TextBox greenText;
	private TextBox blueText;
	
	private Slider redSlider;
	private Slider greenSlider;
	private Slider blueSlider;
	
	//************************* constructor **************************************
	
	public ColorPickWindow() {
		super(new GUIQuad(), GRAY, new GUIElementMatrix(0.25f, 1f, 0.25f, 0.5f));
		
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
		redText   = new TextBox(new GUIQuad(), WHITE, new GUIElementMatrix(0.0833f, -0.4f, 0.1667f, 0.1f), "R");
		greenText = new TextBox(new GUIQuad(), WHITE, new GUIElementMatrix(0.4167f, -0.4f, 0.1667f, 0.1f), "G");
		blueText  = new TextBox(new GUIQuad(), WHITE, new GUIElementMatrix(0.7500f, -0.4f, 0.1667f, 0.1f), "B");
		
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
		
		redText.setLabel(Integer.toString(color.getRedInt()));
		greenText.setLabel(Integer.toString(color.getGreenInt()));
		blueText.setLabel(Integer.toString(color.getBlueInt()));
		
	//	redSlider.setValue(color.getRed());
	//	greenSlider.setValue(color.getGreen());
	//	blueSlider.setValue(color.getBlue());
		
	}
	
}
