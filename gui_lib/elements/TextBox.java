package elements;

import elements.fundamental.GUILabeledElement;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;

public class TextBox extends GUILabeledElement {

	public TextBox(Vector4f color, float x, float y, float width, float height, String text) {
		super(new GUIQuad(), color, x, y, width, height, text);
		
		
	}
	
	
	
}
