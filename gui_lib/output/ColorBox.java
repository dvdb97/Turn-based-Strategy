package output;

import assets.meshes.geometry.Color;
import fundamental.Element;
import rendering.shapes.implemented.GUIQuad;

public class ColorBox extends Element {
	
	public ColorBox(int width, int height, Color color) {
		super(new GUIQuad(color), width, height);
		setColor(color);
	}
	
	
	public ColorBox(float widthPercent, float heightPercent, Color color) {
		super(new GUIQuad(color), widthPercent, heightPercent);
		setColor(color);
	}
	
	
	public void setColor(Color color) {
		getShape().setColor(color);
	}
	
	
	public Color getColor() {
		return getShape().getColor().copyOf();
	}
	
	
	public void setRed(float red) {
		Color color = getShape().getColor();
		color = new Color(red, color.getGreen(), color.getBlue(), color.getAlpha());
		setColor(color);
	}
	
	
	public void setGreen(float green) {
		Color color = getShape().getColor();
		color = new Color(color.getRed(), green, color.getBlue(), color.getAlpha());
		setColor(color);
	}
	
	
	public void setBlue(float blue) {
		Color color = getShape().getColor();
		color = new Color(color.getRed(), color.getGreen(), blue, color.getAlpha());
		setColor(color);
	}
	
}
