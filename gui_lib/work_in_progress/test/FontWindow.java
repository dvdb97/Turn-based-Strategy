package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import rendering.shapes.GUIQuad;
import stbFont.TTFBox;

import static utils.ColorPalette.*;

public class FontWindow extends GUIWindow {
	
	private TTFBox text;
	
	public FontWindow(Color color, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		text = new TTFBox(0.1f, 0, 0.05f, "1234567890", BLACK);
		children.add(text);
		
	}
	
	@Override
	public void update() {
		super.update();
	}
	
}
