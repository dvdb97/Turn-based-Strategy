package modelTest;

import interaction.Window;
import interaction.input.KeyInput;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.matrices.projectionMatrices.ProjectionMatrix;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import java.awt.RenderingHints.Key;

import org.lwjgl.glfw.GLFW;

import assets.cameras.Camera;
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
	
	private static ShaderProgram shader;
	
	private static Camera camera;
	
	private static Model model;
	
	
	public static void init() {
		
		window = new Window();
		
		window.createWindowedWindow("Model Demo");
		
		window.setKeyInputCallback(new KeyInput());
		
		RenderEngine.init(window);
		
		RenderEngine.enableDepthTest();
		
		RenderEngine.setSwapInterval(1);
		
		initShader();
		
	}
	
	
	public static void initShader() {
		
		shader = ShaderLoader.loadShader("Shaders/StandardShaders/shader.vert", "Shaders/StandardShaders/shader.frag");
		
	}
	
	
	public static Model initMesh() {
		
		Mesh mesh = FileLoader.loadObjFile("res/models/Suzanne.obj");
		
		Texture2D texture = new Texture2D("res/Textures/TestTexture.png");
		
		return new Model(shader, mesh, new Material(new Color(1f, 0f, 0f, 1f)), texture, BufferLayout.INTERLEAVED);		
		
	}
	
	
	public static void main(String[] args) {
		
		init();
		
		model = initMesh();
		
		camera = new Camera();
		
		ProjectionMatrix projMatrix = ProjectionMatrix.generatePerspectiveProjectionMatrix(window.getProportions());
		
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			
			RenderEngine.clear();
			
			shader.use();
			
			handleInput();
			
			model.render(camera.getViewMatrix(), projMatrix);
			
			shader.disable();
			
			RenderEngine.swapBuffers();
			
		}
		
		model.delete();
		
	}
	
	
	private static void handleInput() {
		
		if (KeyInput.keyPressed(GLFW.GLFW_KEY_W)) {
			//model.getTransformable().rotate(Transformable._1_DEGREE, 0f, 0f);
			model.getTransformable().translate(0f, 0f, -0.1f);
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
		
	}

}
