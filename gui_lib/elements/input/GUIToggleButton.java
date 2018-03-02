package elements.input;

import assets.textures.Texture2D;
import rendering.shapes.GUIShape;
import rendering.shapes.implemented.GUIQuad;

public class GUIToggleButton extends GUIButton {
	
	//The texture for the active button
	private static final Texture2D active = new Texture2D("res/fonts/Icons/Button_Activated.png", 600, 200);
	
	//The texture for the inactive button
	private static final Texture2D inactive = new Texture2D("res/fonts/Icons/Button_Inactive.png", 600, 200);
	
	//The relation of the buttons width and height that should be maintained
	private static final float sizeRelation = 6f / 2f;
	
	
	private boolean activated;
	

	public GUIToggleButton(GUIShape shape, float x, float y, float width, float height) {
		super(new GUIQuad(), inactive, x, y, width, height);
		
		this.activated = false;
		
	}


	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		
		super.setHeight(width / sizeRelation);
	}


	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		
		super.setWidth(height * sizeRelation);
	}

}
