package utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import math.vectors.Vector3f;

public class CustomBufferUtils {
	
	/**
	 * creates and flips a FloatBuffer which contains all the elements of array
	 * @param array float array which elements will be put in the buffer
	 * @return the buffer
	 */
	public static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		
		return buffer;
	}
	
	/**
	 * creates and flips a ByteBuffer which contains all the elements of array
	 * @param array byte array which elements will be put in the buffer
	 * @return the buffer
	 */
	public static ByteBuffer createByteBuffer(byte[] array) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		
		return buffer;
	}
	
	
	/**
	 * creates and flips a IntBuffer which contains all the elements of array
	 * @param array int array which elements will be put in the buffer
	 * @return the buffer
	 */
	public static IntBuffer createIntBuffer(int[] array) {
		IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		
		return buffer;
	}
	
	public static IntBuffer createIntBuffer(int[][] array) {
		IntBuffer buffer = BufferUtils.createIntBuffer(array.length * array[0].length);

		for (int i = 0; i < array.length; i++) {
			
			buffer.put(array[i]);
			
		}

		buffer.flip();
		
		return buffer;
		
	}
	
	/**
	 * 
	 * creates and flips a FloatBuffer which contains all the elements of list
	 * @param list ArrayList of Float wrapper
	 * @return the buffer
	 */
	/*public static FloatBuffer createFloatBuffer(ArrayList<Float> list) {
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(list.size());
		for (Float f : list) {
			buffer.put(f);
		}
		buffer.flip();
		
		return buffer;
	}*/

	
	public static FloatBuffer createFloatBuffer(ArrayList<Vector3f> list) {
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(list.size()*3);
		for (Vector3f v : list) {
			buffer.put(v.toArray());
		}
		buffer.flip();
		
		return buffer;
		
	}
	
	public static ByteBuffer createByteBuffer(ArrayList<Byte> list) {
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(list.size());
		for (Byte b : list) {
			buffer.put((byte)b);
		}
		buffer.flip();
		
		return buffer;
		
	}
	
}
