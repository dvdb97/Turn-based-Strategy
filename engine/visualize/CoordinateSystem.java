package visualize;

import assets.meshes.Mesh;
import assets.meshes.geometry.Vertex;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINES;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static utils.ColorPalette.*;

public class CoordinateSystem extends Mesh {

	private float range;
	
	public CoordinateSystem(float range) {
		super(GL_LINES);
		
		this.range = range;
		
		generateData();
		int[] indexArray = {0,1,2,3,4,5};
		IntBuffer indexBuffer = CustomBufferUtils.createIntBuffer(indexArray);
		setIndexBuffer(indexBuffer);
	}
	
	
	private void generateData() {
		
		FloatBuffer posData = BufferUtils.createFloatBuffer(6 * 3);
		FloatBuffer colData = BufferUtils.createFloatBuffer(6 * 4);
		
		Vertex vertex;
		
		//X-Axis:
		vertex = new Vertex(-1.0f * range, 0.0f, 0.0f, RED);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		vertex = new Vertex(1.0f * range, 0.0f, 0.0f, RED);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		//Y-Axis:
		vertex = new Vertex(0.0f, -1.0f * range, 0.0f, GREEN);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		vertex = new Vertex(0.0f, 1.0f * range, 0.0f, GREEN);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		//Z-Axis:
		vertex = new Vertex(0.0f, 0.0f, -1.0f * range, BLUE);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		vertex = new Vertex(0.0f, 0.0f, 1.0f * range, BLUE);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		posData.flip();
		colData.flip();
		
		this.setPositionData(posData);
		this.setColorData(colData);
	
	}
	
}
