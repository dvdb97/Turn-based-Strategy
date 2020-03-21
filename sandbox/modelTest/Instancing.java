package modelTest;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Transformable;
import assets.meshes.fileLoaders.FileLoader;
import assets.meshes.instanced.Instancer3D;
import assets.scene.Scene;
import interaction.Window;
import interaction.input.KeyInput;
import interaction.input.KeyInputHandler;
import math.vectors.Vector3f;
import rendering.Renderer;
import utils.Cooldown;

public class Instancing {
	
private static Window window;
	
	private static Camera camera;
	private static DirectionalLight light;
	
	public static void main(String[] args) {
		init();
		run();
	}
	
	
	public static void init() {
		window = new Window();
		window.createFullscreenWindow("Model Demo");
		window.setKeyInputCallback(new KeyInputHandler());
		
		Renderer.init(window);
		Renderer.enableDepthTest();
		Renderer.setSwapInterval(1);
	}
	
	
	public static void run() {
		camera = new Camera(new Vector3f(0f, 0f, 5f));
		light = new DirectionalLight(new Vector3f(0, 0f, -1f), new Vector3f(1f, 1f, 1f), 4000, 4000);
		Scene scene = new Scene(camera, light);
		
		Instancer3D mesh = new Instancer3D(20 * 20 * 20);
		String path = "res/models/";
		FileLoader.loadObjFile(mesh, path + "Suzanne.obj");
		
		Vector3f scale = new Vector3f(0.3f, 0.3f, 0.3f);

		for (int z = 0; z < 20; ++z) {
			for (int y = 0; y < 20; ++y) {
				for (int x = 0; x < 20; ++x) {
					System.out.println(z * 20 * 20 + y * 20 + x);
					System.out.println(mesh.unreserved());
					mesh.addInstance(new Vector3f(-4.5f * 2 + x, 4.5f * 2 - y, 4.5f * 2 - z), Vector3f.ZERO, scale);
				}
			}
		}
		
		mesh.useMaterialColor();
		mesh.transformable.translate(0f, 0f, -15f);
		mesh.transformable.rotate(0f, Transformable._1_DEGREE * 0f, 0f);
		
		double lastTime = glfwGetTime();
		int nbFrames = 0;
		
		Cooldown cd = new Cooldown(0.1);
		cd.start();
		int index = 0;
		
		while (!KeyInputHandler.keyPressed(GLFW_KEY_ESCAPE)) {
			//Measure speed
		    double currentTime = glfwGetTime();
		    nbFrames++;
		    if ( currentTime - lastTime >= 1.0 ){
		        // printf and reset timer
		        System.out.println("ms/frame " + 1000.0 / (double)nbFrames + "; fps " + nbFrames);
		        nbFrames = 0;
		        lastTime += 1.0;
		    }
		    
		    if (cd.ended()) {
		    	mesh.getInstanceById(index++ % (20 * 20 * 20)).getTransform().rotate(0f, 90f * Transformable._1_DEGREE, 0);
		    	cd.start();
		    }
			
			Renderer.clear();
			
			mesh.render(scene);
			
			Renderer.swapBuffers();
		}
		
		mesh.delete();
	}

}
