package implementations;

import rendering.shapes.implemented.GUIRoundedRect;

public class TestRoundedRectElement extends TestElement {
	
	public TestRoundedRectElement(int width, int height, int radius) {
		super(new GUIRoundedRect(defaultColor, radius), width, height);
	}
	
}
