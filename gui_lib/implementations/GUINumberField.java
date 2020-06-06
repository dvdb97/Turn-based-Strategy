package implementations;

import assets.meshes.geometry.Color;
import fundamental.GUITextField;

public class GUINumberField extends GUITextField {
	
	int number;
	String text;
	
	public GUINumberField(String text, int number, String font, Color color, int width, int height, int fontSize) {
		super(text, font, color, width, height, fontSize);
		this.text = text;
		setNumber(number);
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
		this.setText(text+Integer.toString(number));
	}
	
	public int increaseNumber(int delta) {
		setNumber(this.number + delta);
		return getNumber();
	}
	
}
