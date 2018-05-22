package elements.containers;

import assets.textures.Texture2D;
import elements.fundamental.GUITransformable;
import gui_core.GUIManager;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public abstract class GUIWindow_2 extends GUIContainerElement implements GUITransformable {

	public GUIWindow_2(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
	//	GUIManager.addWindow(this);
		
	}
	
	
	public GUIWindow_2(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
	//	GUIManager.addWindow(this);
		
	}
	
	
	@Override
	public void onClick() {
		
		super.onClick();
		
		if (isMovable()) {
			return;
		}
		
	}


	public void move(float x, float y) {
		
		this.setX(x);
		
		this.setY(y);
		
	}


	public void resize(float rx, float ry) {
		
		if (isResizable()) {
			return;
		}
		
	}
	

	@Override
	public void delete() {
		super.delete();
		
	//	GUIManager.remove(this);
	}

}
