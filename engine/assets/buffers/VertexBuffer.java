package assets.buffers;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

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
