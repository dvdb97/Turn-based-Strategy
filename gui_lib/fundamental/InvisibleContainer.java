package fundamental;

import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

public class InvisibleContainer<E extends Element> extends Container<E> {

	public InvisibleContainer(int width, int height, FlexDirection flexDirection) {
		super(new GUIQuad(ColorPalette.ZERO), width, height, flexDirection);
	}
	
	public InvisibleContainer(float widthPercent, float heightPercent, FlexDirection flexDirection) {
		super(new GUIQuad(ColorPalette.ZERO), widthPercent, heightPercent, flexDirection);
	}

	@Override
	public void render(int parentX, int parentY) {
		getChildren().forEach((e) -> e.render(parentX + getLocalXPosition(), parentY + getLocalYPosition()));
	}

}
