package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.Container;
import rendering.shapes.Shape;

/**
 * 
 * Tab is just an container (so far)
 * it has the name "tab", because i need an non-abstract element container to implement an tab menu
 * 
 * important (in relation to TabMenu): Tab does not contain the button you switch to this tab
 * 
 * @author jona
 */
public class Tab extends Container {

	protected Tab(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}

}
