package work_in_progress.test;

import static utils.ColorPalette.WHITE;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.Function;
import input.ToggleButton;
import rendering.shapes.GUIQuad;

public class RadioToggleButton  extends ToggleButton {
	
	private Color color1;
	private static Color color2 = WHITE;
	
	private TestFunction<ToggleButton> suspendFunc;
	
	public RadioToggleButton(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, color2, transformationMatrix);
		color1 = color;
	}
	
	@Override
	public void onRelease() {
		if (!suspendFunc.test(this))
			toggle();
	}
	
	public void setSuspendFunc(TestFunction<ToggleButton> suspendFunc) {
		this.suspendFunc = suspendFunc;
	}
	
	@Override
	public void setEnableFunc(Function<ToggleButton> enableFunc) {
		super.setEnableFunc(enableFunc.andThen((e) -> color=color2));
	}
	
	@Override
	public void setDisableFunc(Function<ToggleButton> disableFunc) {
		super.setDisableFunc(disableFunc.andThen((e) -> color=color1));
	}
	
	
}
