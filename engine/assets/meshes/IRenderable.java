package assets.meshes;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.scene.Scene;

public interface IRenderable {
	
	public abstract void render();
	
	public abstract void render(Camera camera, DirectionalLight light);
	
	public abstract void render(Scene scene);

}
