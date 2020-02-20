package implementations;

import rendering.shapes.implemented.GUIEllipse;

public class TestCircleElement extends TestElement {

	public TestCircleElement(int width, int height) {
		super(new GUIEllipse(defaultColor), width, height);
	}
	
	
	public TestCircleElement(float widthPercent, float heightPercent) {
		super(new GUIEllipse(defaultColor), widthPercent, heightPercent);
	}

}
