package input;

import fundamental.GUIContainer;
import rendering.shapes.GUIShape;


public class GUIScrollBar extends GUIContainer<Slider> {

	public GUIScrollBar(GUIShape shape, int width, int height) {
		super(shape, width, height, FlexDirection.COLUMN, 1);
	}
	
	public GUIScrollBar(GUIShape shape, float widthPercent, float heightPercent) {
		super(shape, widthPercent, heightPercent, FlexDirection.COLUMN, 1);
	}

}
