package assets.models;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.prefs.BackingStoreException;

import assets.buffers.VertexBuffer;
import assets.buffers.Buffer;
import assets.meshes.geometry.VertexLegacy;

import static org.lwjgl.opengl.GL11.*;

public class TestModel {
	
	private VertexArrayObject vao;
	
	
	private static final int POS_INDEX = 0;
	
	private VertexBuffer posBuffer;
	private int posSize, posStride, posOffset;
	
	private static final int COL_INDEX = 1;

	private VertexBuffer colBuffer;
	private int colSize, colStride, colOffset;
	
	private static final int TEX_POS_INDEX = 2;
	
	private VertexBuffer texPosBuffer;
	private int texPosSize, texPosStride, texPosOffset;
	
	private static final int NORMAL_INDEX = 3;
	
	private VertexBuffer normalBuffer;
	private int normalSize, normalStride, normalOffset;
	
	
	public TestModel() {
		vao = new VertexArrayObject();
	}
	
	
	public void setVertexData(VertexLegacy[] vertices, int[] inices) {
		//TODO
	}
	
	
	public void setVertexData(LinkedList<VertexLegacy> vertices, int[] indices) {
		//TODO
	}
	
	
	public void setVertexData(ArrayList<VertexLegacy> vertices, int[] indices) {
		//TODO
	}
	
	
	public void setVertexPositionData(VertexBuffer buffer, int size, int stride, int offset) {
		vao.setVertexAttributePointer(buffer, POS_INDEX, size, stride, offset);
		
		this.posSize = size;
		this.posStride = stride;
		this.posOffset = offset;
		this.posBuffer = buffer;
		
	}
	
	
	public void setVertexPositionData(FloatBuffer data, int size, int stride, int offset, int flag) {
		VertexBuffer buffer = new VertexBuffer(GL_FLOAT);
		buffer.setBufferStorage(data, flag);
		
		this.setVertexPositionData(buffer, size, stride, offset);
	}
	
	
	public void setVertexPositionData(FloatBuffer data, int size, int flag) {
		this.setVertexPositionData(data, size, 0, 0, flag);
	}
	
}
