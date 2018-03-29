package gui.test;

import elements.containers.GUIWindow;
import fontRendering.font.classic.TimesNewRoman;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;

public class ImplementedWindow extends GUIWindow {

	public ImplementedWindow(Vector4f color, float x, float y, float width, float height) {
		super(new GUIQuad(), color, x, y, width, height);
		
		this.add(new TestToggleButton());
		
	}
	
}
