package input;

import fundamental.GUIContainer;
import jdk.internal.org.objectweb.asm.Handle;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.GUIShape;


public class GUIScrollBar extends GUIContainer<Handle> {

	public GUIScrollBar(GUIShape shape, int width, int height, FlexDirection scrollDirection) {
		super(shape, width, height, scrollDirection, 1);
	}
	
	public GUIScrollBar(GUIShape shape, float widthPercent, float heightPercent, FlexDirection scrollDirection) {
		super(shape, widthPercent, heightPercent, scrollDirection, 1);
	}

}
