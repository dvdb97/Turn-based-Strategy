package elements;

import rendering.shapes.Shape;
import assets.textures.Texture2D;
import interfaces.Clickable;

public abstract class ClickableElement extends Element implements Clickable {

	private boolean wasClicked = false;
	
	private boolean wasHovered = false;
	
	private float lastCursorXPos = 0.0f;
	
	private float lastCursorYPos = 0.0f;
	
	
	public ClickableElement(Shape shape, Texture2D texture, float xPos, float yPos, float width, float height) {
		super(shape, texture, xPos, yPos, width, height);
	}


	public ClickableElement(String texturePath, float xPos, float yPos, float width, float height) {
		super(texturePath, xPos, yPos, width, height);
	}


	public ClickableElement(Texture2D texture, float xPos, float yPos, float width, float height) {
		super(texture, xPos, yPos, width, height);
	}


	@Override
	public void processMouseInput(float CursorX, float CursorY, boolean click) {
		
		if(this.isCursorOnElement(CursorX, CursorY)) {
			
			if (!wasHovered) {
				onHover();
				wasHovered = true;
			}
			
			if (click) {
				onClick();
				wasClicked = true;
			} else {
				wasClicked = false;
			}
			
		} else {
			
			wasHovered = false;
			
		}
		
		this.lastCursorXPos = CursorX;
		this.lastCursorYPos = CursorY;
		
	}


	@Override
	public void move(float x, float y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resize(float x, float y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resize(float factor) {
		// TODO Auto-generated method stub
		
	}
	
	
	protected boolean wasClicked() {
		return wasClicked;
	}
	
	
	protected boolean wasHovered() {
		return wasHovered;
	}
	
	
	protected float getLastCursorXPosition() {
		return lastCursorXPos;
	}
	
	
	protected float getLastCursorYPosition() {
		return lastCursorYPos;
	}

}
