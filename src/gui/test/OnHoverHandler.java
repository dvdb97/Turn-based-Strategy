package gui.test;

import elements.functions.GUIEventHandler;
import elements.fundamental.GUIElementBase;
import math.vectors.Vector4f;

public class OnHoverHandler implements GUIEventHandler {
	
	private Vector4f color = new Vector4f(1f, 1f, 1f, 1f);
	
	private Vector4f stdColor;

	@Override
	public void function(GUIElementBase element) {
		
		if (element.getColor() != color) {
			
			stdColor = element.getColor();
			element.setColor(color);
			
			return;
		}
		
		element.setColor(stdColor);	
		
	}

}
