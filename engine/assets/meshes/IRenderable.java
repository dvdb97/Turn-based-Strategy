package assets.meshes;

import assets.cameras.Camera;
import assets.light.DirectionalLight;

public interface IRenderable {
	
	public abstract void render(Camera camera, DirectionalLight light);

}
