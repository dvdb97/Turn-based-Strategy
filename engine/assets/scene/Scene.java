package assets.scene;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.textures.Skybox;

public class Scene {
	
	private Skybox skybox;
	
	private DirectionalLight light;
	
	private Camera camera;
	
	
	public Scene(Camera camera, DirectionalLight light, Skybox skybox) {
		this.camera = camera;
		this.light = light;
		this.skybox = skybox;
	}
	
	
	public Scene(Camera camera, DirectionalLight light) {
		this(camera, light, null);
	}
	
	
	public boolean hasCamera() {
		return camera != null;
	}
	
	
	public Camera getCamera() {
		return camera;
	}
	
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	
	public boolean hasLightSource() {
		return light != null;
	}
	
	
	public DirectionalLight getLightSource() {
		return light;
	}
	
	
	public void setLightSource(DirectionalLight light) {
		this.light = light;
	}
	
	
	public boolean hasSkybox() {
		return skybox != null;
	}
	
	
	public Skybox getSkybox() {
		return skybox;
	}
	
	
	public void setSkybox(Skybox skybox) {
		this.skybox = skybox;
	}

}
