package fundamental;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import input.Mouse;
import math.vectors.Vector3f;
import rendering.shapes.Shape;

public abstract class ClickableElement extends Element implements Clickable {
	
	protected boolean pressed;
	
	//******************** constructor *******************************
	
	protected ClickableElement(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
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
				onClick();
			}
			if (Mouse.isLeftReleased()) {
				if (pressed)
					onRelease();
				pressed = false;
			}
			
			return true;
			
		} else {
			
			if(pressed)
				reset();
			return false;
			
		}
		
	}
	
	@Override
	public void reset() {
		pressed = false;
		
		//reset to original state
		
	}
	
}
