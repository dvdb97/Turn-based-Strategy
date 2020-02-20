package rendering.shapes.implemented;

import assets.meshes.geometry.Color;
import rendering.Renderer2D;
import rendering.shapes.GUIShape;
import target_detection.TargetDetection;

public class GUIImage extends GUIShape {

	public GUIImage(Color color) {
		super(color);
	}

	@Override
	public void render(int x, int y, int width, int height) {
		Renderer2D.beginPath();
		
	}

	@Override
	public boolean isTargeted(int x, int y, int width, int height, int cursorX, int cursorY) {
		return TargetDetection.square(x, y, width, height, cursorX, cursorY);
	}

}
