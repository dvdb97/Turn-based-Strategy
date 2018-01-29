package gui.test;

import elements.containers.GUIContainerElement;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;
import rendering.shapes.implemented.GUIRoundedQuad;

public class Container extends GUIContainerElement {

	public Container() {
		super(new GUIQuad(), new Vector4f(0.5f, 0.5f, 0.5f, 1f), 0.01f, -0.01f, 0.8f, 0.8f);
	
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHover() {
		System.out.println("inner container hovered!");		
	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrag() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		
	}

}
