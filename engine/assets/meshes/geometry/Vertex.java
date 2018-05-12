package assets.meshes.geometry;

import java.nio.FloatBuffer;

import math.vectors.Vector2f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

public class Vertex {
			
	private float[] position = null;
	
	private float[] color = null;
	
	private float[] texPos = null;
	
	private float[] normal = null;
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 */
	public Vertex(Vector3f position) {
		this.position = position.toArray();
	}
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 */
	public Vertex(Vector2f position) {
		this.position = position.toArray();
	}
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 */
	public Vertex(float[] position) {
		this.position = position.clone();
	}
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 * @param color The vertex color
	 */
	public Vertex(Vector3f position, Vector4f color) {
		this(position);
		this.color = color.toArray();		
	}
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 * @param color The vertex color
	 */
	public Vertex(Vector2f position, Vector4f color) {
		this(position);
		this.color = color.toArray();
	}
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 * @param color The vertex color
	 */
	public Vertex(float[] position, float[] color) {
		this(position);
		this.color = color.clone();
	}
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 * @param texPos The vertex texture coords
	 */
	public Vertex(Vector3f position, Vector3f texPos) {
		this(position);
		this.texPos = texPos.toArray();
	}
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 * @param color The vertex color
	 * @param normal The vertex normal
	 */
	public Vertex(Vector3f position, Vector4f color, Vector3f normal) {
		this(position, color);
		this.normal = normal.toArray();
	}
	
	
	/**
	 * 
	 * Creates a new Vertex
	 * 
	 * @param position The vertex position
	 * @param texPos The vertex texture coords
	 * @param normal The vertex normal
	 */
	public Vertex(Vector3f position, Vector3f texPos, Vector3f normal) {
		this(position, texPos);
		this.normal = normal.toArray();
	}
	
	
	/**
	 * 
	 * Encodes the size of every vertex attribute as 4 bits
	 * of an integer
	 * 
	 * @return Returns an integer that stores the information of the attribute sizes
	 */
	public int getDataLayout() {
		int layout = 0;
		int shift = 0;
		
		//WTF?! Black magic!
		layout |= ((position != null ? position.length : 0) << (shift++ * 4));
		
		layout |= ((color != null ? color.length : 0) << (shift++ * 4));
		
		layout |= ((texPos != null ? texPos.length : 0) << (shift++ * 4));
		
		layout |= ((normal != null ? normal.length : 0) << (shift++ * 4));
		
		return layout;
		
	}
	
	
	/**
	 * 
	 * Puts all the data that defines this vertex into one array
	 * 
	 * @return Returns an array containing all the data stored in this data structure
	 */
	public float[] toDataBundle() {
		
		float[][] attributes = {position, color, texPos, normal};
		
		//The size of the array that is needed for this vertex
		int dataSize = 0;
		
		//Compute the size of the array is needed for this vertex
		for (float[] array : attributes) {
			if (array != null) {
				dataSize += array.length;
			}
		}
		
		
		//The array to store the vertex data in
		float[] data = new float[dataSize];
		
		
		int arrayIndex = 0;
		
		for (float[] attribute : attributes) {
			
			if (attribute == null) {
				continue;
			}
			
			for (float value : attribute) {
				
				data[arrayIndex++] = value; 
				
			}
			
		}
		
		return data;
		
	}
	
	
	public float[] toDataBundle(int layout) {
		
		//The sizes of the different attributes in this layout
		byte[] blockSizes = getSizes(layout);
		
		//The array that will be returned as the result
		float[] data = new float[getSum(blockSizes)];
		
		//An array with all the attribute values of this vertex
		float[][] attributes = {position, color, texPos, normal};
		
		int arrayIndex = 0;
		
		for (int i = 0; i < attributes.length; ++i) {
			
			for (int j = 0; j < blockSizes[i]; ++j) {
				
				if (attributes[i] == null) {
					continue;
				}
				
				data[arrayIndex++] = j < attributes[i].length ? attributes[i][j] : 0f; 
				
			}
			
		}
		
		return data;
		
	}
	
	
	/**
	 * 
	 * Utility method
	 * 
	 * @param array
	 * @return
	 */
	private int getSum(byte[] array) {
		
		int sum = 0;
		
		for (byte b : array) {
			sum += b;
		}
		
		return sum;
	}
	
	
	/**
	 * 
	 * Puts all the vertex data into the given FloatBuffer
	 * 
	 * @param buffer A reference to a FloatBuffer
	 */
	public void toDataBundle(FloatBuffer buffer) {
		
		buffer.put(position);
		buffer.put(color);
		buffer.put(texPos);
		buffer.put(normal);
		
	}
	
	
	/**
	 * 
	 * Puts all the vertex data into the given FloatBuffer.
	 * The data will be stored as specified in layout
	 * 
	 * @param buffer A reference to a FloatBuffer
	 * @param layout The layout
	 */
	public void toDataBundle(FloatBuffer buffer, int layout) {
		
		//The sizes of the different attributes in this layout
		byte[] blockSizes = getSizes(layout);
		
		//An array with all the attribute values of this vertex
		float[][] attributes = {position, color, texPos, normal};
		
		for (int i = 0; i < attributes.length; ++i) {
			
			for (int j = 0; j < blockSizes[i]; ++j) {
				
				if (attributes[i] == null) {
					continue;
				}
				
				buffer.put(j < attributes[i].length ? attributes[i][j] : 0f); 
				
			}
			
		}
		
	}
	
	
	//*************************************** Getters and setters ***************************************
	
	
	public void putPositionData(FloatBuffer buffer) {
		buffer.put(position);
	}
	
	
	public float[] getPositionData() {
		return position;
	}
	
	
	public void putColorData(FloatBuffer buffer) {
		buffer.put(color);
	}
	
	
	public float[] getColorData() {
		return color;
	}
	
	
	public void putNormalData(FloatBuffer buffer) {
		buffer.put(normal);
	}
	
	
	public float[] getNormalData() {
		return normal;
	}
	
	
	public void putTexPosData(FloatBuffer buffer) {
		buffer.put(texPos);
	}
	
	
	public float[] getTexPosData() {
		return texPos;
	}
	
	
	public int getPositionVectorSize() {
		return position.length;
	}
	
	
	public float getXPos() {
		
		if (position != null) {
			return 0f;
		}
		
		return position[0];
		
	}
	
	
	public float getYPos() {
		
		if (position != null) {
			return 0f;
		}
		
		if (position.length < 2) {
			return 0f;
		}
		
		return position[1];
		
	}
	
	
	public float getZPos() {
		
		if (position != null) {
			return 0f;
		}
		
		if (position.length < 3) {
			return 0f;
		}
		
		return position[2];
		
	}
	
	
	/**
	 * 
	 * @return Returns the size of all attribute data stored in this vertex
	 */
	public int getDataSize() {
		
		int size = 0;
		
		if (position != null) {
			size += position.length;
		}
		
		if (color != null) {
			size += color.length;
		}
		
		if (texPos != null) {
			size += texPos.length;
		}
		
		if (normal != null) {
			size += normal.length;
		}
		
		return size;
		
	}
	
	
	//*************************************** Static methods ***************************************
	
	
	public static byte[] getSizes(int layout) {
		
		int mask = 15;
		byte[] blockSizes = new byte[8];
		
		for (int i = 0; i < blockSizes.length; i++) {
			blockSizes[i] = (byte) ((layout >> (i * 4)) & mask); 
		}
		
		return blockSizes;
		
	}
	
	
	public static int generateLayout(int posSize, int colorSize, int texPosSize, int normalSize) {
		
		int layout = 0;
		int shift = 0;
		
		//WTF?! Black magic!
		layout |= posSize << (shift++ * 4);
		
		layout |= colorSize << (shift++ * 4);
		
		layout |= texPosSize << (shift++ * 4);
		
		layout |= normalSize << (shift++ * 4);
		
		return layout;
		
		
	}
	
}
