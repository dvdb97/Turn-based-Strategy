package implementations;

import assets.meshes.geometry.Color;
import container.DragableWindow;
import dataType.GUIElementMatrix;
import stbFont.TTFBox;

import static utils.ColorPalette.*;

public class FontWindow extends DragableWindow {
	
	private TTFBox text;
	
	public FontWindow(Color color, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		text = new TTFBox(0.1f, -0.2f, 0.05f, "1234567890", BLACK);
		children.add(text);
		
	}
	
	@Override
	public void update() {
		super.update();
	}
	
}
