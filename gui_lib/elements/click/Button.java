package elements.click;

import assets.textures.Texture;
import elements.Element;
import elements.functions.GUIFunc;
import math.vectors.Vector4f;
import rendering.shapes.Shape;
import timer.Cooldown;

public abstract class Button extends Element {
	
	private String label;
	
	private Cooldown clickCooldown;
	
	private GUIFunc func;

	public Button(Shape shape, Texture texture, float x, float y, float width, float height) {
		super(shape, texture, x, y, width, height);
		// TODO Auto-generated constructor stub
		
		this.setMovable(false);
		
		clickCooldown = new Cooldown(0.5);
		
		label = null;
		
	}
	
	
	public Button(Shape shape, Texture texture, float x, float y, float width, float height, GUIFunc func) {
		this(shape, texture, x, y, width, height);
		
		this.func = func;
		
	}
	
	
	public Button(Shape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		this.setMovable(false);
		
		clickCooldown = new Cooldown(0.5);
		
		label = null;
		
	}
	
	
	public Button(Shape shape, Vector4f color, float x, float y, float width, float height, GUIFunc func) {
		super(shape, color, x, y, width, height);
		
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
