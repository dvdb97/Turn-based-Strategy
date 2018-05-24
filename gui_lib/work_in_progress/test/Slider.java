package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import fundamental.Container;
import rendering.shapes.GUIQuad;
import utils.Percentage;

import static utils.ColorPalette.*;

//TODO: a lot to improve here
public class Slider extends Container {
	
	private Handle handle;
	
	//********************************************************************************
	
	public Slider(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		
		handle = new Handle(BLACK, new GUIElementMatrix(0, 0, 1, 0.1f));
		children.add(handle);
	}
	
	//******************************** get & set **************************************
	
	public float getValue() {
		return -handle.getYShift()/0.9f;
	}
	
	public void setValue(float value) {
		handle.setYShift(-value*0.9f);
	}
}
