package work_in_progress.test;

import container.GUIWindow;
import dataType.GUIElementMatrix;
import function.Function;
import input.PushButton;
import output.ColorBox;
import rendering.shapes.GUIQuad;

import static utils.ColorPalette.*;

import assets.meshes.geometry.Color;

public class TestWindow extends GUIWindow {
	
	private ColorBox colorBox;
	
	public TestWindow() {
		super(new GUIQuad(), GRAY, new GUIElementMatrix(0f, 0.5f, 0.5f, 1f));
		
		colorBox = new ColorBox(WHITE, new GUIElementMatrix(0.03f, -0.03f, 0.94f, 0.235f));
		children.add(colorBox);
		
		TestPushButton button = new TestPushButton(YELLOW, new GUIElementMatrix(0.03f, -0.265f, 0.94f, 0.1f));
		button.setFunction( (e) -> colorBox.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f)) );
		children.add(button);
		
	}
	
	
	
}
