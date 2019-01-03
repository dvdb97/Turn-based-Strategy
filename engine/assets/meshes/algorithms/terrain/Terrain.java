package assets.meshes.algorithms.terrain;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh3D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL31.*;

public class Terrain extends Mesh3D {
	
	private int width, height;

	private Terrain(int width, int height) {
		super(GL_TRIANGLE_STRIP);
		
		this.width = width;
		this.height = height;
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
		FloatBuffer positions = BufferUtils.createFloatBuffer(height * width * 3);
		FloatBuffer texCoords = BufferUtils.createFloatBuffer(height * width * 2);
		FloatBuffer normals = BufferUtils.createFloatBuffer(height * width * 3);
		IntBuffer indices = BufferUtils.createIntBuffer((height - 1) * (3 + (width - 1) * 2));
		
		float quadWidth = 2.0f / width;
		float quadHeight = 2.0f / height;
		
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				positions.put(-1f + x * quadWidth);
				positions.put(-1f + y * quadHeight);
				positions.put(depthFunc.getDepth(x, y));
				
				texCoords.put(x * quadWidth / 2f);
				texCoords.put(y * quadHeight / 2f);
				
				normals.put(depthFunc.getDepth(x-1, y) - depthFunc.getDepth(x+1, y));
				normals.put(depthFunc.getDepth(x, y-1) - depthFunc.getDepth(x, y+1));
				normals.put(1f);
			}
		}
		
		for (int y = 0; y < height - 1; ++y) {
			
			indices.put(y * height);
			indices.put((y + 1) * height);
			
			for (int x = 0; x < width - 1; ++x) {
				indices.put(y * height + x + 1);
				indices.put((y + 1) * height + x + 1);
			}
			
			indices.put(-1);
		}
		
		Terrain terrain = new Terrain(width, height);
		
		positions.flip();
		texCoords.flip();
		normals.flip();
		indices.flip();
		
		terrain.setPositionData(positions, 3);
		terrain.setTexCoordData(texCoords, 2);
		terrain.setNormalData(normals, 3);
		terrain.setIndexBuffer(indices);
		
		return terrain;
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
	
	
	@Override
	public void render(Camera camera, DirectionalLight light) {
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(-1);
		
		super.render(camera, light);
		
		glDisable(GL_PRIMITIVE_RESTART);
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