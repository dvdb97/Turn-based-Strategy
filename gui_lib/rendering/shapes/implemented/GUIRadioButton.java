package rendering.shapes.implemented;

import rendering.Renderer2D;
import rendering.shapes.GUIShape;
import target_detection.TargetDetection;
import utils.ColorPalette;

public class GUIRadioButton extends GUIShape {
	
	public GUIRadioButton() {
		super(ColorPalette.ZERO);
		
		this.state = "inactive";
	}	

	@Override
	public void render(int x, int y, int width, int height) {
		Renderer2D.beginPath();
		Renderer2D.circle(x, y, width / 2);
		Renderer2D.stroke(ColorPalette.BLACK, 10);
		Renderer2D.fill(ColorPalette.WHITE);
		
		if (state.equals("active")) {
			Renderer2D.beginPath();
			int radius = width / 4;
			Renderer2D.circle(x + radius + radius / 4, y + radius + radius / 4, radius);
			Renderer2D.fill(ColorPalette.BLACK);
		}
	}

	@Override
	public boolean isTargeted(int x, int y, int width, int height, int cursorX, int cursorY) {
		return TargetDetection.ellipse(x, y, width, height, cursorX, cursorY);
	}

}
