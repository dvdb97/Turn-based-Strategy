package fundamental;

import assets.meshes.geometry.Color;
import dataType.ElementList;
import dataType.GUIElementMatrix;
import rendering.shapes.implemented.GUIQuad;

public abstract class Container extends Element {
	
	
	protected ElementList children;
	
	
	//******************** constructor *******************************
	
	protected Container(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		
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