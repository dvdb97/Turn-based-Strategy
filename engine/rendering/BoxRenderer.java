package rendering;

import assets.cameras.Camera;
import assets.meshes.geometry.Color;
import assets.meshes.prefabs.WireframeBox;
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
import math.matrices.Matrix44f;

/**
 * 
 * @author Dario
 * 
 * TODO: Also add complete meshes (not only wireframes).
 *
 */
public class BoxRenderer {
	
	private static final String path = "Shaders/StandardShaders/uniformColor";
	
	private static WireframeBox box;
	
	private static ShaderProgram shader;
	
	private static boolean init = false;
	
	
	public static void init() {
		if (init) return;
		
		box = new WireframeBox();		
		shader = ShaderLoader.loadShader(path + ".vert", path + ".frag");
		init = true;
	}
	
	
	/**
	 * 
	 * Draws the outlines of a box onto the screen.
	 * 
	 * @param model The model matrix of the box.
	 * @param camera The camera from whose perspective we look at the box.
	 * @param color The color of the box.
	 */
	public static void draw(Matrix44f model, Camera camera, Color color) {
		init();
		
		shader.bind();
		shader.setCamera(camera);
		shader.setModelMatrix(model);
		shader.setUniformVector4f("u_Color", color);
		
		box.render();
		
		shader.unbind();
	}
	
	
	public static void draw(Matrix44f model, Camera camera) {
		draw(model, camera, Color.WHITE);
	}
	
	
	public static void delete() {
		box.delete();
		shader.delete();
	}
	
}
