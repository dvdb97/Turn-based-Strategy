package rendering.shapes.implemented;

import assets.meshes.geometry.Color;
import rendering.Renderer2D;
import rendering.shapes.GUIShape;
import target_detection.TargetDetection;

public class GUIEllipse extends GUIShape {

	public GUIEllipse(Color color) {
		super(color);
	}
	

	@Override
	public void render(int x, int y, int width, int height) {
		Renderer2D.beginPath();
		Renderer2D.ellipse(x, y, width / 2, height / 2);
		Renderer2D.fill(getColor());
	}

	@Override
	public boolean isTargeted(int x, int y, int width, int height, int cursorX, int cursorY) {
		return TargetDetection.ellipse(x, y, width, height, cursorX, cursorY);
	}
	
}
