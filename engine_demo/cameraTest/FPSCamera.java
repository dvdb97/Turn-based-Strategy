package cameraTest;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import assets.cameras.Camera;
import assets.cameras.FirstPersonController;
import assets.light.DirectionalLight;
import assets.meshes.Mesh3D;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.specialized.SkyboxMesh;
import assets.scene.Scene;
import assets.textures.Skybox;
import interaction.Window;
import interaction.input.CursorPosInput;
import interaction.input.KeyInputHandler;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.RenderEngine;

public class FPSCamera {
	
	private static Window window;
	
	public static void init() {
		window = new Window();
		window.createFullscreenWindow("Model Demo");
		window.setKeyInputCallback(new KeyInputHandler());
		window.setMousePosInput(new CursorPosInput(window));
		
		RenderEngine.init(window);
		RenderEngine.enableDepthTest();
		RenderEngine.setSwapInterval(1);
	}
	
	
	public static void run() {
		FirstPersonController fpc = new FirstPersonController(Vector3f.ZERO);
		fpc.setSpeed(0.5f);
		Camera camera = fpc.getCamera();
		
		DirectionalLight light = new DirectionalLight(new Vector3f(1f, 1f, -1f), new Vector3f(1f, 1f, 1f), 4000, 4000);
		
		String[] paths = new String[6];
		paths[Skybox.FRONT] = "res/Textures/Skyboxes/ice/back.jpg";
		paths[Skybox.BACK] = "res/Textures/Skyboxes/ice/front.jpg";
		paths[Skybox.BOTTOM] = "res/Textures/Skyboxes/ice/bottom.jpg";
		paths[Skybox.TOP] = "res/Textures/Skyboxes/ice/top.jpg";
		paths[Skybox.LEFT] = "res/Textures/Skyboxes/ice/left.jpg";
		paths[Skybox.RIGHT] = "res/Textures/Skyboxes/ice/right.jpg";		
		Skybox skybox = new Skybox(paths);
		SkyboxMesh skyboxMesh = new SkyboxMesh(skybox);
		
		Scene scene = new Scene(camera, light, skybox);
		
		Mesh3D mesh = new Mesh3D();
		FileLoader.loadObjFile(mesh, "res/models/Suzanne.obj");
		mesh.useMaterialColor();
		
		
		while (!KeyInputHandler.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();
			
			fpc.update();
			
			skyboxMesh.render(scene);
			mesh.render(scene);
			
			RenderEngine.swapBuffers();
		}
		
		mesh.delete();
		light.delete();
		skybox.delete();
		skyboxMesh.delete();
	}
	
	
	public static void main(String[] args) {
		init();
		run();
	}

}
