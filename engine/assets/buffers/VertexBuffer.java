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
	
	
	public VertexBuffer(FloatBuffer data, int flag) {
		super(GL_ARRAY_BUFFER, GL_FLOAT);
		
		this.setBufferStorage(data, flag);
	}
	
	
	public VertexBuffer(FloatBuffer data) {
		this(data, DYNAMIC_STORAGE);
	}

}
