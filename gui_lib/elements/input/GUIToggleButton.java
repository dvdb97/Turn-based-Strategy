package elements.input;

import assets.textures.Texture2D;
import elements.GUIElementBase;
import elements.functions.GUIEventHandler;
import rendering.shapes.GUIShape;
import rendering.shapes.implemented.GUIQuad;

public class GUIToggleButton extends GUIButton {
	
	//The texture for the active button
	private static final Texture2D active = new Texture2D("res/Icons/Button_Activated.png", 600, 200);
	
	//The texture for the inactive button
	private static final Texture2D inactive = new Texture2D("res/Icons/Button_Inactive.png", 600, 200);
	
	//The relation of the buttons width and height that should be maintained
	private static final float sizeRelation = 6f / 2f;
	
	
	private boolean activated;
	

	public GUIToggleButton(float x, float y, float width, float height) {
		super(new GUIQuad(), inactive, x, y, width, height);
		
		this.activated = false;
		
		this.setNativeonclickFunc(new GUIEventHandler() {
			@Override
			public void function(GUIElementBase element) {
				activated = activated ? false : true;
				element.setTexture(activated ? active : inactive);
			}
		});
		
	}
	
	
	public GUIToggleButton(float x, float y, float width, float height, String label) {
		this(x, y, width, height);
		
		this.setLabel(label);
		
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
	
	
	public boolean isActivated() {
		return activated;
	}
	
	
	public void setActivated(boolean value) {
		this.activated = value;
	}

}
