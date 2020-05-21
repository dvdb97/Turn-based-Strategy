package modelTest;

import interaction.Window;
import interaction.input.KeyInputHandler;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.Renderer;
import rendering.shapes.implemented.GUIQuad;
import utils.Cooldown;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import org.lwjgl.glfw.GLFW;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Transformable;
import assets.meshes.Mesh;
import assets.meshes.Mesh3D;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.geometry.Color;
import assets.meshes.specialized.EnvMappingMesh;
import assets.meshes.specialized.Quad;
import assets.meshes.specialized.SkyboxMesh;
import assets.meshes.specialized.WireframeBox;
import assets.scene.Scene;
import assets.textures.Skybox;
import assets.textures.Texture2D;

import static java.lang.Math.*;
import static utils.ColorPalette.*;


public class ModelTest {
	
	private static Window window;
	
	private static Camera camera;
	private static DirectionalLight light;
	
	private static Skybox skybox;	
	private static Mesh mesh;
	
	private static boolean shadows = true;
	
	public static void init() {
		window = new Window();
		
		window.createFullscreenWindow("Model Demo");
		
		window.setKeyInputCallback(new KeyInputHandler());
		
		Renderer.init(window);
		
		Renderer.enableDepthTest();
		
		Renderer.setSwapInterval(1);
		
		Cooldown.start("shadows", 0.2);
		Cooldown.start("screenshot", 1.0);
	}
	
	
	public static Mesh initMesh() {
		Mesh3D mesh = new Mesh3D();
		FileLoader.loadObjFile(mesh, "res/models/Suzanne.obj");
		
		Material material = new Material(Color.RED, Vector3f.ZERO, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.6f, 0.6f, 0.6f), 256f);
		material.castShadows = true;
		
		mesh.setMaterial(material);
		
		mesh.getTransformable().setScaling(2f, 2f, 2f);
		mesh.getTransformable().translate(0, 0, 0);
		mesh.getTransformable().rotate(0f, 0f, 0f);
		mesh.useMaterialColor();
		
		return mesh;	
	}
	
	
	public static void main(String[] args) {
		init();
		
		testShadows();
	}
	
	
	private static void testShadows() {
		mesh = initMesh();
		
		camera = new Camera(new Vector3f(0f, 0f, 5f));
		light = new DirectionalLight(new Vector3f(-1f, 0, -1), new Vector3f(1f, 1f, 1f), 4000, 4000);
		
		String[] paths = new String[6];
		paths[Skybox.FRONT] = "res/Textures/Skyboxes/ice/back.jpg";
		paths[Skybox.BACK] = "res/Textures/Skyboxes/ice/front.jpg";
		paths[Skybox.BOTTOM] = "res/Textures/Skyboxes/ice/bottom.jpg";
		paths[Skybox.TOP] = "res/Textures/Skyboxes/ice/top.jpg";
		paths[Skybox.LEFT] = "res/Textures/Skyboxes/ice/left.jpg";
		paths[Skybox.RIGHT] = "res/Textures/Skyboxes/ice/right.jpg";
		
		Renderer.setClearColor(new Vector4f(1f, 1f, 1f, 1f));
		
		skybox = new Skybox(paths);
		SkyboxMesh skyboxMesh = new SkyboxMesh(skybox);
		
		Scene scene = new Scene(camera, light, skybox);
		
		WireframeBox box = new WireframeBox();
		
		Quad quad = new Quad();
		quad.transformable.scale(0.25f);
		quad.transformable.setTranslation(new Vector3f(0.75f, 0.75f, -1f));
		quad.useTextureColor();
		
		light.fitToBoundingBox(mesh);
		
		while (!KeyInputHandler.keyPressed(GLFW_KEY_ESCAPE)) {
			Renderer.clear();
			handleInput();
			
			light.startShadowMapPass();
			light.passToShadowMap(mesh);
			light.endShadowMapPass();
			
			quad.setTexture(light.getShadowMap().getDepthTexture());
			
			mesh.render(scene);
			quad.render(scene);
			
			Renderer.swapBuffers();
		}
		
		mesh.delete();
		box.delete();
		skyboxMesh.delete();
		light.delete();
		skybox.delete();
	}
	
	
	private static void handleInput() {
		if (KeyInputHandler.keyPressed(GLFW.GLFW_KEY_W)) {
			mesh.getTransformable().rotate(Transformable._1_DEGREE, 0f, 0f);		
		}
		
		if (KeyInputHandler.keyPressed(GLFW.GLFW_KEY_S)) {
			mesh.getTransformable().rotate(-Transformable._1_DEGREE, 0f, 0f);			
		}
		
		if (KeyInputHandler.keyPressed(GLFW.GLFW_KEY_A)) {
			mesh.getTransformable().rotate(0f, -Transformable._1_DEGREE, 0f);
		}
		
		if (KeyInputHandler.keyPressed(GLFW.GLFW_KEY_D)) {
			mesh.getTransformable().rotate(0f, Transformable._1_DEGREE, 0f);
		}
		
		if (KeyInputHandler.keyPressed(GLFW.GLFW_KEY_Q)) {
			mesh.transformable.translate(0f, 0f, -0.01f);
		}
		
		if (KeyInputHandler.keyPressed(GLFW.GLFW_KEY_E)) {
			mesh.transformable.translate(0f, 0f, 0.01f);
		}
		
		if (KeyInputHandler.keyPressed(GLFW.GLFW_KEY_ENTER)) {
			if (Cooldown.ended("shadows")) {
				shadows = !shadows;
				
				Cooldown.refresh("shadows");
			}
		}
		
		if (KeyInputHandler.keyPressed(GLFW.GLFW_KEY_F2)) {
			if (Cooldown.ended("screenshot")) {
				Renderer.takeScreenshot("screenshots", "PNG");
				
				Cooldown.refresh("screenshot");
			}
		}
	}
}
