package work_in_progress;

import assets.meshes.geometry.Color;

public abstract class Container extends Element {
	
	
	//******************** constructor *******************************
	
	protected Container(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}
	
	
	//*****************************************************************
	
	
	protected ElementList children;
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		super.update(parentMatrix);
		children.update(parentMatrix);
		
	}
	
	@Override
	public void render() {
		
		super.render();
		children.render();
		

	}
	
	@Override
	public boolean processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		if(super.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown) == false) {
			return false;
		}
		
		children.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown);
		
		return true;

	}
}
