package elements.interaction;

import assets.meshes.geometry.Shape;
import assets.textures.Texture2D;
import elements.Element;
import interfaces.Clickable;

public class Button extends Element implements Clickable {
	
	private OnClickFunc function;
	
	private boolean clicked = false;
	
	
	public Button(rendering.shapes.Shape shape, Texture2D texture, float xPos, float yPos, float width, float height, OnClickFunc func) {
		super(shape, texture, xPos, yPos, width, height);
		
		this.function = func;
	}


	public Button(String texturePath, float xPos, float yPos, float width, float height, OnClickFunc func) {
		super(texturePath, xPos, yPos, width, height);
		
		this.function = func;
	}


	public Button(Texture2D texture, float xPos, float yPos, float width, float height, OnClickFunc func) {
		super(texture, xPos, yPos, width, height);
		
		this.function = func;
	}


	@Override
	public void processMouseInput(float CursorX, float CursorY, boolean click) {
		if (this.isCursorOnElement(CursorX, CursorY)) {
			onHover();
		}
		
	}
	

	@Override
	public void onClick() {
		function.onClick();
		
		
		
	}

	@Override
	public void onHover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeave() {
		// TODO Auto-generated method stub
		
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


	@Override
	public void onHold() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRelease() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

}
