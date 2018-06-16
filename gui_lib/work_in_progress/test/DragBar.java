package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.DragFunction;
import fundamental.Button;
import fundamental.ClickableElement;
import fundamental.DragableElement;
import graphics.matrices.TransformationMatrix;
import input.Mouse;
import math.vectors.Vector3f;
import rendering.shapes.GUIQuad;

public class DragBar extends DragableElement {
	
	private DragFunction dragFunction;
	
	private Color color1;
	private Color color2;
	
	private float x;
	private float y;
	
	public DragBar(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		
		color1 = color;
		color2 = utils.ColorPalette.RED;
		
		dragFunction = new DragFunction() {
			
			@Override
			public void calculate(GUIElementMatrix elementMatrix, GUIElementMatrix parentMatrix) {
				
				parentMatrix.setXShift(Mouse.getCursorX() - x + parentMatrix.getXShift());
				parentMatrix.setYShift(Mouse.getCursorY() - y + parentMatrix.getYShift());
				
			}
		};
		
	}
	
	
	@Override
	public void onClick() {
		color = color2;
		x = Mouse.getCursorX();
		y = Mouse.getCursorY();
	}

	@Override
	public void onRelease() {
		color = color1;
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
