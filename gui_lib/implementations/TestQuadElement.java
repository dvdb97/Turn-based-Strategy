package implementations;

import rendering.shapes.implemented.GUIQuad;

public class TestQuadElement extends TestElement {

	public TestQuadElement(int width, int height) {
		super(new GUIQuad(defaultColor), width, height);
	}

}
