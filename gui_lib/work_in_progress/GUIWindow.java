package work_in_progress;

import assets.meshes.geometry.Color;
import gui_core.GUIManager;

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
