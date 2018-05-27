package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.DragFunction;
import function.TestDragFunction;
import fundamental.ClickableElement;
import rendering.shapes.GUIQuad;

public class Handle extends ClickableElement {
	
	private DragFunction dragFunction;
	
	public Handle(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		dragFunction = new TestDragFunction();
	}
	
	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onRelease() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		if (pressed) {
			dragFunction.calculate(elementMatrix, parentMatrix);
		}
		
		super.update(parentMatrix);
		
	}
	
	@Override
	public void reset() {
		
		//reset to original state
		
	}

	
	//******************************** get & set **************************************
	
	public float getYShift() {
		return elementMatrix.getYShift();
	}
	
	public void setYShift(float yShift) {
		elementMatrix.setYShift(yShift);
	}
	
}
