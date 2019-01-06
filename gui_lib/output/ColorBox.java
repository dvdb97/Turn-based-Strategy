package output;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.Element;
import rendering.shapes.implemented.GUIQuad;

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
	
	public void setRed(float red) {
		color = new Color(red, color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public void setGreen(float green) {
		color = new Color(color.getRed(), green, color.getBlue(), color.getAlpha());
	}
	
	public void setBlue(float blue) {
		color = new Color(color.getRed(), color.getGreen(), blue, color.getAlpha());
	}
	
}
