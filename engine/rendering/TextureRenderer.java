package rendering;

import assets.meshes.Transformable;
import assets.meshes.prefabs.Plane;
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
import assets.textures.Texture;
import math.matrices.Matrix44f;

public class TextureRenderer {
	
	private static final String path = "Shaders/StandardShaders/shaderTexturedMesh";
	
	private static Plane plane;
	
	private static Transformable transformable;
	
	private static ShaderProgram shader;
	
	private static boolean init = false;
	
	public static void init() {
		if (init) return;
		
		plane = new Plane();
		transformable = new Transformable();
		shader = ShaderLoader.loadShader(path + ".vert", path + ".frag");
		init = true;
	}
	
	
	/**
	 * 
	 * Draws the given texture on a flat plane onto the screen.
	 * 
	 * @param texture The texture to be drawn.
	 * @param x The x position of the plane.
	 * @param y The y position of the plane.
	 * @param width The width of the plane.
	 * @param height The height of the plane.
	 */
	public static void draw(Texture texture, float x, float y, float width, float height) {
		init();
		
		transformable.setScaling(width / 2f, height / 2f, 1f);
		transformable.setTranslation(x, y, -0.99f);
		
		shader.bind();
		texture.bind();
		shader.setUniformMatrix4fv("u_mvpMatrix", transformable.getTransformationMatrix());
		
		plane.render();
		
		texture.unbind();
		shader.unbind();
	}
	
	
	/**
	 * 
	 * Draws the given texture on a flat plane onto the screen.
	 * 
	 * @param texture The texture to be drawn.
	 */
	public static void draw(Texture texture) {
		draw(texture, 0f, 0f, 2f, 2f);
	}
	
	
	public static void delete() {
		//shader.
	}

}
