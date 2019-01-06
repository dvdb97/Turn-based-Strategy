package fundamental;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import gui_core.GUIManager;
import input.Mouse;
import math.vectors.Vector3f;
import rendering.shapes.GUIShape;

public abstract class DragableElement extends ClickableElement {
	
	protected DragableElement(GUIShape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean processInput() {
		
		Vector3f vec = invertedTM.times(Mouse.getCursorPosititon());
		
		if(shape.isHit(vec.getA(), vec.getB()) && Mouse.isLeftClicked()) {
			pressed = true;
			GUIManager.setClickedElement(this);
			onClick();
			return true;
		}
		
		if(pressed && Mouse.isLeftReleased()) {
			onRelease();
			GUIManager.resetClickedELement(this);
			pressed = false;
			return true;
		}
		
		
		return false;
		
	}
	

}
