package assets.meshes;

import assets.GLObject;
import assets.buffers.VertexBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;
import static org.lwjgl.opengl.GL41.glVertexAttribLPointer;

/**
 * 
 * @author Dario
 * 
 * One vertex attribute of this vertex attribute object. This
 * vertex attribute stores a reference to the buffer containing
 * the data and information about the layout of the data
 *
 */
class VertexAttribute {
	
	public VertexBuffer buffer;
	
	public int size, stride, offset, divisor;

	public VertexAttribute(VertexBuffer buffer, int size, int stride, int offset, int divisor) {
		this.buffer = buffer;
		this.size = size;
		this.stride = stride;
		this.offset = offset;
		this.divisor = divisor;
	}	
}


public class VertexArrayObject extends GLObject {
	
	//An array containing all vertex attribute
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
	 * @param offset The starting position of the blocks in the buffer
	 */
	public void setVertexAttributePointer(VertexBuffer buffer, int index, int size, int stride, int offset, int divisor) {
		
		this.bind();
		buffer.bind();
		glEnableVertexAttribArray(index);
		
		//If the buffer contains float data
		if(isFloat(buffer.getDataType())) {
			
			glVertexAttribPointer(index, size, buffer.getDataType(), false, stride, offset);
			this.attributes[index] = new VertexAttribute(buffer, size, stride, offset, divisor);
			
		//If the buffer contains integer data
		} else if(isInteger(buffer.getDataType())) {
			
			glVertexAttribIPointer(index, size, buffer.getDataType(), stride, offset);
			this.attributes[index] = new VertexAttribute(buffer, size, stride, offset, divisor);
			
		//If the buffer contains long data (double, long)
		} else {
			
			glVertexAttribLPointer(index, size, buffer.getDataType(), stride, offset);
			this.attributes[index] = new VertexAttribute(buffer, size, stride, offset, divisor);
			
		}
		
		glVertexAttribDivisor(index, divisor);
		glDisableVertexAttribArray(index);
		
		buffer.unbind();
		this.unbind();
	}
	
	
	public void setVertexAttributePointer(VertexBuffer buffer, int index, int size, int stride, int offset) {
		this.setVertexAttributePointer(buffer, index, size, stride, offset, 0);
	}
	
	
	public void setVertexAttributePointer(VertexAttribute attribute, int index) {
		this.setVertexAttributePointer(attribute.buffer, index, attribute.size, attribute.stride, attribute.offset, attribute.divisor);
	}
	
	
	public VertexAttribute[] getVertexAttributes() {
		return attributes;
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
		this.bind();		
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
		this.unbind();
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
		for (VertexAttribute va : attributes) {
			if (va != null)
				va.buffer.delete();
		}
		
		glDeleteVertexArrays(getID());		
	}

}
