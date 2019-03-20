package output.charts;

import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh2D;
import assets.meshes.geometry.Color;
import assets.scene.Scene;
import utils.ColorPalette;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.BufferUtils;

public class PieChart extends Mesh2D {
	
	private HashMap<String,Group> groups;
	private int slices;
	
	private VertexBuffer colors;
	private boolean updated;
	
	
	/**
	 * Creates a pie chart that has consists of 100 triangles.
	 */
	public PieChart() {
		this(100);
	}
	
	
	/**
	 * 
	 * Creates a pie chart that consists of a custom number of triangles.
	 * 
	 * @param slices The number of triangles.
	 */
	public PieChart(int slices) {
		super(GL_TRIANGLES);
		this.slices = slices;
		
		reset();
		initBuffers();
		useAttributeColor();
	}
	
	
	/**
	 * 
	 * Creates a pie chart that consists of 100 triangles. 
	 * 
	 * @param groups The groups to be displayed by the pie chart.
	 */
	public PieChart(List<Group> groups) {
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
		float radiansSteps = -2f * (float)Math.PI / slices;
		
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
		
		//Change the triangle's colors by mapping the color buffer.
		ByteBuffer buffer = colors.mapBuffer(Buffer.MAP_WRITE_ONLY);
		buffer.clear();
		
		//The sum of all amounts
		int total = computeGroupSum();
		
		//Iterate through all groups
		Iterator<String> keys = groups.keySet().iterator();
		String key = keys.next();
		
		//The color and the share of the current group
		Color color = groups.get(key).color;
		float share = (float)groups.get(key).amount / (float)total;
		
		for (int i = 0; i < slices; ++i) {			
			//When enough triangles were assigned to this group continue with the next one.
			if (share <= 0f) {
				key = keys.next();
				color = groups.get(key).color;
				share = (float)groups.get(key).amount / (float)total;
			}
			
			//Add a new color for every vertex of the current triangle.
			for (int j = 0; j < 3; ++j) {
				buffer.putFloat(color.getRed())
				  	  .putFloat(color.getGreen())
				  	  .putFloat(color.getBlue())
				  	  .putFloat(color.getAlpha());
			}
			
			share -= 1f / slices;
		}
		
		buffer.flip();
		colors.unmapBuffer();
		colors.unbind();
		
		updated = true;
	}
	
	
	private int computeGroupSum() {
		int sum = 0;
		
		for (String key : groups.keySet()) {
			sum += groups.get(key).amount;
		}
		
		return sum;
	}
	
	
	/**
	 * 
	 * Add a new group to the pie chart.
	 * 
	 * @param name The name of the new group.
	 * @param color The color of the new group.
	 * @param amount A custom value. The share of this group is defined by this group's amount
	 * in relation to the sum of all other group's amounts.
	 */
	public void addGroup(String name, Color color, int amount) {
		//Check if the name of the group is allowed.
		if (name.equals("")) {
			System.err.println("Invalid group name! Empty strings are reserved for a 'null'-group!");
			return;
		}		
		
		//Remove the default group.
		if (groups.containsKey("")) {
			groups.remove("");
		}
		
		groups.put(name, new Group(name, color, amount));
		updated = false;
	}
	
	
	/**
	 * 
	 * Changes the amount of a group.
	 * 
	 * @param name The name of the group to be changed.
	 * @param amount The new value of the group's amount.
	 */
	public void setGroup(String name, int amount) {
		if (groups.containsKey(name)) {
			groups.get(name).amount = amount;
		} else {
			System.err.println("Unable to find group with given name!");
		}
		
		updated = false;
	}
	
	
	public void incrementGroup(String name) {
		if (groups.containsKey(name)) {
			groups.get(name).amount++;
		} else {
			System.err.println("Unable to find group with given name!");
		}
		
		updated = false;
	}
	
	
	/**
	 *
	 * Remove a group from the PieChart.
	 * 
	 * @param name The name of the group to be removed.
	 */
	public void removeGroup(String name) {
		if (groups.containsKey(name)) {
			groups.remove(name);
		} else {
			System.err.println("Unable to remove group with given name! There is no group with this name.");
		}
		
		updated = false;
	}
	
	
	/**
	 * Remove all groups and add the default group.
	 */
	public void reset() {
		this.groups = new HashMap<String,Group>();
		this.groups.put("", new Group("", ColorPalette.WHITE, 1));
		updated = false;
	}


	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		if (!updated) update();
		
		super.onDrawStart(camera, light);
	}


	@Override
	protected void onDrawStart(Scene scene) {
		if (!updated) update();
		
		super.onDrawStart(scene);
	}
	
}


