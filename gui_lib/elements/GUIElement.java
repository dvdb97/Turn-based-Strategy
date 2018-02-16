package elements;

import assets.textures.Texture2D;
import elements.functions.GUIEventHandler;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;
import timer.Cooldown;

public abstract class GUIElement extends GUIElementBase {
	
	private GUIEventHandler onclickFunc = null;
	
	private Cooldown onclickCooldown;
	
	private GUIEventHandler onHoverFunc = null;
	
	private GUIEventHandler onDragFunc = null;
	
	private GUIEventHandler onCloseFunc = null;
	
	private GUIEventHandler onOpenFunc = null;
		

	public GUIElement(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		onclickCooldown = new Cooldown(0.4);
		
	}

	public GUIElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		onclickCooldown = new Cooldown(0.4);
		
	}
	
	
	@Override
	public void onClick() {
		
		if (onclickFunc == null) {
			return;
		}
		
		if (!onclickCooldown.isFinished()) {
			System.out.println("Onclick is still on cooldown!");
			return;
		}
		
		onclickFunc.function(this);
		
		onclickCooldown.start();
		
	}
	
	
	public GUIEventHandler getOnclickFunc() {
		return onclickFunc;
	}
	

	public void setOnclickFunc(GUIEventHandler onclickFunc) {
		this.onclickFunc = onclickFunc;
	}
	

	@Override
	public void onHover() {
		
		if (onHoverFunc == null) {
			return;
		}
		
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
		
		if (onDragFunc == null) {
			return;
		}
		
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
		
		if (onCloseFunc == null) {
			return;
		}
		
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
		
		if (onOpenFunc == null) {
			return;
		}
		
		onOpenFunc.function(this);
		
	}
	

	public GUIEventHandler getOnOpenFunc() {
		return onOpenFunc;
	}
	

	public void setOnOpenFunc(GUIEventHandler onOpenFunc) {
		this.onOpenFunc = onOpenFunc;
	}

}
