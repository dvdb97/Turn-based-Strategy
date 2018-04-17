package assets.models;

import assets.GLObject;
import assets.buffers.ArrayBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL41.glVertexAttribLPointer;

import java.util.LinkedList;

public class VertexArrayObject extends GLObject {
	
	
	/**
	 * 
	 * @author Dario
	 * 
	 * An exception to handle the case when the user tries to set
	 * an attribute pointer that is already used 
	 *
	 */
	private class GLDuplicateAttributeException extends RuntimeException {}
	
	
	private LinkedList<Integer> attributes;
	

	public VertexArrayObject() {
		super(glGenVertexArrays());
		
		this.attributes = new LinkedList<Integer>();
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
		
		if(attributes.contains(index)) {
			throw new GLDuplicateAttributeException();
		}
		
		this.bind();
		buffer.bind();
		
		//If the buffer contains float data
		if(isFloat(buffer.getDataType())) {
			
			glVertexAttribPointer(index, size, buffer.getDataType(), false, stride, offset);
			
			//If the buffer contains integer data
		} else if(isInteger(buffer.getDataType())) {
			
			glVertexAttribIPointer(index, size, buffer.getDataType(), stride, offset);
			
			//If the buffer contains long data (double, long)
		} else {
			
			glVertexAttribLPointer(index, size, buffer.getDataType(), stride, offset);
			
		}
		
		buffer.unbind();
		this.unbind();
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
		for(int i : attributes) {
			glEnableVertexAttribArray(i);
		}
	}
	
	
	public void disableVertexAttribArray() {
		for (int i : attributes) {
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
