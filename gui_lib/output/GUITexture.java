package output;

import assets.textures.Texture;
import assets.textures.Texture2D;
import dataType.GUIElementMatrix;
import fundamental.Element;
import rendering.shapes.GUIQuad;

public class GUITexture extends Element{
	
	private Texture2D texture;
	
	public GUITexture(String path, GUIElementMatrix transformationMatrix) {
		super(new GUIQuad(), null, transformationMatrix);
		texture = new Texture2D(path, 3000, 1000, Texture.LINEAR, Texture.NEAREST);
	}
	
	@Override
	public void render() {
		shape.render(texture, null, TM);
	}
	
}
