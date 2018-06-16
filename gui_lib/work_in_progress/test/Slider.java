package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.Function;
import fundamental.Container;
import rendering.shapes.GUIQuad;
import static utils.ColorPalette.*;

//TODO: a lot to improve here
public class Slider extends Container {
	
	private Handle handle;
	
	private Function<Slider> function;
	
	//********************************************************************************
	
	public Slider(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		
		handle = new Handle(BLACK, new GUIElementMatrix(0, 0, 1, 0.1f));
		children.add(handle);
	}
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		if(function != null)
			function.execute(this);
		
		super.update(parentMatrix);
		
	}
	
	//******************************** get & set **************************************
	
	public void setFunction(Function<Slider> function) {
		this.function = function;
	}
	
	public float getValue() {
		return (handle.getYShift()+0.9f)/0.9f;
	}
	
	public void setValue(float value) {
		handle.setYShift(value*0.9f-0.9f);
	}
}
