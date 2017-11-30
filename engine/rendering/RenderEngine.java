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
import math.matrices.Matrix44f;
import math.vectors.Vector4f;


public class RenderEngine {
	
	//A reference to the window this RenderEngine is supposed to draw on 
	private static Window window;
	
	
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

	}
	
	
	public static void close() {
		
	}
	
	
	//****************************** Draw Functions ******************************
	
	
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
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
	
	
	//****************************** getters and setters *************************************
		
	
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
	
	
}