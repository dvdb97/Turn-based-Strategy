package assets.models;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.prefs.BackingStoreException;

import assets.buffers.ArrayBuffer;
import assets.buffers.Buffer;
import assets.meshes.geometry.Vertex;

import static org.lwjgl.opengl.GL11.*;

public class TestModel {
	
	private VertexArrayObject vao;
	
	
	private static final int POS_INDEX = 0;
	
	private ArrayBuffer posBuffer;
	private int posSize, posStride, posOffset;
	
	private static final int COL_INDEX = 1;

	private ArrayBuffer colBuffer;
	private int colSize, colStride, colOffset;
	
	private static final int TEX_POS_INDEX = 2;
	
	private ArrayBuffer texPosBuffer;
	private int texPosSize, texPosStride, texPosOffset;
	
	private static final int NORMAL_INDEX = 3;
	
	private ArrayBuffer normalBuffer;
	private int normalSize, normalStride, normalOffset;
	
	
	public TestModel() {
		vao = new VertexArrayObject();
	}
	
	
	public void setVertexData(Vertex[] vertices, int[] inices) {
		//TODO
	}
	
	
	public void setVertexData(LinkedList<Vertex> vertices, int[] indices) {
		//TODO
	}
	
	
	public void setVertexData(ArrayList<Vertex> vertices, int[] indices) {
		//TODO
	}
	
	
	public void setVertexPositionData(ArrayBuffer buffer, int size, int stride, int offset) {
		vao.setVertexAttributePointer(buffer, POS_INDEX, size, stride, offset);
		
		this.posSize = size;
		this.posStride = stride;
		this.posOffset = offset;
		this.posBuffer = buffer;
		
	}
	
	
	public void setVertexPositionData(FloatBuffer data, int size, int stride, int offset, int flag) {
		ArrayBuffer buffer = new ArrayBuffer(GL_FLOAT);
		buffer.setBufferStorage(data, flag);
		
		this.setVertexPositionData(buffer, size, stride, offset);
	}
	
	
	public void setVertexPositionData(FloatBuffer data, int size, int flag) {
		this.setVertexPositionData(data, size, 0, 0, flag);
	}
	
}
