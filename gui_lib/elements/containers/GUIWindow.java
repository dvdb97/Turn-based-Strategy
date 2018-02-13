package elements.containers;

import assets.textures.Texture2D;
import elements.GUITransformable;
import gui_core.GUIManager;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public abstract class GUIWindow extends GUIContainerElement implements GUITransformable {
	
	private boolean resizable;
	
	private boolean movable;


	public GUIWindow(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		GUIManager.addWindow(this);
		
		this.resizable = true;
		
		this.movable = true;
		
	}
	
	
	public GUIWindow(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		GUIManager.addWindow(this);
		
		this.resizable = true;
		
		this.movable = true;
		
	}
	
	
	@Override
	public void onClick() {
		
		super.onClick();
		
		if (isMovable()) {
			return;
		}
		
	}


	@Override
	public void move(float x, float y) {
		
		this.setX(x);
		
		this.setY(y);
		
	}

	
	@Override
	public void resize(float rx, float ry) {
		
		if (isResizable()) {
			return;
		}
		
	}
	
	
	private float getAbsoluteValue(float value) {
		
		return value > 0f ? value : -value;
		
	}
	
	
	public boolean isResizable() {
		return resizable;
	}


	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}


	public boolean isMovable() {
		return movable;
	}


	public void setMovable(boolean movable) {
		this.movable = movable;
	}


	@Override
	public void delete() {
		super.delete();
		
		GUIManager.remove(this);
	}

}
