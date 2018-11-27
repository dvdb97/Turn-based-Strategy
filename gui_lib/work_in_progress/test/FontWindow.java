package work_in_progress.test;

import assets.meshes.geometry.Color;
import container.GUIWindow;
import dataType.GUIElementMatrix;
import rendering.shapes.GUIQuad;
import stbFont.TTFBox;

import static utils.ColorPalette.*;

public class FontWindow extends GUIWindow {
	
	private TTFBox text;
	
	public FontWindow(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		
		text = new TTFBox(0.1f, 0, 0.05f, "Ja?\nNein!", BLACK);
		children.add(text);
		
	}
	
	
	
}
