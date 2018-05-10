package elements.containers;

import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

/**
 * 
 * GUITab is an arbitrary container (so far)
 * it has the name "tab", because i need an non-abstract element container to implement an tab menu
 * 
 * important (in relation to GUITabMenu): GUITab does not contain the button you switch to this tab
 * 
 * @author jona
 */
public class GUITab extends GUIContainerElement {
	
	
	
	public GUITab(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

}
