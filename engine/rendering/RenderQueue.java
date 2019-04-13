package rendering;

import java.util.LinkedList;
import java.util.List;

import assets.IDeletable;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.meshes.specialized.Quad;
import assets.scene.Scene;
import assets.textures.Texture2D;
import interaction.Window;
import rendering.framebuffers.Framebuffer;
import rendering.postprocessing.PostprocessingLayer;


public class RenderQueue implements IDeletable {
	protected Window window;
	protected LinkedList<Mesh> meshes;
	protected Scene scene;
	
	private PostprocessingLayer postprocessing = null;
	private Framebuffer framebuffer;
	private Texture2D texture;
	
	/**
	 * Set up the render queue.
	 */
	public RenderQueue(Window window, Scene scene) {
		this.window = window;
		this.meshes = new LinkedList<Mesh>();
		this.scene = scene;
		this.setUpFramebuffer();
	}
	
	
	private void setUpFramebuffer() {
		this.framebuffer = new Framebuffer();
		this.texture = Texture2D.generateColorTexture(window.getWidth(), window.getHeight());
		this.framebuffer.addColorAttachment(texture);
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
	
	
	public void setPostprocessingLayer(PostprocessingLayer layer) {
		this.postprocessing = layer;
	}
	
	
	public void render() {
		mapShadows();
		renderMeshes();
		runPostprocessing();
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
		framebuffer.clear();
		
		for (Mesh mesh : meshes) {
			mesh.render(scene);
		}
		
		framebuffer.unbind();
	}
	
	
	protected void runPostprocessing() {
		postprocessing.runPostprocessing(texture);
	}


	@Override
	public void delete() {
		for (Mesh mesh : meshes) {
			mesh.delete();
		}
		
		postprocessing.delete();
	}
	
}
