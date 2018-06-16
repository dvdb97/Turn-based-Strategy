package fundamental;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import gui_core.GUIManager;
import input.Mouse;
import math.vectors.Vector3f;
import rendering.shapes.Shape;

public abstract class Button extends ClickableElement {
	
	//******************** constructor *******************************
	
	protected Button(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}
	
	//*****************************************************************
	
	@Override
	public boolean processInput() {
		
		Vector3f vec = invertedTM.times(Mouse.getCursorPosititon());
		
		if (shape.isHit(vec.getA(), vec.getB())) {
			
			if (Mouse.isLeftClicked()) {
				pressed = true;
				GUIManager.setClickedElement(this);
				onClick();
			}
			if (Mouse.isLeftReleased()) {
				if (pressed)
					onRelease();
				GUIManager.resetClickedELement(this);
				pressed = false;
			}
			
			return true;
			
		} else {
			
			if(pressed) {
				reset();
				GUIManager.resetClickedELement(this);
			}
			return false;
			
		}
		
	}
	
	//@Override
	public void reset() {
		pressed = false;
		
		//reset to original state
		
	}
	
}
