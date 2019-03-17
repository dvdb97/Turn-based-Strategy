package container;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.GUIWindow;

import static utils.ColorPalette.*;

public class DragableWindow extends GUIWindow implements Moveable {
	
	private DragBar bar;
	
	public DragableWindow(Color color, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		bar = new DragBar(GREEN, new GUIElementMatrix(0, 0, 1, 0.1f), this);
		children.add(bar);
		
	}
	
	@Override
	public void update() {
		this.elementMatrix.setXShift(this.TM.getXShift());
		this.elementMatrix.setYShift(this.TM.getYShift());
		super.update();
	}

	@Override
	public GUIElementMatrix getTransformationMatrix() {
		return this.TM;
	}
	
}
