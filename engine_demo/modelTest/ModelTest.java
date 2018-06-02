package modelTest;

import interaction.Window;
import interaction.input.KeyInput;
import math.vectors.Vector2f;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import java.util.ArrayList;

import assets.meshes.Mesh;
import assets.meshes.Model;
import assets.meshes.geometry.Vertex;

public class ModelTest {
	
	private static Window window;
	
	private static ShaderProgram shader;
	
	
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
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		vertices.add(new Vertex(new Vector2f(-0.5f, 0.5f), new Vector4f(0.5f, 1.0f, 0.0f, 1.0f)));
		
		vertices.add(new Vertex(new Vector2f(0.5f, 0.5f), new Vector4f(1.0f, 0.0f, 0.0f, 1.0f)));
		
		vertices.add(new Vertex(new Vector2f(-0.5f, -0.5f), new Vector4f(0.0f, 1.0f, 0.0f, 1.0f)));
		
		vertices.add(new Vertex(new Vector2f(0.5f, -0.5f), new Vector4f(0.0f, 0.0f, 1.0f, 1.0f)));
		
		int[] indices = {
			0, 1, 3,
			0, 2, 3
		};
		
		Mesh mesh = new Mesh(vertices, indices);
		
		return new Model(shader, mesh);		
		
	}
	
	
	public static void main(String[] args) {
		
		init();
		
		Model model = initMesh();
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			
			RenderEngine.clear();
			
			model.render();
			
			RenderEngine.swapBuffers();
			
		}
		
	}

}
