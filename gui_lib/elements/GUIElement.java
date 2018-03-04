package elements;

import assets.textures.Texture2D;
import elements.functions.GUIEventHandler;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;
import timer.Cooldown;

public abstract class GUIElement extends GUIElementBase {
	
	//The onClick function that can be specified when creating an object
	private GUIEventHandler onClickFunc = null;
	
	//The onClick function that can be set to specify the behavior of a subclass
	private GUIEventHandler nativeOnClickFunc = null;
	
	//The cooldown for the onClick event
	private Cooldown onClickCooldown;
	
	
	//The onHover function that can be specified when creating an object
	private GUIEventHandler onHoverFunc = null;
	
	//The onHover function that can be set to specify the behavior of a subclass
	private GUIEventHandler nativeOnHoverFunc = null;
	
	
	//The onDrag function that can be specified when creating an object
	private GUIEventHandler onDragFunc = null;
	
	//The onDrag function that can be set to specify the behavior of a subclass
	private GUIEventHandler nativeOnDragFunc = null;
	
	
	//The onClose function that can be specified when creating an object
	private GUIEventHandler onCloseFunc = null;
	
	//The onClose function that can be set to specify the behavior of a subclass
	private GUIEventHandler nativeOnCloseFunc = null;
	
	
	//The onOpen function that can be specified when creating an object
	private GUIEventHandler onOpenFunc = null;
	
	//The onOpen function that can be set to specify the behavior of a subclass
	private GUIEventHandler nativeOnOpenFunc = null;
		

	public GUIElement(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		onClickCooldown = new Cooldown(0.4);
		
	}

	public GUIElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		onClickCooldown = new Cooldown(0.4);
		
	}
	
	
	//****************************************** onClick ******************************************
	
	
	@Override
	public void onClick() {
		
		if (!onClickCooldown.isFinished()) {
			System.out.println("Onclick is still on cooldown!");
			return;
		}
		
		if (nativeOnClickFunc != null) {
			nativeOnClickFunc.function(this);
		}
		
		if (onClickFunc != null) {
			onClickFunc.function(this);
		}
		
		onClickCooldown.start();
		
	}
	
	
	public GUIEventHandler getOnclickFunc() {
		return onClickFunc;
	}
	

	public void setOnclickFunc(GUIEventHandler onclickFunc) {
		this.onClickFunc = onclickFunc;
	}
	
	
	protected GUIEventHandler getNativeOnclickFunc() {
		return nativeOnClickFunc;
	}
	
	
	protected void setNativeonclickFunc(GUIEventHandler nativeOnclickFunc) {
		this.nativeOnClickFunc = nativeOnclickFunc;
	}
	
	
	//****************************************** onHover ******************************************
	

	@Override
	public void onHover() {
		
		if (nativeOnHoverFunc != null) {
			nativeOnHoverFunc.function(this);
		}
		
		if (onHoverFunc != null) {
			onHoverFunc.function(this);	
		}
				
	}
	
	
	public GUIEventHandler getOnHoverFunc() {
		return onHoverFunc;
	}
	

	public void setOnHoverFunc(GUIEventHandler onHoverFunc) {
		this.onHoverFunc = onHoverFunc;
	}
	
	
	protected GUIEventHandler getNativeOnhoverFunc() {
		return nativeOnHoverFunc;
	}
	
	
	protected void setNativeOnhoverFunc(GUIEventHandler nativeOnhoverFunc) {
		this.nativeOnHoverFunc = nativeOnhoverFunc;
	}
	
	
	//****************************************** onDrag ******************************************
	

	@Override
	public void onDrag() {
		
		if (nativeOnDragFunc != null) {
			nativeOnDragFunc.function(this);
		}
		
		if (onDragFunc != null) {
			onDragFunc.function(this);
		}
		
	}
	
	
	public GUIEventHandler getOnDragFunc() {
		return onDragFunc;
	}
	

	public void setOnDragFunc(GUIEventHandler onDragFunc) {
		this.onDragFunc = onDragFunc;
	}
	
	
	protected GUIEventHandler getNativeOnDragFunc() {
		return nativeOnDragFunc;
	}
	
	
	protected void setNativeOnDragFunc(GUIEventHandler nativeOnDragFunc) {
		this.nativeOnDragFunc = nativeOnDragFunc;
	}
	
	
	//****************************************** onClose ******************************************
	
	
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
	
	
	protected GUIEventHandler getNativeOnCloseFunc() {
		return nativeOnCloseFunc;
	}
	
	
	protected void setNativeOnCloseFunc(GUIEventHandler nativeOnCloseFunc) {
		this.nativeOnCloseFunc = nativeOnCloseFunc;
	}
	
	
	//****************************************** onOpen ******************************************
	

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
	
	
	protected GUIEventHandler getNativeOnOpenFunc() {
		return nativeOnOpenFunc;
	}
	
	
	protected void setNativeOnOpenFunc(GUIEventHandler nativeOnOpenFunc) {
		this.nativeOnOpenFunc = nativeOnOpenFunc;
	}

}
