package rendering.shapes.implemented;

import assets.meshes.geometry.Color;
import rendering.Renderer2D;
import rendering.shapes.GUIShape;
import target_detection.TargetDetection;

public class GUIText extends GUIShape {

	private String text;
	private String font;
	private int fontSize;
	
	/**
	 * 
	 * @param text The text to display.
	 * @param font The name of the font to use.
	 * @param color The color of the text.
	 * @param fontSize The size of the text.
	 */
	public GUIText(String text, String font, Color color, int fontSize) {
		super(color);
		
		this.text = text;
		this.font = font;
		this.fontSize = fontSize;
	}
	
	
	/**
	 * 
	 * Changes the text displayed by this shape.
	 * 
	 * @param text The text to display.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	
	/**
	 * 
	 * Changes the font used to display the text.
	 * 
	 * @param font The name of the font to use.
	 */
	public void setFont(String font) {
		this.font = font;
	}
	
	
	/**
	 * 
	 * Changes the size of the rendered text.
	 * 
	 * @param fontSize The size of the text.
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	

	@Override
	public void render(int x, int y, int width, int height) {
		Renderer2D.beginPath();		
		Renderer2D.multilineText(x, y, width, text, font, getColor(), fontSize);
	}
	

	@Override
	public boolean isTargeted(int x, int y, int width, int height, int cursorX, int cursorY) {
		return TargetDetection.square(x, y, width, height, cursorX, cursorY);
	}

}
