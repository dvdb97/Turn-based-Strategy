package rendering;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.specialized.SkyboxMesh;
import assets.scene.Scene;
import assets.textures.Skybox;
import interaction.PlayerCamera;
import math.vectors.Vector3f;

public class SceneManager {
	
	private static Scene scene;
	
	private static Camera camera;
	
	private static DirectionalLight light;
	
	private static Skybox skybox;
	private static SkyboxMesh sbMesh;
	
	
	public static void init() {
		camera = new PlayerCamera();

		light = new DirectionalLight(new Vector3f(0f, -1f, -0.2f), new Vector3f(0.8f, 0.8f, 0.5f), 4000, 4000);
		
		skybox = new GameSkybox();
		sbMesh = new SkyboxMesh(skybox);
		
		scene = new Scene(camera, light, skybox);
		
		Renderer.initRenderQueue(scene);
		Renderer.addMeshToRenderQueue(sbMesh);
	}
	
	
	public static Camera getCamera() {
		return camera;
	}
	
	
	public static DirectionalLight getLightSource() {
		return light;
	}
	
	
	public static Skybox getSkybox() {
		return skybox;
	}
	
	
	public static Scene getScene() {
		return scene;
	}

}
