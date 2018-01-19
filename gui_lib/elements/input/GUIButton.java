package elements.input;

import assets.textures.Texture;
import assets.textures.Texture2D;
import elements.GUIElement;
import elements.functions.GUIFunc;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;
import timer.Cooldown;

public abstract class GUIButton extends GUIElement {
	
	private String label;
	
	private Cooldown clickCooldown;
	
	private GUIFunc func;

	public GUIButton(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		// TODO Auto-generated constructor stub
		
		this.setMovable(false);
		
		clickCooldown = new Cooldown(0.5);
		
		label = null;
		
	}
	
	
	public GUIButton(GUIShape shape, Texture2D texture, float x, float y, float width, float height, GUIFunc func) {
		this(shape, texture, x, y, width, height);
		
		this.func = func;
		
	}
	
	
	public GUIButton(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		this.setMovable(false);
		
		clickCooldown = new Cooldown(0.5);
		
		label = null;
		
	}
	
	
	public GUIButton(GUIShape shape, Vector4f color, float x, float y, float width, float height, GUIFunc func) {
		this(shape, color, x, y, width, height);
		
		this.func = func;
	}


	@Override
	public void onClick() {
		if (clickCooldown.isFinished()) {
			func.function(this);
			
			clickCooldown.start();
		}		
	}
	
	
	public void setOnclickFunction(GUIFunc func) {
		this.func = func;
	}
	
	
	public void setLabel(String label) {
		this.label = label;
	}
		
}
