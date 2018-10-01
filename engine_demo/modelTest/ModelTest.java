package modelTest;

import interaction.Window;
import interaction.input.KeyInput;
import math.vectors.Vector3f;
import rendering.RenderEngine;
import rendering.matrices.projectionMatrices.ProjectionMatrix;
import rendering.shaders.standardShaders.lightShader.LightShader;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFW;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Mesh;
import assets.meshes.Model;
import assets.meshes.Transformable;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.algorithms.terrain.CosFunction;
import assets.meshes.algorithms.terrain.Heightmap;
import assets.meshes.algorithms.terrain.Terrain;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.geometry.Color;
import assets.textures.Skybox;
import assets.textures.Texture2D;

import static java.lang.Math.*;

public class ModelTest {
	
	private static Window window;
	
	private static LightShader shader;
	
	private static Camera camera;
	
	private static DirectionalLight light;
	
	private static Model model;
	
	private static Skybox skybox;
	
	private static float xRot = 0f;
	private static float yRot = 0f;
	
	private static boolean wireframe = false;	
	
	public static void init() {
		window = new Window();
		
		window.createFullscreenWindow("Model Demo");
		
		window.setKeyInputCallback(new KeyInput());
		
		RenderEngine.init(window);
		
		RenderEngine.enableDepthTest();
		
		RenderEngine.setSwapInterval(1);
		
		initShader();
	}
	
	
	public static void initShader() {
		shader = LightShader.createPerFragmentLightShader();
	}
	
	
	public static Model initMesh() {		
		Heightmap heightmap = new Heightmap("res/heightmaps/Osttirol.png");
		
		Mesh mesh = Terrain.generate(heightmap);
		
		Texture2D texture = new Texture2D("res/Textures/TestTexture.png");
		
		Material material = new Material(Color.GREY, Vector3f.ZERO, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), 1f);
		
		return new Model(shader, mesh, material, texture, BufferLayout.INTERLEAVED);		
	}
	
	
	public static void main(String[] args) {
		init();
		
		model = initMesh();
		
		model.getTransformable().setScaling(6f, 6f, 0.5f);
		
		camera = new Camera(new Vector3f(0f, 0f, 5f));
		
		light = new DirectionalLight(new Vector3f(0f, 0f, -1f), new Vector3f(1f, 1f, 1f));
		
		String[] paths = new String[6];
		paths[Skybox.FRONT] = "res/Textures/Skyboxes/ice/back.jpg";
		paths[Skybox.BACK] = "res/Textures/Skyboxes/ice/front.jpg";
		paths[Skybox.BOTTOM] = "res/Textures/Skyboxes/ice/bottom.jpg";
		paths[Skybox.TOP] = "res/Textures/Skyboxes/ice/top.jpg";
		paths[Skybox.LEFT] = "res/Textures/Skyboxes/ice/left.jpg";
		paths[Skybox.RIGHT] = "res/Textures/Skyboxes/ice/right.jpg";
		
		skybox = new Skybox(paths);
		
		ProjectionMatrix projMatrix = ProjectionMatrix.generatePerspectiveProjectionMatrix(window.getProportions());
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			
			RenderEngine.clear();
			
			skybox.render(camera.getViewMatrix(), projMatrix);
			
			shader.use();
			
			shader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
			
			shader.setLightSource(light);
			
			shader.setCamera(camera);
			
			handleInput();
			
			model.render(camera.getViewMatrix(), projMatrix, true);
			
			shader.disable();
			
			RenderEngine.swapBuffers();
		}
		
		model.delete();
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
			RenderEngine.takeScreenshot(window, "screenshots/Fancy.png", "PNG");			
		}
	}
}
