package modelTest;

import interaction.Window;
import interaction.input.KeyInput;
import math.vectors.Vector3f;
import rendering.RenderEngine;
import utils.Cooldown;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import org.lwjgl.glfw.GLFW;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Transformable;
import assets.meshes.Mesh;
import assets.meshes.algorithms.terrain.Heightmap;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.geometry.Color;
import assets.meshes.specialized.EnvMappingMesh;
import assets.meshes.specialized.SkyboxMesh;
import assets.meshes.specialized.WireframeBox;
import assets.scene.Scene;
import assets.shaders.standardShaders.lightShader.LightShader;
import assets.shaders.standardShaders.skybox.EnvMappingShader;
import assets.shaders.subshaders.ConstantColorSubshader;
import assets.shaders.subshaders.Subshader;
import assets.textures.Skybox;
import static java.lang.Math.*;


public class ModelTest {
	
	private static Window window;
	
	private static LightShader shader;
	
	private static Camera camera;
	
	private static DirectionalLight light;
	
	private static Skybox skybox;
	
	private static float xRot = 0f;
	private static float yRot = 0f;	
	
	private static boolean shadows = true;
	
	public static void init() {
		window = new Window();
		
		window.createFullscreenWindow("Model Demo");
		
		window.setKeyInputCallback(new KeyInput());
		
		RenderEngine.init(window);
		
		RenderEngine.enableDepthTest();
		
		RenderEngine.setSwapInterval(1);
		
		Cooldown.start("shadows", 0.2);
		Cooldown.start("screenshot", 1.0);
	}
	
	
	public static void initShader() {
		Subshader subshader = new ConstantColorSubshader(new Color(1f, 0f, 0f, 1f));//Subshader.loadSubshader("Shaders/subshaders/Terrain.frag");
		shader = LightShader.createPerFragmentLightShader(subshader);
	}
	
	
	public static Mesh initMesh() {		
		Heightmap heightmap = new Heightmap("res/heightmaps/HalloWelt.png");
		
		Mesh mesh = new EnvMappingMesh();
		FileLoader.loadObjFile(mesh, "res/models/Suzanne.obj");
		
		Material material = new Material(Color.RED, Vector3f.ZERO, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.5f, 0.5f, 0.5f), 256f);
		//Mesh mesh = Terrain.generate(heightmap);
		mesh.setMaterial(material);
		
		return mesh;	
	}
	
	
	public static void main(String[] args) {
		init();
		
		testShadows();
	}
	
	
	private static void testShadows() {
		initShader();
		
		Mesh mesh = initMesh();
		
		mesh.getTransformable().setScaling(2f, 2f, 2f);
		mesh.getTransformable().translate(0, 0, 0);
		mesh.getTransformable().rotate(0f, 0f, 0f);
		
		camera = new Camera(new Vector3f(0f, 0f, 5f));
		
		light = new DirectionalLight(new Vector3f(1f, 1f, -1f), new Vector3f(1f, 1f, 1f), 4000, 4000);
		
		String[] paths = new String[6];
		paths[Skybox.FRONT] = "res/Textures/Skyboxes/ice/back.jpg";
		paths[Skybox.BACK] = "res/Textures/Skyboxes/ice/front.jpg";
		paths[Skybox.BOTTOM] = "res/Textures/Skyboxes/ice/bottom.jpg";
		paths[Skybox.TOP] = "res/Textures/Skyboxes/ice/top.jpg";
		paths[Skybox.LEFT] = "res/Textures/Skyboxes/ice/left.jpg";
		paths[Skybox.RIGHT] = "res/Textures/Skyboxes/ice/right.jpg";
		
		skybox = new Skybox(paths);
		SkyboxMesh skyboxMesh = new SkyboxMesh(skybox);
		
		WireframeBox box = new WireframeBox();
		box.setColor(new Color(0f, 1f, 0f, 1f));
		box.getTransformable().setScaling(3f);
		
		EnvMappingShader emShader = EnvMappingShader.createEnvMappingShader();

		Scene scene = new Scene(camera, light, skybox);
		
		light.fitToBoundingBox(mesh);
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();
			
			handleInput();
			
			skyboxMesh.render(scene);
			mesh.render(scene);
			box.render(scene);
			
			RenderEngine.swapBuffers();
		}
		
	}
	
	
	private static void handleInput() {
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_W)) {
			//model.getTransformable().rotate(Transformable._1_DEGREE, 0f, 0f);
			
			xRot += Transformable._1_DEGREE;
			float l = camera.getPosition().norm();
			
			camera.setPosition(new Vector3f(0f, (float)sin(xRot), (float)cos(xRot)).times(l));
			camera.lookAt(new Vector3f(0f, 0f, 0f));			
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_S)) {
			//model.getTransformable().rotate(-Transformable._1_DEGREE, 0f, 0f);
			
			xRot -= Transformable._1_DEGREE;
			float l = camera.getPosition().norm();
			
			camera.setPosition(new Vector3f(0f, (float)sin(xRot), (float)cos(xRot)).times(l));
			camera.lookAt(new Vector3f(0f, 0f, 0f));			
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_A)) {
			//model.getTransformable().rotate(0f, -Transformable._1_DEGREE, 0f);
			
			yRot -= Transformable._1_DEGREE;
			float l = camera.getPosition().norm();
			
			camera.setPosition(new Vector3f((float)sin(yRot), 0f, (float)cos(yRot)).times(l));
			camera.lookAt(new Vector3f(0f, 0f, 0f));
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_D)) {
			//model.getTransformable().rotate(0f, Transformable._1_DEGREE, 0f);
			
			yRot += Transformable._1_DEGREE;
			float l = camera.getPosition().norm();
			
			camera.setPosition(new Vector3f((float)sin(yRot), 0f, (float)cos(yRot)).times(l));
			camera.lookAt(new Vector3f(0f, 0f, 0f));
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_Q)) {
			camera.backward(0.1f);
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_E)) {
			camera.forward(0.1f);
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_ENTER)) {
			if (Cooldown.ended("shadows")) {
				shadows = !shadows;
				
				Cooldown.refresh("shadows");
			}
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_F2)) {
			if (Cooldown.ended("screenshot")) {
				RenderEngine.takeScreenshot("screenshots", "PNG");
				
				Cooldown.refresh("screenshot");
			}
		}
	}
}
