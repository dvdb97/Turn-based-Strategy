package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.Function;
import input.PushButton;
import rendering.shapes.GUIQuad;

public class TestPushButton extends PushButton {

	public TestPushButton(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}
	
	public void setFunction(Function<PushButton> function) {
		super.setFunction(function);
	}
	
}