package elements.containers;

import java.util.LinkedList;
import assets.textures.Texture2D;
import elements.fundamental.GUIElement;
import elements.fundamental.GUIElementBase;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public abstract class GUIContainerElement extends GUIElement {
	
	
	private LinkedList<GUIElementBase> children;
	

	public GUIContainerElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		children = new LinkedList<GUIElementBase>();
	}
	
	
	public GUIContainerElement(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		children = new LinkedList<GUIElementBase>();
	}
	
	
	@Override
	public void render() {
		
		if (!super.isVisible()) {
			return;
		}
		
		super.render();
		
		for (GUIElementBase element : children) {
			element.render();
		}
		
	}
	
	
	@Override
	public void update() {
		
		super.update();
		
		for (GUIElementBase element : children) {
			element.update();
		}
		
	}


	@Override
	public boolean processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		//Compute the local space coordinates of the cursor position
		Vector4f vec = new Vector4f(cursorX, cursorY, 1f, 1f);
		vec = this.getInvertedRenderingMatrix().times(vec);
		
		
		if (!this.getShape().isHit(vec.getA(), vec.getB())) {
			return false;
		}
		
		
		//process childrens' input
		for (GUIElementBase child : children) {
			
			if (child.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown)) {
				return true;
			}
			
		}
		//
		
		//process this's input
		if (leftMouseButtonDown) {
			
			onClick();
			
		} else {
			
			onHover();
			
		}
		//
		
		
		return true;
			
		
	}
	

	public void addChild(GUIElementBase element) {
		children.add(element);
		element.setParent(this);
	}
	
	
	public void removeChild(GUIElementBase element) {
		if (children.contains(element)) {
			children.remove(element);
		}
	}
	
	
	public int getNumberOfChildren() {
		return children.size();
	}

}
