package gui.test;

import elements.containers.GUIWindow;
import elements.input.GUIToggleButton;
import fontRendering.font.classic.TimesNewRoman;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;

public class ImplementedWindow extends GUIWindow {

	public ImplementedWindow(Vector4f color, float x, float y, float width, float height) {
		super(new GUIQuad(), color, x, y, width, height);
		
		this.addChild(new GUIToggleButton(0.1f, -0.1f, 0.8f, 0.35f, "First!"));
		
		this.addChild(new GUIToggleButton(0.1f, -0.55f, 0.8f, 0.35f, "Second!"));
		
	}
	
}
