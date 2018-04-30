package assets.models;

import assets.GLObject;
import assets.buffers.ArrayBuffer;
import assets.buffers.Buffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL41.glVertexAttribLPointer;

import java.util.HashMap;
import java.util.LinkedList;

public class VertexArrayObject extends GLObject {
		
	/**
	 * 
	 * @author Dario
	 * 
	 * One vertex attribute of this vertex attribute object. This
	 * vertex attribute stores a reference to the buffer containing
	 * the data and information about the layout of the data
	 *
	 */
	private class VertexAttribute {
		
		private Buffer buffer;
		
		private int size, stride, offset;

		public VertexAttribute(Buffer buffer, int size, int stride, int offset) {
			
			this.buffer = buffer;
			this.size = size;
			this.stride = stride;
			this.offset = offset;
		}

		public Buffer getBuffer() {
			return buffer;
		}

		public int getSize() {
			return size;
		}

		public int getStride() {
			return stride;
		}

		public int getOffset() {
			return offset;
		}
		
		
		
	}
	
	
	//A hashmap containing all vertex attribute
	private VertexAttribute[] attributes;

	public VertexArrayObject() {
		super(glGenVertexArrays());
		
		this.attributes = new VertexAttribute[16];
	}
	
	
	/**
	 * 
	 * Sets a vertex attribute pointer to the given buffer
	 * 
	 * @param buffer The buffer containing the vertex data
	 * @param index The index of the pointer that should be set
	 * @param size The size of the block of data that defines an attribute of one vertex
	 * @param stride The offset in bytes between the blocks
	 * @param offset The staring position of the blocks in the buffer
	 */
	public void setVertexAttributePointer(ArrayBuffer buffer, int index, int size, int stride, int offset) {
		
		this.bind();
		buffer.bind();
		
		//If the buffer contains float data
		if(isFloat(buffer.getDataType())) {
			
			glVertexAttribPointer(index, size, buffer.getDataType(), false, stride, offset);
			this.attributes[index] = new VertexAttribute(buffer, size, stride, offset);
			
		//If the buffer contains integer data
		} else if(isInteger(buffer.getDataType())) {
			
			glVertexAttribIPointer(index, size, buffer.getDataType(), stride, offset);
			this.attributes[index] = new VertexAttribute(buffer, size, stride, offset);
			
		//If the buffer contains long data (double, long)
		} else {
			
			glVertexAttribLPointer(index, size, buffer.getDataType(), stride, offset);
			this.attributes[index] = new VertexAttribute(buffer, size, stride, offset);
			
		}
		
		buffer.unbind();
		this.unbind();
	}
	
	
	/**
	 * 
	 * @param index The position of the requested vertex attribute in the vertex attribute array
	 * @return Returns the vertex attribute or null if the slot isn't used yet.
	 */
	public VertexAttribute getVertexAttribute(int index) {
		return attributes[index];
	}
	
	
	/**
	 * 
	 * Utility function for setVertexAttributePointer
	 * 
	 * @param dataType
	 * @return
	 */
	private boolean isInteger(int dataType) {
		return dataType == GL_INT || dataType == GL_UNSIGNED_INT || dataType == GL_BYTE || dataType == GL_UNSIGNED_BYTE || dataType == GL_SHORT || dataType == GL_UNSIGNED_SHORT;
	}
	
	
	/**
	 * 
	 * Utility function for setVertexAttributePointer
	 * 
	 * @param dataType
	 * @return
	 */
	private boolean isFloat(int dataType) {
		return dataType == GL_FLOAT || dataType == GL_HALF_FLOAT;
	}
	
	
	public void enableVertexAttribArray() {
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i] != null)
				glEnableVertexAttribArray(i);
		}
	}
	
	
	public void disableVertexAttribArray() {
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i] != null)
				glDisableVertexAttribArray(i);
		}
	}
	

	@Override
	public void bind() {
		glBindVertexArray(this.getID());		
	}
	

	@Override
	public void unbind() {
		glBindVertexArray(0);		
	}
	

	@Override
	public void delete() {
		glDeleteVertexArrays(getID());		
	}

}
