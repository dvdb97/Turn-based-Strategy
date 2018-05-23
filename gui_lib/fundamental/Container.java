package fundamental;

import assets.meshes.geometry.Color;
import dataType.ElementList;
import dataType.GUIElementMatrix;
import rendering.shapes.Shape;

public abstract class Container extends Element {
	
	
	protected ElementList children;
	
	
	//******************** constructor *******************************
	
	protected Container(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		
		children = new ElementList();
		
	}
	
	
	//*****************************************************************
	
	
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		super.update(parentMatrix);
		children.update(this.TM);
		
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
