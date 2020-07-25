package rendering;


import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_3D;
import static org.lwjgl.opengl.GL43.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import assets.meshes.Mesh;
import assets.scene.Scene;
import debugging.ErrorListener;
import interaction.Window;
import math.vectors.Vector2i;
import math.vectors.Vector4f;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;


public class Renderer {
	
	//A reference to the window this RenderEngine is supposed to draw on 
	private static Window window;
		
	//Settings:
	private static boolean depthTest = false;
	
	private static double timer;
	
	private static RenderQueue renderQueue;
	
	
	public static void init(Window window) {
		createCapabilities();
		
		Renderer.window = window;
		
		timer = glfwGetTime();
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		glEnable(GL_STENCIL_TEST);
		glEnable(GL_TEXTURE_3D);
		
		//glEnable(GL_DEBUG_OUTPUT);
		//glDebugMessageCallback(new ErrorListener(ErrorListener.HIGH_SEVERITY), 0);
	}
	
	
	/**
	 * 
	 * @param scene The scene in which the meshes in the render queue are rendered.
	 */
	public static void initRenderQueue(Scene scene) {
		renderQueue = new RenderQueue(window, scene);
	}
	
	
	/**
	 * 
	 * @param renderQueue A predefined render queue.
	 */
	public static void setRenderQueue(RenderQueue renderQueue) {
		Renderer.renderQueue = renderQueue;
	}
	
	
	public static void addMeshToRenderQueue(Mesh mesh) {
		renderQueue.addMesh(mesh);
	}
	
	
	public static void addMeshesToRenderQueue(Mesh[] meshes) {
		renderQueue.addMeshes(meshes);
	}
	
	
	public static void addMeshesToRenderQueue(List<Mesh> meshes) {
		renderQueue.addMeshes(meshes);
	}
	
	
	public static void removeMeshFromRenderQueue(Mesh mesh) {
		renderQueue.removeMesh(mesh);
	}
	
	
	public static void close() {
		renderQueue.delete();
	}
	
	
	//****************************** Draw Functions ******************************
	
	
	public static void render() {
		renderQueue.render();
	}
	
	
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}
	
	
	public static void clear(int bits) {
		glClear(bits);
	}
	
	
	public static void swapBuffers() {
		glfwPollEvents();
		glfwSwapBuffers(window.getWindowID());
	}
	
	
	public static void takeScreenshot(String path, String format) {
		glReadBuffer(GL_FRONT);
		
		//The size of the framebuffer
		int width = window.getFrameBufferWidth();
		int height = window.getFrameBufferHeight();
		
		//Bytes per pixel
		int bpp = 4;
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
		LocalDateTime now = LocalDateTime.now();  
		
		File file = new File(path + "/screenshot_" + dtf.format(now)+ "." + format.toLowerCase());
		
		for(int x = 0; x < width; x++) 
		{
		    for(int y = 0; y < height; y++)
		    {
		        int i = (x + (width * y)) * bpp;
		        int r = buffer.get(i) & 0xFF;
		        int g = buffer.get(i + 1) & 0xFF;
		        int b = buffer.get(i + 2) & 0xFF;
		        image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
		    }
		}
		   
		try {
		    ImageIO.write(image, format, file);
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	
	public static void displayFPS() {
		
		double fps = 1.0 / (glfwGetTime() - timer);
		
		System.out.println("FPS: " + fps);
		
		timer = glfwGetTime();
		
	}
	
	
	//****************************** getters and setters *************************************
		
	
	public static boolean isDepthTestEnabled() {
		return depthTest;
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
		if (depthTest) {
			System.out.println("Depth Test is already enabled!");
			
			return;
		}
		
		depthTest = true;
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
	}
	
	
	public static void disableDepthTest() {
		if (!depthTest) {
			System.out.println("Depth is already disabled!");
			
			return;
		}
		
		depthTest = false;
		
		glDisable(GL_DEPTH_TEST);
	}
	
	
	public static void cullBackFace() {
		glCullFace(GL_BACK);
	}
	
	
	public static void cullFrontFace() {
		glCullFace(GL_FRONT);
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