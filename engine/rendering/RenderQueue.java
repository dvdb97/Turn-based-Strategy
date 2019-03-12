package rendering;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

import java.util.LinkedList;
import java.util.List;

import assets.IDeletable;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.meshes.specialized.Quad;
import assets.postprocessing.DefaultPostprocessing;
import assets.postprocessing.PostprocessingLayer;
import assets.scene.Scene;
import assets.textures.Texture2D;
import assets.textures.Texture2DMultisample;
import interaction.Window;
import rendering.framebuffers.Framebuffer;

public class RenderQueue implements IDeletable {
	
	protected Window window;
	
	protected LinkedList<Mesh> meshes;
	protected LinkedList<PostprocessingLayer> postprocessingQueue;
	
	protected Scene scene;
	protected Framebuffer framebuffer;
	
	protected Framebuffer pp_fbo;
	protected Texture2D screenTexture;
	protected Quad postprocessingQuad;
	
	/**
	 * Set up the render queue.
	 */
	public RenderQueue(Window window, Scene scene) {
		this.window = window;
		this.meshes = new LinkedList<Mesh>();
		
		this.postprocessingQueue = new LinkedList<PostprocessingLayer>();
		this.postprocessingQuad = new Quad();
		this.postprocessingQuad.useTextureColor();
		
		this.scene = scene;
		
		this.setUpFramebuffers(window.getFrameBufferWidth(), window.getFrameBufferHeight(), 4);
	}
	
	
	private void setUpFramebuffers(int windowWidth, int windowHeight, int numSamples) {
		this.framebuffer = new Framebuffer();
		Texture2DMultisample texture = new Texture2DMultisample(windowWidth, windowHeight, numSamples);
		this.framebuffer.addColorAttachment(texture);		
		
		this.screenTexture = Texture2D.generateColorTexture(windowWidth, windowHeight);
		this.pp_fbo = new Framebuffer();
		//Make sure the framebuffer stores the color values in our texture
		this.pp_fbo.addColorAttachment(screenTexture);
	}
	
	
	/**
	 * 
	 * Add a set of meshes to the render queue.
	 * 
	 * @param meshes The meshes to add to the render queue
	 */
	public void addMeshes(Mesh[] meshes) {
		for (Mesh mesh : meshes) {
			this.meshes.add(mesh);
		}
	}
	
	
	/**
	 * 
	 * Add a set of meshes to the render queue.
	 * 
	 * @param meshes The meshes to add to the render queue
	 */
	public void addMeshes(List<Mesh> meshes) {
		for (Mesh mesh : meshes) {
			this.meshes.add(mesh);
		}
	}
	
	
	/**
	 * 
	 * Add a mesh to the render queue.
	 * 
	 * @param mesh The mesh to add to the render queue
	 */
	public void addMesh(Mesh mesh) {
		this.meshes.add(mesh);
	}
	
	
	/**
	 * 
	 * Remove a mesh from the render queue.
	 * 
	 * @param mesh The mesh to remove from the render queue
	 */
	public void removeMesh(Mesh mesh) {
		this.meshes.remove(mesh);
	}
	
	
	/**
	 * 
	 * Add a set of postprocessing layer to the stack.
	 * 
	 * @param layers The layers to add to the stack
	 */
	public void addPostprocessingLayers(PostprocessingLayer[] layers) {
		for (PostprocessingLayer layer : layers) {
			this.postprocessingQueue.add(layer);
		}
	}
	
	
	/**
	 * 
	 * Add a set of postprocessing layer to the stack.
	 * 
	 * @param layers The layers to add to the stack
	 */
	public void addPostprocessingLayers(List<PostprocessingLayer> layers) {
		for (PostprocessingLayer layer : layers) {
			this.postprocessingQueue.add(layer);
		}
	}
	
	
	/**
	 * 
	 * Add a postprocessing layer to the stack.
	 * 
	 * @param layer The layer to add to the stack
	 */
	public void addPostprocessingLayer(PostprocessingLayer layer) {
		postprocessingQueue.add(layer);
	}
	
	
	/**
	 * 
	 * Remove a postprocessing layer from the stack.
	 * 
	 * @param layer The layer to be removed from the stack
	 */
	public void removePostprocessingLayer(PostprocessingLayer layer) {
		postprocessingQueue.remove(layer);
	}
	
	
	public void render() {
		mapShadows();
		renderMeshes();
		//runPostprocessing();
	}
	
	
	public void show() {		
		glfwPollEvents();
		glfwSwapBuffers(window.getWindowID());
	}
	
	
	public void clear() {
		framebuffer.bind();
		framebuffer.clear();
		framebuffer.unbind();
		
		pp_fbo.bind();
		pp_fbo.clear();
		pp_fbo.unbind();
	}
	
	
	protected void mapShadows() {		
		DirectionalLight light = scene.getLightSource();
		
		if (light == null || !light.hasShadowMap()) {
			return;
		}
		
		light.startShadowMapPass();
		
		for (Mesh mesh : meshes) {
			if (mesh.castShadows()) {
				light.passToShadowMap(mesh);
			}
		}
		
		light.endShadowMapPass();
	}
	
	
	protected void renderMeshes() {
		framebuffer.bind();
		
		for (Mesh mesh : meshes) {
			mesh.render(scene);
		}
		
		framebuffer.unbind();
	}
	
	
	protected void runPostprocessing() {		
		pp_fbo.blitFramebuffer(framebuffer);
		
		if (postprocessingQueue.size() > 0) {
			pp_fbo.bind();
			
			for (PostprocessingLayer layer : postprocessingQueue) {
				layer.setTexture(screenTexture);
				layer.render();
			}
			
			pp_fbo.unbind();
		}
		
		postprocessingQuad.setTexture(screenTexture);
		postprocessingQuad.render();
	}


	@Override
	public void delete() {
		for (Mesh mesh : meshes) {
			mesh.delete();
		}
		
		for (PostprocessingLayer layer : postprocessingQueue) {
			layer.delete();
		}
		
		framebuffer.delete();
		pp_fbo.delete();
	}
	
}
