package assets.buffers;

import assets.GLTargetObject;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL44.*;

import java.nio.ByteBuffer;
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
	 * 
	 * @author admin
	 *
	 * Custom exception to handle the unsuccessful attempts to map a buffer.
	 *
	 */
	private class GLBufferMappingFailedException extends RuntimeException {}
	
	
	/**
	 * The contents of the data store may be updated after creation
	 */
	public static final int DYNAMIC_STORAGE = GL_DYNAMIC_STORAGE_BIT;
	
	public static final int MAP_READ_ONLY = GL_READ_ONLY;
	public static final int MAP_WRITE_ONLY = GL_WRITE_ONLY;
	public static final int MAP_READ_WRITE = GL_READ_WRITE;
	
	public static final int USAGE_DYNAMIC_READ = GL_DYNAMIC_READ;
	public static final int USAGE_DYNAMIC_DRAW = GL_DYNAMIC_DRAW;	
	public static final int USAGE_DYNAMIC_COPY = GL_DYNAMIC_COPY;
	
	public static final int USAGE_STREAM_READ = GL_STREAM_READ;
	public static final int USAGE_STREAM_DRAW = GL_STREAM_DRAW;	
	public static final int USAGE_STREAM_COPY = GL_STREAM_COPY;

	
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
	
	
	public void unmapBuffer() {
		bind();
		
		if (!glUnmapBuffer(getType())) {
			throw new GLBufferMappingFailedException();
		}
		
		unbind();
	}
	
	
	public ByteBuffer mapBuffer(int access) {
		bind();
		
		ByteBuffer buffer = glMapBuffer(getType(), access);
		
		unbind();
		
		return buffer;
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
	
	
	public void setBufferData(long size, int usage) {
		bind();
		glBufferData(getType(), size, usage);
		unbind();
	}
	
	
	/**
	 * 
	 * Copies this buffer's data stored on the gpu.
	 * 
	 * @param target The target buffer to put the copied data in.
	 */
	public void copy(Buffer target) {	
		this.bind(GL_COPY_READ_BUFFER);
		target.bind(GL_COPY_WRITE_BUFFER);
		
		glCopyBufferSubData(GL_COPY_READ_BUFFER, GL_COPY_WRITE_BUFFER, 0, 0, getSize());
		
		target.unbind(GL_COPY_WRITE_BUFFER);
		this.unbind(GL_COPY_READ_BUFFER);		
	}
	
	
	/**
	 * 
	 * @return Returns a deep copy of this buffer
	 */
	public Buffer copy() {
		Buffer buffer = new Buffer(getType(), getDataType());
		buffer.setBufferStorage(null, this.flag);
		
		copy(buffer);
		
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
	
	
	//TODO: Changing data with mapping
	
	
	/**
	 * 
	 * @return Returns the size of the buffer measured in bytes
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
	
	
	public void bind(int target) {
		glBindBuffer(target, getID());
	}
	
	
	public void unbind(int target) {
		glBindBuffer(target, 0);
	}
	

	@Override
	public void delete() {
		glDeleteBuffers(getID());		
	}
	
}
