package work_in_progress;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.DragFunction;
import fundamental.DragableElement;
import input.Mouse;
import math.vectors.Vector2f;
import rendering.shapes.implemented.GUIQuad;

public class DragBar extends DragableElement {
	
	private DragFunction dragFunction;
	
	private Color color1;
	private Color color2;
	
	private Vector2f offset;
	
	private Moveable movedElement;
	
	public DragBar(Color color, GUIElementMatrix transformationMatrix, Moveable movedElement) {
		super(new GUIQuad(), color, transformationMatrix);
		
		color1 = color;
		color2 = utils.ColorPalette.RED;
		
		this.movedElement = movedElement;
		
		offset = new Vector2f(0, 0);
		
		dragFunction = new DragFunction() {
			
			@Override
			public void calculate(GUIElementMatrix elementMatrix, GUIElementMatrix parentMatrix, Vector2f offset) {
				
				movedElement.getTransformationMatrix().setXShift(Mouse.getCursorX() - offset.getA());
				movedElement.getTransformationMatrix().setYShift(Mouse.getCursorY() - offset.getB());
				
			}
		};
		
	}
	
	
	@Override
	public void onClick() {
		color = color2;
		offset.setA(Mouse.getCursorX() - movedElement.getTransformationMatrix().getXShift());
		offset.setB(Mouse.getCursorY() - movedElement.getTransformationMatrix().getYShift());
	}

	@Override
	public void onRelease() {
		color = color1;
	}
	
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		if (pressed) {
			dragFunction.calculate(elementMatrix, parentMatrix, offset);
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
