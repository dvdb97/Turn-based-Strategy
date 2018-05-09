package gui.test;

import elements.fundamental.GUILabeledElement;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;

public class TestElement extends GUILabeledElement {

	public TestElement() {
		super(new GUIQuad(), new Vector4f(1f, 1f, 0f, 1f), 0.01f, -0.01f, 0.8f, 0.8f);
		
		this.setOnclickFunc(new OnHoverHandler());
		
		this.setLabel("Hello!");
		
	}

}
