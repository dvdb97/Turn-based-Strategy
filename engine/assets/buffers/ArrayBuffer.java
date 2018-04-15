package assets.buffers;

import static org.lwjgl.opengl.GL15.*;

public class ArrayBuffer extends Buffer {

	public ArrayBuffer(int dataType) {
		super(GL_ARRAY_BUFFER, dataType);
	}

}
