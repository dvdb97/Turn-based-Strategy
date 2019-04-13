package container;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.GUIWindow;
import fundamental.Moveable;
import work_in_progress.DragBar;

import static utils.ColorPalette.*;

public class DragableWindow extends GUIWindow implements Moveable {
	
	private DragBar bar;
	
	public DragableWindow(Color color, GUIElementMatrix elementMatrix) {
		super(color, elementMatrix);
		
		bar = new DragBar(GREEN, new GUIElementMatrix(0, 0, 1, 0.1f), this);
		children.add(bar);
		
	}
	
	@Override
	public void update() {
		super.update();
	}

	@Override
	public GUIElementMatrix getTransformationMatrix() {
		return this.TM;
	}
	
	@Override
	public void onConcludedMovement() {
		this.elementMatrix.setXShift(this.TM.getXShift());
		this.elementMatrix.setYShift(this.TM.getYShift());
	}
	
}
