package implementations;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import font.EditableTextBox;
import fundamental.GUIWindow;

import static utils.ColorPalette.*;

public class FontWindow extends GUIWindow {
	
	private EditableTextBox text;
	
	public FontWindow(Color color, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		text = new EditableTextBox(0.05f, -0.2f, 0.9f, 0.1f, GREEN);
		children.add(text);
		children.add(text.textBox);
		
	}
	
	@Override
	public void update() {
		super.update();
	}
	
}
