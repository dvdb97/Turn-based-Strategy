package gui.windows;

import elements.containers.GUIWindow;
import gui.shapes.Quad;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public class ImplementedWindow extends GUIWindow {

	public ImplementedWindow(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(new Quad(), color, x, y, width, height);
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHover() {
		// TODO Auto-generated method stub
		
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

	@Override
	public void close() {
		super.delete();
	}

}
