package output;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.Element;
import rendering.shapes.GUIQuad;

public class ColorBox extends Element {
	
	public ColorBox(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
	}
	
	public void setColor(Color color) {
		this.color = color.copyOf();
	}
	
	public Color getColor() {
		return color.copyOf();
	}
	
}
