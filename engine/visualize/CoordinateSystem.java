package visualize;

import assets.meshes.geometry.Color;
import assets.meshes.geometry.VertexLegacy;
import assets.models.Array_Model;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class CoordinateSystem extends Array_Model {

	private float range;
	
	public CoordinateSystem(float range) {
		super(GL_LINES);
		
		this.range = range;
		
		generateData();
		
	}
	
	
	private void generateData() {
		
		FloatBuffer posData = BufferUtils.createFloatBuffer(6 * 3);
		FloatBuffer colData = BufferUtils.createFloatBuffer(6 * 4);
		
		VertexLegacy vertex;
		
		Color red = new Color(1.0f, 0.0f, 0.0f, 1.0f);
		Color green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
		Color blue = new Color(0.0f, 0.0f, 1.0f, 1.0f);
		
		
		//X-Axis:
		vertex = new VertexLegacy(-1.0f * range, 0.0f, 0.0f, red);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		vertex = new VertexLegacy(1.0f * range, 0.0f, 0.0f, red);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		//Y-Axis:
		vertex = new VertexLegacy(0.0f, -1.0f * range, 0.0f, green);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		vertex = new VertexLegacy(0.0f, 1.0f * range, 0.0f, green);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		//Z-Axis:
		vertex = new VertexLegacy(0.0f, 0.0f, -1.0f * range, blue);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		vertex = new VertexLegacy(0.0f, 0.0f, 1.0f * range, blue);
		posData.put(vertex.getPositionData());
		colData.put(vertex.getColorData());
		
		
		posData.flip();
		colData.flip();
		
		this.setVertexPositionData(posData, 3, GL_STATIC_DRAW);
		this.setVertexColorData(colData, 4, GL_STATIC_DRAW);
	
	}
	
	

}
