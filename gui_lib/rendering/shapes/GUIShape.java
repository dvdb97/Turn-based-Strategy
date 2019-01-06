package rendering.shapes;

import assets.meshes.Mesh2D;
import assets.meshes.geometry.Color;
import assets.textures.Texture;

public abstract class GUIShape extends Mesh2D {
	
	public GUIShape() {
		useTextureColor();	
	}
	
	
	public GUIShape(int drawMode) {
		super(drawMode);
		useTextureColor();	
	}
	
	
	public void render(Texture texture) {
		this.setTexture(texture);
		
		onDrawStart(null);
		this.render();		
		onDrawEnd(null);
	}
	
	
	public void render(Color color) {
		this.getMaterial().color = color;
		
		onDrawStart(null);
		this.render();
		onDrawEnd(null);
	}
	
	
	public abstract boolean isHit(float cursorX, float cursorY);
	
}
