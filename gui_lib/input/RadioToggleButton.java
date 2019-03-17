package input;

import static utils.ColorPalette.WHITE;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.BooleanFunction;
import function.Function;
import rendering.shapes.implemented.GUIQuad;

public class RadioToggleButton  extends ToggleButton {
	
	private Color color1;
	private static Color color2 = WHITE;
	
	private BooleanFunction<ToggleButton> suspendFunc;
	
	public RadioToggleButton(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, color2, transformationMatrix);
		color1 = color;
	}
	
	@Override
	public void onRelease() {
		if (!suspendFunc.test(this))
			toggle();
	}
	
	public void setSuspendFunc(BooleanFunction<ToggleButton> suspendFunc) {
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
