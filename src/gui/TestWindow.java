package gui;

import assets.textures.Texture2D;
import elements.GUI_Window;
import rendering.shapes.Rectangle;

public class TestWindow extends GUI_Window {
	
	public TestWindow(Texture2D texture, float xPos, float yPos, float width, float height) {
		super(texture, xPos, yPos, width, height);
		
		this.setShape(new Rectangle());
	}

	
	public TestWindow(String texturePath, float xPos, float yPos, float width, float height) {
		super(texturePath, xPos, yPos, width, height);
		
		this.setShape(new Rectangle());;
	}

	
	@Override
	public void onHover() {
		System.out.println("I was hovered!");		
	}

	@Override
	public void onLeave() {
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

}
