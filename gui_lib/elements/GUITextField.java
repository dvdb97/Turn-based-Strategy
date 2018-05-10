package elements;

import elements.fundamental.GUILabeledElement;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;

public class GUITextField extends GUILabeledElement {

	public GUITextField(Vector4f color, float x, float y, float width, float height, String text) {
		super(new GUIQuad(), color, x, y, width, height, text);
		
		
	}
	
	public String getText() {
		return getLabel();
	}
	
	public void setText(String text) {
		setLabel(text);
	}
	
}
