package work_in_progress.test;

import assets.meshes.geometry.Color;
import work_in_progress.GUIElementMatrix;
import work_in_progress.GUIWindow;
import work_in_progress.Mouse;
import work_in_progress.Quad;

public class TestWindow extends GUIWindow {

	public TestWindow() {
		super(new Quad(), new Color(0, 255, 255, 255), new GUIElementMatrix(-0.3f, 0, 1, 1));
		
		
		TestToggleButton button = new TestToggleButton(new Color(1f,  0f, 0f, 1f), new GUIElementMatrix(0.2f, -0.2f, 0.4f, 0.4f));
		children.add(button);
		button.setEnableFunc( (e) -> {});//Mouse.getCursorPosititon().print() );
		button.setDisableFunc( (e) -> {});//Mouse.getCursorPosititon().print() );
		
	}
	
	
	
}
