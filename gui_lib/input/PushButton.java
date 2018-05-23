package input;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.Function;
import fundamental.ClickableElement;
import rendering.shapes.Shape;

//TODO: bullshit

public abstract class PushButton extends ClickableElement {
	
	private Function<PushButton> function;
	
	private Function<PushButton> onClickFunc;
	private Function<PushButton> resetFunc;
	
	protected PushButton(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onClick() {
		if(onClickFunc != null)
			onClickFunc.execute(this);
	}
	
	@Override
	public void onRelease() {
		if(function != null)
			function.execute(this);
	}
	
	@Override
	public void reset() {
		if(resetFunc != null)
			resetFunc.execute(this);
	}
	
	protected void setFunction(Function<PushButton> function) {
		this.function= function;
	}
	
}
