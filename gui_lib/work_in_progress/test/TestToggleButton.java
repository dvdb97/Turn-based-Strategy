package work_in_progress.test;

import assets.meshes.geometry.Color;
import work_in_progress.Function;
import work_in_progress.GUIElementMatrix;
import work_in_progress.Quad;
import work_in_progress.Shape;
import work_in_progress.ToggleButton;

public class TestToggleButton extends ToggleButton {
	
	private Color color1;
	private static Color color2 = new Color(1f, 1f, 1f, 1f);
	
	public TestToggleButton(Color color, GUIElementMatrix transformationMatrix) {
		super(new Quad(), color, color2, transformationMatrix);
		color1 = color;
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
