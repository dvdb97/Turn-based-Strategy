package visualize;

import assets.meshes.Mesh;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;

import static org.lwjgl.opengl.GL11.GL_LINES;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class CoordinateSystem extends Mesh {

	private float range;
	
	public CoordinateSystem(float range) {
		super(GL_LINES);
		
		this.range = range;
		
		generateData();
		
	}
	
	
	private void generateData() {
		
		FloatBuffer posData = BufferUtils.createFloatBuffer(6 * 3);
		FloatBuffer colData = BufferUtils.createFloatBuffer(6 * 4);
		
		Vertex vertex;
		
		Color red = new Color(1.0f, 0.0f, 0.0f, 1.0f);
		Color green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
		Color blue = new Color(0.0f, 0.0f, 1.0f, 1.0f);
		
		
		//X-Axis:
		vertex = new Vertex(-1.0f * range, 0.0f, 0.0f, red);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		vertex = new Vertex(1.0f * range, 0.0f, 0.0f, red);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		//Y-Axis:
		vertex = new Vertex(0.0f, -1.0f * range, 0.0f, green);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		vertex = new Vertex(0.0f, 1.0f * range, 0.0f, green);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		//Z-Axis:
		vertex = new Vertex(0.0f, 0.0f, -1.0f * range, blue);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		vertex = new Vertex(0.0f, 0.0f, 1.0f * range, blue);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		posData.flip();
		colData.flip();
		
		this.setPositionData(posData);
		this.setColorData(colData);
	
	}
	
}
