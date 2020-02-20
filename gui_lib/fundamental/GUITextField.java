package fundamental;

import rendering.shapes.implemented.GUIText;
import utils.ColorPalette;

public class GUITextField extends Element {
	
	private GUIText textField;

	/**
	 * 
	 * Creates a text field that can be used to display text in the GUI.
	 * 
	 * @param text The text to display.
	 * @param font The font to render the text with.
	 * @param width The width of the TextField measured in pixel.
	 * @param height The height of the TextField measured in pixel.
	 * @param fontSize The color to render the text with.
	 */
	public GUITextField(String text, String font, int width, int height, int fontSize) {
		super(new GUIText(text, font, ColorPalette.BLACK, fontSize), width, height);
		
		textField = (GUIText)getShape();
	}
	
	
	/**
	 * 
	 * Creates a text field that can be used to display text in the GUI.
	 * 
	 * @param text The text to display.
	 * @param font The font to render the text with.
	 * @param width The width of the TextField in relation to its parent element.
	 * @param height The height of the TextField in relation to its parent element.
	 * @param fontSize The color to render the text with.
	 */
	public GUITextField(String text, String font, float widthPercent, float heightPercent, int fontSize) {
		super(new GUIText(text, font, ColorPalette.BLACK, fontSize), widthPercent, heightPercent);
		
		textField = (GUIText)getShape();
	}
	
	
	/**
	 * 
	 * Changes the text displayed by this shape.
	 * 
	 * @param text The text to display.
	 */
	public void setText(String text) {
		textField.setText(text);;
	}
	
	
	/**
	 * 
	 * Changes the font used to display the text.
	 * 
	 * @param font The name of the font to use.
	 */
	public void setFont(String font) {
		textField.setFont(font);
	}
	
	
	/**
	 * 
	 * Changes the size of the rendered text.
	 * 
	 * @param fontSize The size of the text.
	 */
	public void setFontSize(int fontSize) {
		textField.setFontSize(fontSize);
	}

}
