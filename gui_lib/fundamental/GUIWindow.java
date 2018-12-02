package fundamental;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import gui_core.GUIManager;
import input.Mouse;
import rendering.shapes.Shape;

public abstract class GUIWindow extends Container implements Clickable {
	//TODO: element matrix vs transformation matrix
	public GUIWindow(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		
		super(shape, color, transformationMatrix);
		
		GUIManager.addWindow(this);
		
	}
	
	public void update() {
		update(new GUIElementMatrix());
	}
	
	@Override
	public boolean processInput() {
		
		boolean b = super.processInput();
		if (b && Mouse.isLeftClicked()) {
			onClick();
		}
		
		return b;
	}
	
	public void onClick() {
		GUIManager.setPrimaryWindow(this);
	}
	
	public void onRelease() {
		//do nothing
	}
}
