package moving_parts;

import fundamental.Element;
import rendering.shapes.GUIShape;

public class Handle extends Element {
	
	private float value;
	
	
	public Handle(GUIShape shape, int width, int height) {
		super(shape, width, height);
	}
	
	
	public Handle(GUIShape shape, float widthPercent, float heightPercent) {
		super(shape, widthPercent, heightPercent);
	}
	
}
