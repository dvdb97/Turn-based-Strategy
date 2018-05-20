package work_in_progress;

import assets.meshes.geometry.Color;
import math.vectors.Vector3f;

public abstract class ClickableElement extends Element implements Clickable {
	
	private boolean pressed;
	
	//******************** constructor *******************************
	
	protected ClickableElement(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}
	
	//*****************************************************************
	
	@Override
	public boolean processInput() {
		
		Vector3f vec = Mouse.getCursorPosititon();
		vec = transformationMatrix.times(vec);
		
		if (shape.isHit(vec.getA(), vec.getB())) {
			
			if (Mouse.isLeftPressed())
				pressed = true;
				onPress();
			if (Mouse.isLeftReleased())
				pressed = false;
				onRelease();
			
			return true;
			
		} else {
			
			pressed = false;
			return false;
			
		}
		
	}
		
}
