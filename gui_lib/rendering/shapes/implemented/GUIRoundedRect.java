package rendering.shapes.implemented;

import assets.meshes.geometry.Color;
import rendering.Renderer2D;
import rendering.shapes.GUIShape;
import target_detection.TargetDetection;

public class GUIRoundedRect extends GUIShape {
	
	private int radius;
	
	public GUIRoundedRect(Color color, int radius) {
		super(color);
		
		this.radius = radius;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void render(int x, int y, int width, int height) {
		Renderer2D.beginPath();
		Renderer2D.roundedRect(x, y, width, height, radius);
		Renderer2D.fill(getColor());
	}

	@Override
	public boolean isTargeted(int x, int y, int width, int height, int cursorX, int cursorY) {
		return TargetDetection.roundedRect(x, y, width, height, radius, cursorX, cursorY);
	}
	
}