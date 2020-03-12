package fundamental;

import rendering.shapes.implemented.GUIQuad;

public class GUIImageBox extends GUIElement {

	public GUIImageBox(String imgPath, float widthPercent, float heightPercent) {
		super(new GUIQuad(imgPath), widthPercent, heightPercent);
	}
	
	
	public GUIImageBox(String imgPath, int width, int height) {
		super(new GUIQuad(imgPath), width, height);
	}
	
}
