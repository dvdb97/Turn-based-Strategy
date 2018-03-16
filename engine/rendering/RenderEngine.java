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
import math.vectors.Vector2i;
import math.vectors.Vector4f;
import rendering.framebuffers.FrameBuffer;


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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	
	public static void clear(int bits) {
		glClear(bits);
	}
	
	
	public static void swapBuffers() {
		glfwPollEvents();
		glfwSwapBuffers(window.getWindowID());
	}
	
	
	public static void render(Renderable model, Texture2D texture) {
		
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
	
	
	public static void render(FrameBuffer frameBuffer, Renderable model, Texture2D texture) {
		
		frameBuffer.bind();
		
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
		
		frameBuffer.unbind();
		
	}
	
	
	public static void displayFPS() {
		
		double fps = 1.0 / (glfwGetTime() - timer);
		
		System.out.println("FPS: " + fps);
		
		timer = glfwGetTime();
		
	}
	
	
	//****************************** getters and setters *************************************
		
	
	public static boolean isDepthTestEnabled() {
		return depthTestEnabled;
	}
	
	
	//****************************** Settings Stuff ******************************
	
	
	public static void setViewport(int x, int y, int width, int height) {
		glViewport(x, y, width, height);
	}
	
	
	public static void setViewport(Vector2i position, Vector2i size) {
		glViewport(position.getX(), position.getY(), size.getX(), size.getY());
	}
	
	
	public static Vector2i getViewportSize() {
		int[] values = new int[4];
		
		glGetIntegerv(GL_VIEWPORT, values);
		
		return new Vector2i(values[2], values[3]);
		
	}
	
	
	public static Vector2i getViewPortPosition() {
		int[] values = new int[4];
		
		glGetIntegerv(GL_VIEWPORT, values);
		
		return new Vector2i(values[0], values[1]);
	}
	
	
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