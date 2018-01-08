package elements.containers;

import assets.textures.Texture;
import math.vectors.Vector4f;
import rendering.shapes.Shape;

public abstract class Window extends ContainerElement {	
	
	public Window(Shape shape, Texture texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);		
	}
	
	
	public Window(Shape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
	}

}
