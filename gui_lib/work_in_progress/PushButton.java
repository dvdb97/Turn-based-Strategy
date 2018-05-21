package work_in_progress;

import assets.meshes.geometry.Color;

public abstract class PushButton extends ClickableElement {
	
	private Function function;
	
	protected PushButton(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onPress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRelease() {
		function.execute(this);
	}
	
	protected void setFunction(Function function) {
		this.function= function;
	}
	
}
