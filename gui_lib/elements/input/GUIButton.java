package elements.input;

import assets.textures.Texture2D;
import elements.functions.GUIEventHandler;
import elements.fundamental.GUILabeledElement;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public abstract class GUIButton extends GUILabeledElement{
	
	public GUIButton(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		this.setMovable(false);
		
	}
	
	
	public GUIButton(GUIShape shape, Texture2D texture, float x, float y, float width, float height, GUIEventHandler func) {
		this(shape, texture, x, y, width, height);
		
	}
	
	
	public GUIButton(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		this.setMovable(false);
		
	}
	
	
	public GUIButton(GUIShape shape, Vector4f color, float x, float y, float width, float height, GUIEventHandler func) {
		this(shape, color, x, y, width, height);
		
	}
		
}
