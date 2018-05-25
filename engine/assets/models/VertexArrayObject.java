package assets.models;

import assets.GLObject;
import assets.buffers.VertexBuffer;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.geometry.Vertex;
import assets.buffers.Buffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL41.glVertexAttribLPointer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
	
	
	//An array containing all vertex attribute
	private VertexAttribute[] attributes;
	
	
	private BufferLayout layout;
	

	public VertexArrayObject(BufferLayout layout) {
		super(glGenVertexArrays());
		
		this.attributes = new VertexAttribute[16];
		
		this.layout = layout;
	}
	
	
	/**
	 * 
	 * Sets pointers to the vertex data. The algorithm assumes that the vertex data is interleaved and
	 * sets pointers according to the layout
	 * 
	 * @param buffer The buffer in which the data is stored
	 * @param layout The layout of the data in the buffer
	 */
	public void setVertexAttributePointers(VertexBuffer buffer, int layout) {
		
		if (buffer == null) {
			System.err.println("The buffer hasn't been initialized yet!");
			
			return;
		}
		
		
		this.bind();
		buffer.bind();
		
		byte[] sizes = Vertex.getSizes(layout);
		
		int stride = 0;
		
		for (byte b : sizes) {
			stride += b * Float.BYTES;
		}
		
		int offset = 0;
		
		for (int i = 0; i < sizes.length; ++i) {
		
			offset += sizes[i] * Float.BYTES;
			
			if (sizes[i] == 0) {
				continue;
			}
			
			glVertexAttribPointer(i, sizes[i], buffer.getDataType(), false, stride, offset);
			this.attributes[i] = new VertexAttribute(buffer, sizes[i], stride, offset);
			
		}
		
		buffer.unbind();
		this.unbind();
		
	}
	
	
	/**
	 * 
	 * Sets pointers to the vertex data. The algorithm assumes that the vertex data is stored in multiple buffers and
	 * sets pointers according to the layout
	 * 
	 * @param buffers The buffers the pointer should point to
	 * @param layout The layout of the vertices stored in the buffers
	 */
	public void setVertexAttributePointers(VertexBuffer[] buffers, int layout) {
		
		if (buffers == null) {
			System.err.println("The buffer array hasn't been initialized yet!");
			
			return;
		}
		
		
		if (buffers.length > attributes.length) {
			System.err.println("There are more buffers than pointers that can be set!");
			
			return;
		}
		
		
		byte[] sizes = Vertex.getSizes(layout);
		
		
		for (int i = 0; i < buffers.length; ++i) {
			
			if (sizes[i] == 0) {
				continue;
			}
			
			setVertexAttributePointer(buffers[i], i, sizes[i], 0, 0);
		}
		
	}
	
	
	/**
	 * 
	 * Sets pointers to the vertex data. The algorithm assumes that the vertex data is stored in multiple buffers and
	 * sets pointers according to the layout
	 * 
	 * @param buffers The buffers the pointer should point to
	 * @param layout The layout of the vertices stored in the buffers
	 */
	public void setVertexAttributePointers(List<VertexBuffer> buffers, int layout) {
	
		if (buffers == null) {
			System.err.println("The buffer array hasn't been initialized yet!");
			
			return;
		}
		
		
		if (buffers.size() > attributes.length) {
			System.err.println("There are more buffers than pointers that can be set!");
			
			return;
		}
		
		
		byte[] sizes = Vertex.getSizes(layout);
		
		
		for (int i = 0; i < buffers.size(); ++i) {
			
			if (sizes[i] == 0) {
				continue;
			}
			
			setVertexAttributePointer(buffers.get(i), i, sizes[i], 0, 0);
		}
		
	}
	
	
	/**
	 * 
	 * Sets pointers to the vertex data. The algorithm assumes that the vertex data is stored blockwise in one buffer and
	 * sets pointers according to the layout
	 * 
	 * @param buffer
	 * @param numVertices
	 * @param layout
	 */
	public void setVertexAttributePointers(VertexBuffer buffer, int numVertices, int layout) {
		
		if (buffer == null) {
			System.err.println("The buffer hasn't been initialized yet!");
			
			return;
		}
		
		
		byte[] sizes = Vertex.getSizes(layout);
		
		
		int offset = 0;
		
		for (int i = 0; i < sizes.length; ++i) {
			
			if (sizes[i] == 0) {
				continue;
			}
			
			setVertexAttributePointer(buffer, i, sizes[i], 0, offset);
			
			offset += sizes[i] * numVertices;
			
		}
		
	}
	
	
	/**
	 * 
	 * Sets a vertex attribute pointer to the given buffer
	 * 
	 * @param buffer The buffer containing the vertex data
	 * @param index The index of the pointer that should be set
	 * @param size The size of the block of data that defines an attribute of one vertex
	 * @param stride The offset in bytes between the blocks
	 * @param offset The starting position of the blocks in the buffer
	 */
	public void setVertexAttributePointer(VertexBuffer buffer, int index, int size, int stride, int offset) {
		
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
	
	
	/**
	 * 
	 * @return Returns the total size of the data stored for each vertex
	 */
	public int getVertexSize() {
		
		int totalSize = 0;
		
		for (VertexAttribute attribute : attributes) {
			if (attribute == null) {
				continue;
			}
			
			totalSize += attribute.size;
		}
		
		return totalSize;
		
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
