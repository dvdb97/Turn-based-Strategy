package moving_parts;

import fundamental.GUIElement;
import rendering.shapes.GUIShape;

public class Handle extends GUIElement {
	
	private float value;
	
	
	public Handle(GUIShape shape, int width, int height) {
		super(shape, width, height);
	}
	
	
	public Handle(GUIShape shape, float widthPercent, float heightPercent) {
		super(shape, widthPercent, heightPercent);
	}
	
}
