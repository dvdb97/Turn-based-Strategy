package elements;

import assets.textures.Texture2D;
import elements.functions.GUIEventHandler;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public abstract class GUIElement extends GUIElementBase {
	
	private GUIEventHandler onclickFunc = null;
	
	private GUIEventHandler onHoverFunc = null;
	
	private GUIEventHandler onDragFunc = null;
	
	private GUIEventHandler onCloseFunc = null;
	
	private GUIEventHandler onOpenFunc = null;
		

	public GUIElement(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
	}

	public GUIElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
	}
	
	
	@Override
	public void onClick() {
		onclickFunc.function(this);		
	}
	
	
	public GUIEventHandler getOnclickFunc() {
		return onclickFunc;
	}
	

	public void setOnclickFunc(GUIEventHandler onclickFunc) {
		this.onclickFunc = onclickFunc;
	}
	

	@Override
	public void onHover() {
		onHoverFunc.function(this);		
	}
	
	
	public GUIEventHandler getOnHoverFunc() {
		return onHoverFunc;
	}
	

	public void setOnHoverFunc(GUIEventHandler onHoverFunc) {
		this.onHoverFunc = onHoverFunc;
	}
	

	@Override
	public void onDrag() {
		onDragFunc.function(this);		
	}
	
	
	public GUIEventHandler getOnDragFunc() {
		return onDragFunc;
	}
	

	public void setOnDragFunc(GUIEventHandler onDragFunc) {
		this.onDragFunc = onDragFunc;
	}
	
	
	@Override
	public void onClose() {
		onCloseFunc.function(this);		
	}
	
	
	public GUIEventHandler getOnCloseFunc() {
		return onCloseFunc;
	}
	

	public void setOnCloseFunc(GUIEventHandler onCloseFunc) {
		this.onCloseFunc = onCloseFunc;
	}
	

	@Override
	public void onOpen() {
		onOpenFunc.function(this);		
	}
	

	public GUIEventHandler getOnOpenFunc() {
		return onOpenFunc;
	}
	

	public void setOnOpenFunc(GUIEventHandler onOpenFunc) {
		this.onOpenFunc = onOpenFunc;
	}

}
