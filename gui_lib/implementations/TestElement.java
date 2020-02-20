package implementations;

import assets.meshes.geometry.Color;
import fundamental.Element;
import gui_core.Input;
import rendering.shapes.GUIShape;
import rendering.shapes.implemented.GUIEllipse;
import utils.ColorPalette;

public class TestElement extends Element {

	protected static final Color defaultColor = ColorPalette.RED;
	protected static final Color targetedColor = ColorPalette.GREEN;
	
	public TestElement(GUIShape shape, int width, int height) {
		super(shape, width, height);
		
		addMouseEnterListener((Input input) -> getShape().setColor(targetedColor));
		addMouseLeaveListener((Input input) -> getShape().setColor(defaultColor));
	}
	
	
	public TestElement(GUIShape shape, float widthPercent, float heightPercent) {
		super(shape, widthPercent, heightPercent);
		
		addMouseEnterListener((Input input) -> getShape().setColor(targetedColor));
		addMouseLeaveListener((Input input) -> getShape().setColor(defaultColor));
	}
	
}
