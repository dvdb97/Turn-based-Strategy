package output.charts;

import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;
import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.meshes.Mesh2D;
import assets.scene.Scene;
import assets.shaders.standardShaders.SpriteShader;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;

import org.lwjgl.BufferUtils;

public class FunctionGraph2D extends Mesh2D {
	
	private VertexBuffer positions;
	
	private LinkedList<Float> values;
	
	
	public FunctionGraph2D(int numSamples) {
		super(GL_LINE_STRIP);
		initBuffers(numSamples);
	}
	
	
	private void initBuffers(int numSamples) {
		this.values = new LinkedList<Float>();
		IntBuffer indices = BufferUtils.createIntBuffer(numSamples);
		
		for (int i = 0; i < numSamples; ++i) {
			values.add(0f);
			indices.put(i);
		}
		
		indices.flip();
		
		positions = new VertexBuffer();
		positions.setBufferData(numSamples * 2 * 4, Buffer.USAGE_STREAM_DRAW);
		
		update();
		
		this.setVertexBuffer(positions, 0, 2);
		this.setIndexBuffer(indices);
	}
	
	
	private void update() {
		positions.bind();
		ByteBuffer buffer = positions.mapBuffer(Buffer.MAP_WRITE_ONLY);
		buffer.clear();
		
		for (int i = 0; i < values.size(); ++i) {
			//The x position
			buffer.putFloat(i * 2f / values.size() -1f);
			
			//The y position
			buffer.putFloat(values.get(i));
		}
		
		buffer.flip();
		positions.unmapBuffer();
		positions.unbind();
	}
	
	
	public void setSample(float sample) {
		this.values.add(sample);
		this.values.removeFirst();
	}
	
	
	public void setSamples(float[] samples) {
		for (float f : samples) {
			setSample(f);
		}
	}
	
	
	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		this.update();
		super.onDrawStart(camera, light);
	}
	
}
