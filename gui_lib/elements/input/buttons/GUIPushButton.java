package elements.input.buttons;

import elements.functions.GUIEventHandler;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public class GUIPushButton extends GUIButton {

	public GUIPushButton(GUIShape shape, Vector4f color, float x, float y, float width, float height,
			GUIEventHandler func) {
		super(shape, color, x, y, width, height, func);
		
		setOnclickFunc(func);
		
	}
	
	
	
}
