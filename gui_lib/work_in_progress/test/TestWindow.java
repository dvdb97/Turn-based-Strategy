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

public class TestWindow extends GUIWindow {
	
	private ColorBox colorBox;
	
	private TextBox redText;
	private TextBox greenText;
	private TextBox blueText;
	
	private Slider slider;
	
	//************************* constructor **************************************
	
	public TestWindow() {
		super(new GUIQuad(), GRAY, new GUIElementMatrix(0f, 0.5f, 0.5f, 1f));
		
		setUpColorBox();
		
		SetUpButton();
		
		setUpTextBoxes();
		
		setUpSliders();
		
	}
	
	
	
	//********************************************************************************
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		super.update(parentMatrix);
		setColor(new Color(slider.getValue(), colorBox.getColor().getGreen(), colorBox.getColor().getBlue(), colorBox.getColor().getAlpha()));
	}
	
	//********************************************************************************
	
	private void setUpColorBox() {
		colorBox = new ColorBox(WHITE, new GUIElementMatrix(0.03f, -0.03f, 0.94f, 0.235f));
		children.add(colorBox);
	}

	private void SetUpButton() {
		TestPushButton button = new TestPushButton(YELLOW, new GUIElementMatrix(0.03f, -0.265f, 0.94f, 0.1f));
		button.setFunction( (e) -> setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f)) );
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
		
		slider = new Slider(GIANTS_ORANGE, new GUIElementMatrix(0.0833f, -0.535f, 0.1667f, 0.435f));
		children.add(slider);
	}
	
	
	//********************************************************************************
	
	private void setColor(Color color) {
		
		colorBox.setColor(color);
		
		redText.setLabel(Integer.toString(color.getRedInt()));
		greenText.setLabel(Integer.toString(color.getGreenInt()));
		blueText.setLabel(Integer.toString(color.getBlueInt()));
		
		slider.setValue(color.getRed());
		
	}
	
}
