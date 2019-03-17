package fundamental;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import rendering.shapes.GUIShape;

public abstract class ClickableElement extends Element implements Clickable {
	
	protected boolean pressed;
	
	protected ClickableElement(GUIShape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		pressed = false;
	}
	
}
