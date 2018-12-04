package work_in_progress.test;

import assets.meshes.geometry.Color;
import dataType.GUIElementMatrix;
import function.Function;
import input.ToggleButton;
import rendering.shapes.GUIQuad;
import rendering.shapes.Shape;
import stbFont.TTFBox;

import static utils.ColorPalette.*;

public class TestToggleButton extends ToggleButton {
	
	private Color color1;
	private static Color color2 = WHITE;
	
	public TestToggleButton(Color color, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), color, color2, transformationMatrix);
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
