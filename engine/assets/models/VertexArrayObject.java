package assets.models;

import assets.GLObject;
import assets.buffers.ArrayBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL41.glVertexAttribLPointer;

import java.util.LinkedList;

public class VertexArrayObject extends GLObject {
	
	private LinkedList<Integer> attributes;
	

	public VertexArrayObject() {
		super(glGenVertexArrays());
		
		this.attributes = new LinkedList<Integer>();
	}
	
	
	public void setVertexAttributePointer(ArrayBuffer buffer, int index, int size, int byteOffsetBetweenValues, int byteOffset) {
		this.bind();
		buffer.bind();
		
		//If the buffer contains float data
		if(buffer.getDataType() == GL_FLOAT) {
			
			glVertexAttribPointer(index, size, buffer.getDataType(), false, byteOffsetBetweenValues, byteOffset);
			
			//If the buffer contains integer data
		} else if(isInteger(buffer.getDataType())) {
			
			glVertexAttribIPointer(index, size, buffer.getDataType(), byteOffsetBetweenValues, byteOffset);
			
			//If the buffer contains long data (double, long)
		} else {
			
			glVertexAttribLPointer(index, size, buffer.getDataType(), byteOffsetBetweenValues, byteOffset);
			
		}
		
		buffer.unbind();
		this.unbind();
	}
	
	
	private boolean isInteger(int dataType) {
		return dataType == GL_INT || dataType == GL_UNSIGNED_INT || dataType == GL_BYTE || dataType == GL_UNSIGNED_BYTE || dataType == GL_SHORT || dataType == GL_UNSIGNED_SHORT;
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
