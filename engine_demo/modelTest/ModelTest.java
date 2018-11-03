package modelTest;

import interaction.Window;
import interaction.input.KeyInput;
import math.vectors.Vector3f;
import performance.SpeedTester;
import rendering.BoxRenderer;
import rendering.RenderEngine;
import rendering.TextureRenderer;
import utils.Cooldown;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nuklear.Nuklear;
import org.lwjgl.system.MemoryStack;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Mesh;
import assets.meshes.Model;
import assets.meshes.Transformable;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.algorithms.terrain.Heightmap;
import assets.meshes.algorithms.terrain.Terrain;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.geometry.Color;
import assets.shaders.standardShaders.lightShader.LightShader;
import assets.shaders.subshaders.ConstantColorSubshader;
import assets.shaders.subshaders.Subshader;
import assets.textures.Skybox;
import static java.lang.Math.*;

import org.lwjgl.nuklear.NkAllocator;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.nuklear.NkUserFont;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;

import static org.lwjgl.stb.STBTruetype.*;


public class ModelTest {
	
	private static Window window;
	
	private static LightShader shader;
	
	private static Camera camera;
	
	private static DirectionalLight light;
	
	private static Model model;
	
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
	}
	
	
	public static void initShader() {
		Subshader subshader = Subshader.loadSubshader("Shaders/subshaders/Terrain.frag");// new ConstantColorSubshader(Color.BLUE);//
		shader = LightShader.createPerFragmentLightShader(subshader);
	}
	
	
	public static Model initMesh() {		
		Heightmap heightmap = new Heightmap("res/heightmaps/Osttirol_HR.png");
		
		Mesh mesh = /*FileLoader.loadObjFile("res/models/Suzanne.obj");*/Terrain.generate(heightmap);
		
		Material material = new Material(Color.GREY, Vector3f.ZERO, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0.2f, 0.2f, 0.2f), 256f);
		
		return new Model(shader, mesh, material, null, BufferLayout.INTERLEAVED);		
	}
	
	
	public static void main(String[] args) {
		init();
		
		testShadows();
	}
	
	
	private static void testShadows() {
		initShader();
		
		model = initMesh();
		
		model.getTransformable().setScaling(6f, 6f, 1f);
		model.getTransformable().translate(0, 0, 0);
		model.getTransformable().rotate(0f, 0f, 0f);
		
		camera = new Camera(new Vector3f(0f, 0f, 5f));
		
		light = new DirectionalLight(new Vector3f(-1f, 0f, -1f), new Vector3f(1f, 1f, 1f), 2000, 2000);
		
		String[] paths = new String[6];
		paths[Skybox.FRONT] = "res/Textures/Skyboxes/ice/back.jpg";
		paths[Skybox.BACK] = "res/Textures/Skyboxes/ice/front.jpg";
		paths[Skybox.BOTTOM] = "res/Textures/Skyboxes/ice/bottom.jpg";
		paths[Skybox.TOP] = "res/Textures/Skyboxes/ice/top.jpg";
		paths[Skybox.LEFT] = "res/Textures/Skyboxes/ice/left.jpg";
		paths[Skybox.RIGHT] = "res/Textures/Skyboxes/ice/right.jpg";
		
		skybox = new Skybox(paths);
		
		light.fitToBoundingBox(model);
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();
			
			//skybox.render(camera);
			
			light.startShadowMapPass();
			light.passToShadowMap(model);
			light.endShadowMapPass();
			
			shader.bind();
			
			shader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
			shader.setLightSource(light, shadows);
			shader.setCamera(camera);
			
			handleInput();
			
			model.render();
			
			shader.unbind();
			
			//BoxRenderer.draw(model.getTransformable().getTransformationMatrix(), camera, Color.RED);
			//light.render(camera, Color.YELLOW);
			//TextureRenderer.draw(light.getShadowMap().getDepthTexture(), 0.5f, 0.5f, 1f / window.getAspectRatio(), 1f);
			
			RenderEngine.swapBuffers();
		}
		
		TextureRenderer.delete();
		BoxRenderer.delete();
		shader.delete();
		model.delete();
		light.delete();
		skybox.delete();
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
	}
}
