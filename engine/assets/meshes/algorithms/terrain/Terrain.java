package assets.meshes.algorithms.terrain;

import java.util.ArrayList;
import java.util.List;

import assets.meshes.Mesh;
import assets.meshes.geometry.Vertex;
import math.vectors.Vector2f;
import math.vectors.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL31.*;

public class Terrain extends Mesh {
	
	private int width, height;

	private Terrain(int width, int height, List<Vertex> vertices, List<Integer> indices) {
		super(vertices, indices, GL_TRIANGLE_STRIP);
		
		this.width = width;
		this.height = height;
	}
	
	
	public void render() {
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(-1);
		
		super.render();
		
		glDisable(GL_PRIMITIVE_RESTART);
	}
	
	
	/**
	 * 
	 * Generates a terrain Mesh with the given width, height and using the
	 * noise to define the depth. The vertices' x, y and z position values 
	 * will range from -1 to 1.
	 * 
	 * @param width The width in vertices.
	 * @param height The height in vertices.
	 * @param depth The noise defining the terrain's landscape. The values
	 * have to range between -1 and 1.
	 */
	public static Terrain generate(int width, int height, ElevationFunction depthFunc) {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		float quadWidth = 2.0f / width;
		float quadHeight = 2.0f / height;
		
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				Vector3f position = new Vector3f(-1f + x * quadWidth, -1f + y * quadHeight, depthFunc.getDepth(x, y));
				
				Vector2f texCoords = new Vector2f(x * quadWidth / 2f, y * quadHeight / 2f);
				
				Vector3f normal = new Vector3f(depthFunc.getDepth(x-1, y) - depthFunc.getDepth(x+1, y), 
											   depthFunc.getDepth(x, y-1) - depthFunc.getDepth(x, y+1), 
											   1f);
				
				vertices.add(new Vertex(position.toArray(), texCoords.toArray(), normal.toArray()));
			}
		}
		
		for (int y = 0; y < height - 1; ++y) {
			
			indices.add(y * height);
			indices.add((y + 1) * height);
			
			for (int x = 0; x < width - 1; ++x) {
				indices.add(y * height + x + 1);
				indices.add((y + 1) * height + x + 1);
			}
			
			indices.add(-1);
		}
		
		return new Terrain(width, height, vertices, indices);
	}
	
	
	/**
	 * 
	 * Generates a terrain Mesh using the noise to define the depth. 
	 * The vertices' x, y and z position values will range from -1 to 1.
	 * 
	 * @param depth The noise defining the terrain's landscape. The values
	 * have to range between -1 and 1.
	 */
	public static Terrain generate(ElevationFunction depthFunc) {
		return generate(depthFunc.getWidth(), depthFunc.getHeight(), depthFunc);
	}
	
	
	/**
	 * 
	 * @return Returns the width of the Terrain.
	 */
	public int getWidth() {
		return width;
	}
	
	
	/**
	 * 
	 * @return Returns the height of the Terrain.
	 */
	public int getHeight() {
		return height;
	}
}