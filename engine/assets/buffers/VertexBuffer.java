package assets.buffers;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
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
	
	
	public void storeDataBlockwise(List<Vertex> vertices, int flag) {
		//TODO
	}
	
	
	public void storeDataBlockwise(List<Vertex> vertices, int layout, int flag) {
		//TODO
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
		return null;
	}
	
	
	public static VertexBuffer[] storeDataMultipleBuffers(Vertex[] vertices, int flag) {
		
	}
	
	
	public static VertexBuffer[] storeDataMultipleBuffers(Vertex[] vertices, int layout, int flag) {
		
	}

}
