package assets.buffers;

import assets.GLTargetObject;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL44.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Buffer extends GLTargetObject {
	
	
	/**
	 * 
	 * @author Dario
	 *
	 * Custom exception to handle cases where the user tries to access empty
	 * buffers
	 *
	 */
	private class GLEmptyBufferException extends RuntimeException {}
	
	
	/**
	 * 
	 * @author Dario
	 *
	 * Custom exception to handle cases where the user tries to change
	 * immutable memory
	 *
	 */
	private class GLNoAccessException extends RuntimeException {}
	
	
	/**
	 * The contents of the data store may be updated after creation
	 */
	public static final int DYNAMIC_STORAGE = GL_DYNAMIC_STORAGE_BIT;
	
	
	private int dataType;
	
	private boolean dataStored;
	
	private int flag;


	/**
	 * 
	 * Creates a buffer
	 * 
	 * @param type The type of the buffer (GL_ARRAY_BUFFER
	 */
	public Buffer(int type, int dataType) {
		super(glGenBuffers(), type);
		
		this.dataStored = false;
		this.dataType = dataType;
	}
	
	
	/**
	 * 
	 * Stores data on the gpu
	 * 
	 * @param data The data to be stored
	 * @param flag The way the data will be accessed
	 */
	public void setBufferStorage(FloatBuffer data, int flag) {
		bind();
		
		glBufferStorage(getType(), data, flag);
		this.flag = flag;
		this.dataStored = true;
		
		unbind();
	}
	
	
	/**
	 * 
	 * Loads a block of data stored on the gpu back to the cpu
	 * 
	 * @param offset The position of the data in bytes
	 * @param length The length of the block
	 * @return Returns a FloatBuffer containing the block of data
	 */	
	public FloatBuffer getBufferSubdataAsFloatBuffer(int offset, int length) {
		if (!dataStored) {
			throw new GLEmptyBufferException();
		}
		
		bind();
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(length);
		
		glGetBufferSubData(getType(), offset, buffer);
		
		unbind();
		
		return buffer;
	}
	
	
	/**
	 * 
	 * Loads a block of data stored on the gpu back to the cpu
	 * 
	 * @param offset The position of the data in bytes
	 * @param length The length of the block
	 * @return Returns a FloatBuffer containing the block of data
	 */	
	public IntBuffer getBufferSubdataAsIntBuffer(int offset, int length) {
		if (!dataStored) {
			throw new GLEmptyBufferException();
		}
		
		bind();
		
		IntBuffer buffer = BufferUtils.createIntBuffer(length);
		
		glGetBufferSubData(getType(), offset, buffer);
		
		unbind();
		
		return buffer;
	}
	
	
	/**
	 * 
	 * Replaces a block of data stored in the buffer
	 * 	
	 * @param offset The position of the data in bytes
	 * @param data The data to replace the block with
	 */
	public void setBufferSubdata(int offset, FloatBuffer data) {
		if(!dataStored) {
			throw new GLEmptyBufferException();
		}
		
		if(flag != DYNAMIC_STORAGE) {
			throw new GLNoAccessException();
		}
		
		bind();
		
		glBufferSubData(getType(), offset, data);
		
		unbind();
		
	}
	
	
	/**
	 * 
	 * Replaces a block of data stored in the buffer
	 * 	
	 * @param offset The position of the data in bytes
	 * @param data The data to replace the block with
	 */
	public void setBufferSubdata(int offset, IntBuffer data) {
		if(!dataStored) {
			throw new GLEmptyBufferException();
		}
		
		if(flag != DYNAMIC_STORAGE) {
			throw new GLNoAccessException();
		}
		
		bind();
		
		glBufferSubData(getType(), offset, data);
		
		unbind();
		
	}
	
	
	/**
	 * 
	 * @return Returns the size of the buffer
	 */
	public int getSize() {
		bind();
		
		int size = glGetBufferParameteri(getType(), GL_BUFFER_SIZE);
		
		unbind();
		
		return size;
	}
	
	
	public int getDataType() {
		return dataType;
	}


	@Override
	public void bind() {
		glBindBuffer(getType(), getID());		
	}
	

	@Override
	public void unbind() {
		glBindBuffer(getType(), 0);		
	}
	

	@Override
	public void delete() {
		glDeleteBuffers(getID());		
	}
	
}