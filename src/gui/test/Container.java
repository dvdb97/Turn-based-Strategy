package gui.test;

import elements.containers.GUIContainerElement;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;
import rendering.shapes.implemented.GUIRoundedQuad;

public class Container extends GUIContainerElement {

	public Container() {
		super(new GUIQuad(), new Vector4f(0.5f, 0.5f, 0.5f, 1f), 0.01f, -0.01f, 0.8f, 0.8f);
	
	}

}
