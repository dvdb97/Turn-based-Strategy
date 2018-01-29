package gui.test;

import elements.containers.GUIWindow;
import math.vectors.Vector4f;
import rendering.shapes.implemented.GUIQuad;

public class ImplementedWindow extends GUIWindow {

	public ImplementedWindow(Vector4f color, float x, float y, float width, float height) {
		super(new GUIQuad(), new Vector4f(1f, 0f, 0f, 1f), x, y, width, height);
	
		TestElement element = new TestElement();
		Container container = new Container();
		container.add(element);
		this.add(container);
		
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHover() {
		System.out.println("window hovered!");
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
