package output.charts;

import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;
import assets.meshes.Mesh2D;
import assets.meshes.geometry.Color;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;

public class PiChart extends Mesh2D {
	
	private HashMap<String,Group> groups;
	private int slices;
	
	private VertexBuffer colors;
	
	
	public PiChart() {
		this(100);
	}
	
	
	public PiChart(int slices) {
		super(GL_TRIANGLES);
		this.slices = slices;
		this.groups = new HashMap<String,Group>();
		initBuffers();
		useAttributeColor();
	}
	
	
	public PiChart(List<Group> groups) {
		super(GL_TRIANGLES);
		
		this.slices = 100;
		this.groups = new HashMap<String,Group>();
		
		for (Group group : groups) {
			this.groups.put(group.name, group);
		}
		
		initBuffers();
		useAttributeColor();
	}
	
	
	private void initBuffers() {
		FloatBuffer positions = BufferUtils.createFloatBuffer(3 * 2 * slices);
		IntBuffer indices = BufferUtils.createIntBuffer(3 * slices);
		
		float radiansOffset = (float)Math.PI / 2f;
		float radiansSteps = -(float)Math.PI / slices;
		
		for (int i = 0; i < slices; ++i) {
			float radians = radiansOffset + i * radiansSteps;
			
			positions.put((float)Math.cos(radians)).put((float)Math.sin(radians));
			positions.put((float)Math.cos(radians + radiansSteps)).put((float)Math.sin(radians + radiansSteps));
			positions.put(0f).put(0f);
			
			indices.put(i * 3).put(i * 3 + 1).put(i * 3 + 2);
		}
		
		positions.flip();
		indices.flip();
		
		this.setPositionData(positions, 2);
		this.setIndexBuffer(indices);
		
		colors = new VertexBuffer();
		//SLICES * NUM_TRIANGLES * NUM_COLOR_CHANNELS * NUM_BYTES
		colors.setBufferData(slices * 3 * 4 * 4, Buffer.USAGE_DYNAMIC_DRAW);
		
		update();
		
		this.setColorVertexBuffer(colors, 4);
	}
	
	
	private void update() {
		colors.bind();
		ByteBuffer buffer = colors.mapBuffer(Buffer.MAP_WRITE_ONLY);
		buffer.clear();
		
		int total = computeGroupSum();
		
		for (String group : groups.keySet()) {
			Group current = groups.get(group);
			Color color = current.color;
			
			float share = (float)current.amount / (float)total;
			
			while (share > 0) {
				buffer.putFloat(color.getRed())
					.putFloat(color.getGreen())
					.putFloat(color.getBlue())
					.putFloat(color.getAlpha());
				
				buffer.putFloat(color.getRed())
					.putFloat(color.getGreen())
					.putFloat(color.getBlue())
					.putFloat(color.getAlpha());
				
				buffer.putFloat(color.getRed())
					.putFloat(color.getGreen())
					.putFloat(color.getBlue())
					.putFloat(color.getAlpha());
				
				share -= slices / 100f;
			}
			
		}
		
		buffer.flip();
		colors.unmapBuffer();
		colors.unbind();
	}
	
	
	private int computeGroupSum() {
		int sum = 0;
		
		for (String key : groups.keySet()) {
			sum += groups.get(key).amount;
		}
		
		return sum;
	}
	
	
	public void addGroup(String name, Color color, int amount) {
		groups.put(name, new Group(name, color, amount));
		update();
	}
	
	
	public void setGroup(String name, int amount) {
		if (groups.containsKey(name)) {
			groups.get(name).amount = amount;
		} else {
			System.err.println("Unable to find group with given name!");
		}
		update();
	}
	
}


