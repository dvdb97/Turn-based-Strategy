package input;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.Function;
import fundamental.Button;
import rendering.shapes.Shape;

public abstract class ToggleButton extends Button {
	
	private boolean enabled;
	
	private Function<ToggleButton> enableFunc;
	private Function<ToggleButton> disableFunc;
	
	protected ToggleButton(Shape shape, Color color1, Color color2, GUIElementMatrix transformationMatrix) {
		super(shape, color1, transformationMatrix);
	}
	
	@Override
	public void onClick() {

	}
	
	@Override
	public void onRelease() {
		toggle();
	}
	
	@Override
	public void reset() {
		super.reset();
	}
	
	public void toggle() {
		enabled = !enabled;
		
		if(enabled)
			enableFunc.execute(this);
		else
			disableFunc.execute(this);
		
		
	}
	
	protected void setEnableFunc(Function<ToggleButton> enableFunc) {
		this.enableFunc = enableFunc;
	}
	
	protected void setDisableFunc(Function<ToggleButton> disableFunc) {
		this.disableFunc = disableFunc;
	}
	
}
