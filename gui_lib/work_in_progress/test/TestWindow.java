package work_in_progress.test;

import assets.meshes.geometry.Color;
import container.GUIWindow;
import dataType.GUIElementMatrix;
import input.Mouse;
import rendering.shapes.Quad;

public class TestWindow extends GUIWindow {

	public TestWindow() {
		super(new Quad(), new Color(0, 255, 255, 255), new GUIElementMatrix(0f, 0f, 1f, 1f));
		
		
		TestToggleButton button = new TestToggleButton(new Color(1f,  0f, 0f, 1f), new GUIElementMatrix(0f, 0f, 0.5f, 0.5f));
		children.add(button);
		button.setEnableFunc( (e) -> {});//Mouse.getCursorPosititon().print() );
		button.setDisableFunc( (e) -> {});//Mouse.getCursorPosititon().print() );
		
	}
	
	
	
}
