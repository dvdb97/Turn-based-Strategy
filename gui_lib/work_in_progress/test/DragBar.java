package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.DragFunction;
import fundamental.ClickableElement;
import graphics.matrices.TransformationMatrix;
import input.Mouse;
import math.vectors.Vector3f;
import rendering.shapes.GUIQuad;

public class DragBar extends ClickableElement {
	
	private DragFunction dragFunction;
	
	public DragBar(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		
		dragFunction = new DragFunction() {
			
			@Override
			public void calculate(GUIElementMatrix elementMatrix, GUIElementMatrix parentMatrix) {
				
				parentMatrix.setXShift(Mouse.getCursorX()-0.1f);
				parentMatrix.setYShift(Mouse.getCursorY()+0.02f);
				
			}
		};
		
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

	public float getX() {
		return TM.getXShift();
	}
	
	public float getY() {
		return TM.getYShift();
	}
	
}
