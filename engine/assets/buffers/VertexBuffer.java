package assets.buffers;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Vertex;

import static org.lwjgl.opengl.GL11.*;

public class VertexBuffer extends Buffer {

	
	public VertexBuffer() {
		super(GL_ARRAY_BUFFER, GL_FLOAT);
	}
	
	
	public VertexBuffer(LinkedList<Vertex> vertices, int flag) {
		super(GL_ARRAY_BUFFER, GL_FLOAT);
		
		this.storeDataInterleaved(vertices, flag);
	}
	
	
	public VertexBuffer(LinkedList<Vertex> vertices, int layout, int flag) {
		super(GL_ARRAY_BUFFER, GL_FLOAT);
		
		this.storeDataInterleaved(vertices, layout, flag);
	}
	
	
	public VertexBuffer(FloatBuffer data, int flag) {
		super(GL_ARRAY_BUFFER, GL_FLOAT);
		
		this.setBufferStorage(data, flag);
	}
	
	
	public int storeDataInterleaved(List<Vertex> vertices, int flag) {
		
		if (vertices.isEmpty()) {
			return 0;
		}
		
		int layout = vertices.get(0).getDataLayout();
		
		storeDataInterleaved(vertices, layout, flag);
		
		return layout;
		
	}
	
	
	public void storeDataInterleaved(List<Vertex> vertices, int layout, int flag) {
		
		if (vertices.isEmpty()) {
			return;
		}
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * Vertex.getSize(layout));
		
		for (Vertex vertex : vertices) {			
			buffer.put(vertex.toDataBundle(layout));
		}
		
		buffer.flip();
		
		this.setBufferStorage(buffer, flag);
		
	}
	
	
	public int storeDataInterleaved(Vertex[] vertices, int flag) {
		
		int layout = vertices[0].getDataLayout();
		
		storeDataInterleaved(vertices, layout, flag);
		
		return layout;
		
	}
	
	
	public void storeDataInterleaved(Vertex[] vertices, int layout, int flag) {
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.getSize(layout));
		
		for (Vertex vertex : vertices) {
			buffer.put(vertex.toDataBundle(layout));
		}
		
		buffer.flip();
		
		this.setBufferStorage(buffer, flag);
		
	}
	
	
	/**
	 * 
	 * TODO
	 * 
	 * @param vertices
	 * @param flag
	 * @return
	 */
	public int storeDataBlockwise(List<Vertex> vertices, int flag) {
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.get(0).getDataSize() * vertices.size());
		
		for (Vertex vertex : vertices) {
			vertex.putPositionData(buffer);
		}
		
		for (Vertex vertex : vertices) {
			vertex.putColorData(buffer);
		}
		
		for (Vertex vertex : vertices) {
			vertex.putTexPosData(buffer);
		}
		
		for (Vertex vertex : vertices) {
			vertex.putNormalData(buffer);
		}
		
		buffer.flip();
		
		this.setBufferStorage(buffer, flag);
			
		return vertices.get(0).getDataLayout();
			
	}
	
	
	/**
	 * 
	 * TODO
	 * 
	 * @param vertices
	 * @param layout
	 * @param flag
	 */
	public void storeDataBlockwise(List<Vertex> vertices, int layout, int flag) {

		FloatBuffer buffer = BufferUtils.createFloatBuffer(Vertex.getSize(layout) * vertices.size());
		
		byte[] sizes = Vertex.getSizes(layout);
		
		for (Vertex vertex : vertices) {
			vertex.putPositionData(buffer, sizes[0]);
		}
		
		for (Vertex vertex : vertices) {
			vertex.putColorData(buffer, sizes[1]);
		}
		
		for (Vertex vertex : vertices) {
			vertex.putTexPosData(buffer, sizes[2]);
		}
		
		for (Vertex vertex : vertices) {
			vertex.putNormalData(buffer, sizes[3]);
		}
		
		buffer.flip();
		
		this.setBufferStorage(buffer, flag);

	}
	
	
	public void storeDataBlockwise(Vertex[] vertices, int flag) {
		//TODO
	}
	
	
	public void storeDataBlockwise(Vertex[] vertices, int layout, int flag) {
		//TODO
	}
	
	
	public static VertexBuffer[] storeDataMultipleBuffers(List<Vertex> vertices, int flag) {
		return null;
	}
	
	
	public static VertexBuffer[] storeDataMultipleBuffers(List<Vertex> vertices, int layout, int flag) {
		VertexBuffer[] buffers = new VertexBuffer[8];
		
		byte[] sizes = Vertex.getSizes(layout);
		
		
		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(vertices.size() * sizes[0]);
		for (Vertex vertex : vertices) {
			vertex.putPositionData(posBuffer, sizes[0]);
		}
		buffers[0] = new VertexBuffer(posBuffer, VertexBuffer.DYNAMIC_STORAGE);
		
		
		FloatBuffer colBuffer = BufferUtils.createFloatBuffer(vertices.size() * sizes[1]);
		for (Vertex vertex : vertices) {
			vertex.putColorData(colBuffer, sizes[1]);
		}
		buffers[1] = new VertexBuffer(colBuffer, VertexBuffer.DYNAMIC_STORAGE);
		
		
		FloatBuffer texPosBuffer = BufferUtils.createFloatBuffer(vertices.size() * sizes[2]);
		for (Vertex vertex : vertices) {
			vertex.putTexPosData(texPosBuffer, sizes[2]);
		}
		buffers[2] = new VertexBuffer(texPosBuffer, VertexBuffer.DYNAMIC_STORAGE);
		
		
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(vertices.size() * sizes[2]);
		for (Vertex vertex : vertices) {
			vertex.putNormalData(normalBuffer, sizes[3]);
		}
		buffers[3] = new VertexBuffer(normalBuffer, VertexBuffer.DYNAMIC_STORAGE);
		
		
		return buffers;		
	}
	
	
	public static VertexBuffer[] storeDataMultipleBuffers(Vertex[] vertices, int flag) {
		return null;
	}
	
	
	public static VertexBuffer[] storeDataMultipleBuffers(Vertex[] vertices, int layout, int flag) {
		return null;
	}

}
