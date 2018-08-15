package modelTest;

import interaction.Window;
import interaction.input.KeyInput;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.matrices.projectionMatrices.ProjectionMatrix;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;
import rendering.shaders.standardShaders.lightShader.LightShader;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import java.awt.RenderingHints.Key;

import org.lwjgl.glfw.GLFW;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Mesh;
import assets.meshes.Model;
import assets.meshes.Transformable;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.geometry.Color;
import assets.textures.Texture2D;

public class ModelTest {
	
	private static Window window;
	
	private static LightShader shader;
	
	private static Camera camera;
	
	private static DirectionalLight light;
	
	private static Model model;
	
	
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
		
		Mesh mesh = FileLoader.loadObjFile("res/models/Suzanne.obj");
		
		Texture2D texture = new Texture2D("res/Textures/TestTexture.png");
		
		Material material = new Material(Color.GREEN, Vector3f.ZERO, new Vector3f(0.5f, 0.5f, 1f), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.2f, 0.2f, 0.2f), 1f);
		
		return new Model(shader, mesh, material, texture, BufferLayout.INTERLEAVED);		
		
	}
	
	
	public static void main(String[] args) {
		
		init();
		
		model = initMesh();
		
		camera = new Camera();
		
		light = new DirectionalLight(new Vector3f(-1f, 0f, 0f), new Vector3f(1f, 1f, 0f));
		
		ProjectionMatrix projMatrix = ProjectionMatrix.generatePerspectiveProjectionMatrix(window.getProportions());
		
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			
			RenderEngine.clear();
			
			shader.use();
			
			shader.setAmbientLight(new Vector3f(1f, 1f, 1f));
			
			shader.setLightSource(light);
			
			shader.setCamera(camera);
			
			handleInput();
			
			model.render(camera.getViewMatrix(), projMatrix);
			
			shader.disable();
			
			RenderEngine.swapBuffers();
			
		}
		
		model.delete();
		
	}
	
	
	private static void handleInput() {
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_W)) {
			model.getTransformable().rotate(Transformable._1_DEGREE, 0f, 0f);
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_S)) {
			model.getTransformable().rotate(-Transformable._1_DEGREE, 0f, 0f);
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_A)) {
			model.getTransformable().rotate(0f, -Transformable._1_DEGREE, 0f);
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_D)) {
			model.getTransformable().rotate(0f, Transformable._1_DEGREE, 0f);
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_Q)) {
			camera.backward(0.1f);
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_E)) {
			camera.forward(0.1f);
		}
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_ENTER)) {
			RenderEngine.takeScreenshot(window, "screenshots/monkey.png", "PNG");
		}
		
	}

}
