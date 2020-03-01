package input;

import function.Function;
import fundamental.GUIContainer;
import moving_parts.Handle;
import rendering.shapes.implemented.GUIQuad;
import assets.meshes.geometry.Color;

public class Slider extends GUIContainer<Handle> {
	
	private Handle handle;
	
	private Function<Slider> function;
	
	
	public Slider(int width, int height, Color sliderColor, Color handleColor, FlexDirection direction) {
		super(new GUIQuad(sliderColor), width, height, direction);
		
		//handle = new Handle(handleColor, width, height);
		addChild(handle);
	}
	
	
	public void setFunction(Function<Slider> function) {
		this.function = function;
	}
	
	
	public float getValue() {
		//TODO
		return 0f;
	}
	
	
	public void setValue(float value) {
		//TODO
	}

}
