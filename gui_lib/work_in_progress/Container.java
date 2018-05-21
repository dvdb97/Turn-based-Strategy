package work_in_progress;

import assets.meshes.geometry.Color;

public abstract class Container extends Element {
	
	
	protected ElementList children;
	
	
	//******************** constructor *******************************
	
	protected Container(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}
	
	
	//*****************************************************************
	
	
	
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
	public boolean processInput() {
		
		if(super.processInput() == false) {
			return false;
		}
		
		children.processInput();
		
		return true;

	}
	
}
