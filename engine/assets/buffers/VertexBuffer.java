package assets.buffers;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Vertex;

import static org.lwjgl.opengl.GL11.*;

public class VertexBuffer extends Buffer {

	public VertexBuffer() {
		super(GL_ARRAY_BUFFER, GL_FLOAT);
	}
	
	
	public int storeData(LinkedList<Vertex> vertices, int flag) {
		
		if (vertices.isEmpty()) {
			return 0;
		}
		
		int layout = vertices.getFirst().getDataLayout();
		
		storeData(vertices, layout, flag);
		
		return layout;
		
	}
	
	
	public void storeData(LinkedList<Vertex> vertices, int layout, int flag) {
		
		if (vertices.isEmpty()) {
			return;
		}
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * vertices.getLast().getDataSize());
		
		for (Vertex vertex : vertices) {
			buffer.put(vertex.toDataBundle(layout));
		}
		
		buffer.flip();
		
		this.setBufferStorage(buffer, flag);
		
	}
	
	
	public int storeData(Vertex[] vertices, int flag) {
		
		int layout = vertices[0].getDataLayout();
		
		storeData(vertices, layout, flag);
		
		return layout;
		
	}
	
	
	public void storeData(Vertex[] vertices, int layout, int flag) {
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * vertices[0].getDataSize());
		
		for (Vertex vertex : vertices) {
			buffer.put(vertex.toDataBundle(layout));
		}
		
		buffer.flip();
		
		this.setBufferStorage(buffer, flag);
		
	}

}
