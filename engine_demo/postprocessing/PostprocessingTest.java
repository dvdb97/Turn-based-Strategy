package postprocessing;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Mesh3D;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.geometry.Color;
import assets.meshes.specialized.Circle;
import assets.meshes.specialized.SkyboxMesh;
import assets.scene.Scene;
import assets.textures.Skybox;
import assets.textures.Texture2D;
import interaction.Window;
import interaction.input.KeyInput;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.RenderQueue;
import rendering.postprocessing.ToonShading;

public class PostprocessingTest {

	private static Window window;

	public static void main(String[] args) {
		window = new Window();
		window.createFullscreenWindow("Parenting Test");
		window.setKeyInputCallback(new KeyInput());
		
		RenderEngine.init(window);
		RenderEngine.enableDepthTest();
		RenderEngine.setSwapInterval(1);
		
		run();
	}
	
	public static void run() {
		Camera camera = new Camera(new Vector3f(0f, 0f, 2f));
		DirectionalLight light = new DirectionalLight(new Vector3f(1f, 1f, -1f), new Vector3f(1f, 1f, 1f), 4000, 4000);
		Scene scene = new Scene(camera, light, null);
		
		RenderQueue renderQueue = new RenderQueue(window, scene);
		renderQueue.setPostprocessingLayer(new ToonShading());
		
		String[] paths = new String[6];
		paths[Skybox.FRONT] = "res/Textures/Skyboxes/ice/back.jpg";
		paths[Skybox.BACK] = "res/Textures/Skyboxes/ice/front.jpg";
		paths[Skybox.BOTTOM] = "res/Textures/Skyboxes/ice/bottom.jpg";
		paths[Skybox.TOP] = "res/Textures/Skyboxes/ice/top.jpg";
		paths[Skybox.LEFT] = "res/Textures/Skyboxes/ice/left.jpg";
		paths[Skybox.RIGHT] = "res/Textures/Skyboxes/ice/right.jpg";
		Skybox skybox = new Skybox(paths);
		SkyboxMesh skyboxMesh = new SkyboxMesh(skybox);
		
		Mesh3D mesh = new Mesh3D();
		FileLoader.loadObjFile(mesh, "res/models/Suzanne.obj");
		renderQueue.addMesh(mesh);
		renderQueue.addMesh(skyboxMesh);
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();
			
			renderQueue.render();
			
			RenderEngine.swapBuffers();
		}
		
		renderQueue.delete();
	}
	
}
