package elements.containers;

import java.util.LinkedList;

import assets.textures.Texture;
import elements.Clickable;
import elements.Element;
import math.vectors.Vector4f;
import rendering.shapes.Shape;

public abstract class ContainerElement extends Element {
	
	
	private LinkedList<Element> children;
	

	public ContainerElement(Shape shape, Texture texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		children = new LinkedList<Element>();
	}
	
	
	public ContainerElement(Shape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		children = new LinkedList<Element>();
		
	}
	
	
	public void add(Element element) {
		children.add(element);
		element.setParent(this);
	}
	
	
	public void remove(Element element) {
		if (children.contains(element)) {
			children.remove(element);
		}
	}
	
	
	public int getNumberOfChildren() {
		return children.size();
	}

}
