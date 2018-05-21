package work_in_progress;

import assets.meshes.geometry.Color;

public abstract class ToggleButton extends ClickableElement {
	
	private boolean enabled;
	
	private Function enableFunc;
	private Function disableFunc;
	
	protected ToggleButton(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onPress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRelease() {
		toggle();
	}
	
	public void toggle() {
		
		enabled = !enabled;
		
		if(enabled)
			enableFunc.execute(this);
		else
			disableFunc.execute(this);
		
	}

	protected void setEnableFunc(Function enableFunc) {
		this.enableFunc = enableFunc;
	}

	protected void setDisableFunc(Function disableFunc) {
		this.disableFunc = disableFunc;
	}
	
}
