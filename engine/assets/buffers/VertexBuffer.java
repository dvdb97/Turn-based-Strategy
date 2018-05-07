package assets.buffers;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

public class VertexBuffer extends Buffer {

	public VertexBuffer() {
		super(GL_ARRAY_BUFFER, GL_FLOAT);
	}

}
