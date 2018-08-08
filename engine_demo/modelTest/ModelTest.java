package modelTest;

import interaction.Window;
import interaction.input.KeyInput;
import math.matrices.Matrix44f;
import math.vectors.Vector2f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.matrices.projectionMatrices.ProjectionMatrix;
import rendering.matrices.transformation.TransformationMatrix;
import rendering.shaders.ShaderLoader;
import rendering.shaders.ShaderProgram;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import java.util.ArrayList;

import javax.xml.crypto.dsig.Transform;

import assets.meshes.Mesh;
import assets.meshes.Model;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.geometry.Vertex;
import assets.textures.Texture2D;

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
		
		shader = ShaderLoader.loadShader("Shaders/StandardShaders/shaderColorAsU.vert", "Shaders/StandardShaders/shaderColorAsU.frag");
		
	}
	
	
	public static Model initMesh() {
		
		Mesh mesh = FileLoader.loadObjFile("res/models/Ball.obj");
		
		Texture2D texture = new Texture2D("res/Textures/TestTexture.png");
		
		return new Model(shader, mesh, texture, BufferLayout.INTERLEAVED);		
		
	}
	
	
	public static void main(String[] args) {
		
		init();
		
		Model model = initMesh();
		
		
		model.getTransformable().scale(0.5f);
		
		model.getTransformable().rotate(1.5708f, 0f, 0f);
		
		Matrix44f transformationMatrix = model.getTransformable().getTransformationMatrix();
		
		System.out.println(transformationMatrix);
		
		ProjectionMatrix projMatrix = ProjectionMatrix.generatePerspectiveProjectionMatrix(window.getProportions());
		
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			
			RenderEngine.clear();
			
			shader.setUniformMatrix4fv("mvpMatrix", projMatrix.times(transformationMatrix));
			
			shader.setUniformVector4f("u_Color", new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
			
			model.render();
			
			RenderEngine.swapBuffers();
			
		}
		
		model.delete();
		
	}

}
