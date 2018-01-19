package elements.containers;

import assets.textures.Texture2D;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public abstract class GUIWindow extends GUIContainerElement {	
	
	public GUIWindow(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);		
	}
	
	
	public GUIWindow(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
	}

}
