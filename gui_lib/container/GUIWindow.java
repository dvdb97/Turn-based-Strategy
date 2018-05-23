package container;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.Container;
import gui_core.GUIManager;
import rendering.shapes.Shape;

public abstract class GUIWindow extends Container {
	//TODO: element matrix vs transformation matrix
	public GUIWindow(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		
		super(shape, color, transformationMatrix);
		
		GUIManager.addWindow(this);
		
	}
	
	public void update() {
		update(new GUIElementMatrix());
	}
	
}
