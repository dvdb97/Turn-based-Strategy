package work_in_progress;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.DragFunction;
import function.TestDragFunction;
import fundamental.DragableElement;
import input.Mouse;
import math.vectors.Vector2f;
import rendering.shapes.implemented.GUIQuad;

public class Handle extends DragableElement {
	
	private DragFunction dragFunction;
	
	private Vector2f offset;
	
	public Handle(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		
		dragFunction = new TestDragFunction();
		
		offset = new Vector2f(0, 0);
		
	}
	
	@Override
	public void onClick() {
		offset.setB(invertedTM.times(Mouse.getCursorPosititon()).getB() * elementMatrix.getYStretch());
	}
	
	@Override
	public void onRelease() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		if (pressed) {
			dragFunction.calculate(elementMatrix, parentMatrix, offset);
		}
		
		super.update(parentMatrix);
		
	}
	
	//******************************** get & set **************************************
	
	public float getYShift() {
		return elementMatrix.getYShift();
	}
	
	public void setYShift(float yShift) {
		elementMatrix.setYShift(yShift);
	}
	
	public boolean isPressed() {
		return pressed;
	}
}
