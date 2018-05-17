package work_in_progress;

import assets.meshes.geometry.Color;

public abstract class Window extends Container {
	
	public Window(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		
		super(shape, color, transformationMatrix);
		
		//TODO:
		//GUIManager.addWindow(this);
		
	}
	
	public void update() {
		update(new GUIElementMatrix());
	}
	
}
