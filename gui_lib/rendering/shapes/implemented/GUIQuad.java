package rendering.shapes.implemented;

import assets.meshes.geometry.Color;
import rendering.Renderer2D;
import rendering.shapes.GUIShape;
import target_detection.TargetDetection;
import utils.ColorPalette;

public class GUIQuad extends GUIShape {
	
	private String imgName;
	
	public GUIQuad(Color color) {
		super(color);
	}
	
	
	public GUIQuad(String imgPath) {
		super(ColorPalette.WHITE);
		
		this.imgName = "" + imgPath.hashCode();
		Renderer2D.loadImage(imgName, imgPath);
	}
	

	@Override
	public void render(int x, int y, int width, int height) {		
		if (imgName == null) {
			Renderer2D.beginPath();
			Renderer2D.rect(x, y, width, height);
			Renderer2D.fill(getColor());
		} else {
			Renderer2D.image(imgName, x, y, width, height);
			Renderer2D.fill();
		}
	}

	@Override
	public boolean isTargeted(int x, int y, int width, int height, int cursorX, int cursorY) {
		return TargetDetection.square(x, y, width, height, cursorX, cursorY);
	}

}
