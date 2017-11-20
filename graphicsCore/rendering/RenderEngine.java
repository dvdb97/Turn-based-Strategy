package rendering;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import assets.models.abstractModels.Renderable;
import assets.textures.Texture2D;
import interaction.Window;
import lwlal.Matrix44f;
import lwlal.Vector4f;
import lwlal_advanced.ProjectionMatrix;
import lwlal_advanced.ViewMatrix;


public class RenderEngine {
	
	//A reference to the window this RenderEngine is supposed to draw on 
	private static Window window;
	
	
	//Matrices that will be outsourced soon TODO
	private static ViewMatrix viewMatrix;
	private static Matrix44f projectionMatrix;
	
	
	//Settings:
	private static boolean depthTestEnabled = false;
	

	private static double timer;
	
	
	public static void init(Window win) {
		createCapabilities();
		
		window = win;
		
		timer = glfwGetTime();
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		
		glEnable(GL_STENCIL_TEST);
		
		
		createViewMatrix();
		createProjectionMatrix();

	}
	
	
	public static void close() {
		
	}
	
	
	//****************************** Draw Functions ******************************
	
	
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}
	
	
	public static void swapBuffers() {
		glfwPollEvents();
		glfwSwapBuffers(window.getWindowID());
	}
	
	
	public static void draw(Renderable model, Texture2D texture) {
		
		if (texture != null) {
			texture.bind();
		}
		

		model.onDrawStart();
		
		model.activateAttributes();
		
		
		model.render();
		
		
		model.deactivateAttributes();
		
		model.onDrawStop();
		
		
		if (texture != null) {
			texture.unbind();
		}
		
	}
	
	
	public static void displayFPS() {
		
		double fps = 1.0 / (glfwGetTime() - timer);
		
		System.out.println("FPS: " + fps);
		
		timer = glfwGetTime();
		
	}
	
	
	public static void takeScreenshot(String fileName) {
		
		int[] pixels = new int[window.getFrameBufferHeight() * window.getFrameBufferWidth()];
		
		glReadPixels(0, 0, window.getFrameBufferWidth(), window.getFrameBufferHeight(), GL_RGBA, GL_INT, pixels);
		
		
		BufferedImage image = new BufferedImage(window.getFrameBufferWidth(), window.getFrameBufferHeight(), BufferedImage.TYPE_INT_ARGB);
		
		
		for (int y = 0; y < window.getFrameBufferHeight(); ++y) {
			
			for (int x = 0; x < window.getFrameBufferWidth(); ++x) {
				
				/*
				 * Take the bits for the alpha channel and put them at the first byte of the rgba color
				 * thus making it argb
				 */
				int alpha = (byte)pixels[y * window.getFrameBufferWidth() + x];
				
				int argb = (pixels[y * window.getFrameBufferWidth() + x] >> 8) | (alpha << 24);
				
				image.setRGB(x, y, argb);
				
			}
			
		}
		
		
		try {
			ImageIO.write(image, "jpg", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//****************************** methods *************************************
	
	
	public static void refreshViewMatrix() {
		viewMatrix.refresh();
	}
	
	//****************************** get & set ***********************************
	
	//TODO: if you return an object, you return not the data but the reference to that data
	//			so effectively this object is public instead of private.
	//			--> better return a copy of that object
	public static Matrix44f getViewmatrix() {
		
		return viewMatrix;
		
	}
	
	
	//TODO: same as above
	public static Matrix44f getProjectionMatrix() {
		
		return projectionMatrix;
		
	}
	
	
	public static boolean isDepthTestEnabled() {
		return depthTestEnabled;
	}
	
	
	//****************************** Settings Stuff ******************************
	
	
	public static void enableDepthTest() {
		if (depthTestEnabled) {
			System.out.println("Depth Test is already enabled!");
			
			return;
		}
		
		depthTestEnabled = true;
		
		glEnable(GL_DEPTH_TEST);
	}
	
	
	public static void disableDepthTest() {
		if (!depthTestEnabled) {
			System.out.println("Depth is already disabled!");
			
			return;
		}
		
		depthTestEnabled = false;
		
		glDisable(GL_DEPTH_TEST);
	}
	
	
	public static void setClearColor(float red, float green, float blue, float alpha) {
		glClearColor(red, green, blue, alpha);
	}
	
	
	public static void setClearColor(Vector4f colorVec) {
		glClearColor(colorVec.getA(), colorVec.getB(), colorVec.getC(), colorVec.getD());
	}
	
	
	public static void setSwapInterval(int interval) {
		if(interval >= 0) {
			glfwSwapInterval(interval);
			return;
		}
		System.out.println("Invalid swap interval!");
	}
	
	
	//TODO: values should be set at a different place
	private static void createProjectionMatrix() {
		projectionMatrix = new ProjectionMatrix(-1, 1, -1, 1, 1, -10.0f, window.getProportions());
		
	}
	
	
	private static void createViewMatrix() {
		viewMatrix = new ViewMatrix();
	}
}