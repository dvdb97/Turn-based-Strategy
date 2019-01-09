package modelTest;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import assets.cameras.Camera;
import assets.material.Material;
import assets.meshes.geometry.Color;
import assets.meshes.specialized.Circle;
import assets.scene.Scene;
import assets.textures.Texture2D;
import interaction.Window;
import interaction.input.KeyInput;
import math.vectors.Vector3f;
import rendering.RenderEngine;

public class Parenting {
	
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
		
		Circle redCircle = new Circle();
		redCircle.getTransformable().setScaling(0.3f);
		redCircle.setTexture(new Texture2D("res/Textures/TestTexture.png"));
		//circle.useTextureColor();
		
		Circle blueCircle = new Circle();
		blueCircle.setMaterial(new Material(new Color(0f, 0f, 1f, 1f)));
		blueCircle.transformable.setScaling(0.3f);
		blueCircle.transformable.translate(-2f, 0f, 0f);
		blueCircle.transformable.setParent(redCircle);
		
		Circle greenCircle = new Circle();
		greenCircle.setMaterial(new Material(new Color(0f, 1f, 0f, 1f)));
		greenCircle.transformable.setScaling(0.4f);
		greenCircle.transformable.translate(-2f, 0f, 0f);
		greenCircle.transformable.setParent(blueCircle);
		
		Camera camera = new Camera(new Vector3f(0f, 0f, 1f));
		Scene scene = new Scene(camera, null, null);
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();
			
			redCircle.transformable.rotate(0, 0, 0.01f);
			blueCircle.transformable.rotate(0f, 0f, 0.02f);
			
			redCircle.render(scene);
			blueCircle.render(scene);
			greenCircle.render(scene);
			
			RenderEngine.swapBuffers();
		}
		
		redCircle.delete();
	}

}
