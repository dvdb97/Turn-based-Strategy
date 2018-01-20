package elements.containers;

import java.util.LinkedList;

import assets.textures.Texture;
import assets.textures.Texture2D;
import elements.Clickable;
import elements.GUIElement;
import gui_core.GUIManager;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public abstract class GUIContainerElement extends GUIElement {
	
	
	private LinkedList<GUIElement> children;
	

	public GUIContainerElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		
		children = new LinkedList<GUIElement>();
	}
	
	
	public GUIContainerElement(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		children = new LinkedList<GUIElement>();
		
	}
	
	
	@Override
	public void render() {
		
		super.render();
		
		if (!super.isVisible()) {
			return;
		}
		
		for (GUIElement element : children) {
			element.render();
		}
		
	}
	
	
	@Override
	public void update() {
		
		super.update();
		
		for (GUIElement element : children) {
			element.update();
		}
		
	}


	@Override
	public boolean processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		//Compute the local space coordinates of the cursor position
		Vector3f vec = new Vector3f(cursorX, cursorY, 1f);
		vec = this.getInvertedRenderingMatrix().times(vec);
		
		
		if (this.getShape().isHit(vec.getA(), vec.getB())) {
			
			for (GUIElement child : children) {
				
				if (child.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown)) {
					return true;
				}
				
			}
			
			if (leftMouseButtonDown) {
				
				onClick();
				
			} else {
				
				onHover();
				
			}
			
			return true;
			
		} 
		
		return false;		
		
	}


	public void add(GUIElement element) {
		children.add(element);
		element.setParent(this);
	}
	
	
	public void remove(GUIElement element) {
		if (children.contains(element)) {
			children.remove(element);
		}
	}
	
	
	public int getNumberOfChildren() {
		return children.size();
	}

}
