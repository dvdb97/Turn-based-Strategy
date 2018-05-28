package work_in_progress.test;

import assets.meshes.geometry.Color;
import container.GUIWindow;
import dataType.GUIElementMatrix;
import rendering.shapes.Shape;

import static utils.ColorPalette.*;

public class DragableWindow extends GUIWindow {
	
	private DragBar bar;
	
	public DragableWindow(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		
		bar = new DragBar(GREEN, new GUIElementMatrix(0, 0, 1, 0.1f));
		children.add(bar);
		
	}
	
	@Override
	public void update() {
	//	this.elementMatrix.setXShift(bar.getX());
	//	this.elementMatrix.setYShift(bar.getY());
		super.update();
	}
	
}
