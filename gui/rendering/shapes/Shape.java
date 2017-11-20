package rendering.shapes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import assets.meshes.geometry.Color;
import utils.CustomBufferUtils;

public abstract class Shape {
	
	private int numberOfVertices;
	
	private float[] positionData;
	
	private int[] indexArray;
	
	private IntBuffer indexBuffer;
	private int offset = 0;
	
	private Color color;
	
	
	public Shape(float[] positionData, int[] indexArray) {
		
		this.positionData = positionData.clone();
		
		this.indexArray = indexArray.clone();
		
		this.numberOfVertices = positionData.length / 2;
		
	}
	
	
	public Shape() {
		
	}
	
	
	public void setPosData(float[] posData) {
		this.positionData = posData.clone();
	}
	
	
	public void setIndexData(int[] indexData) {
		this.indexArray = indexData.clone();
	}
	
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	//@param scales the position data to match the requested x / y Position and the requested width / height.
	public FloatBuffer getPositionData(float xOffset, float yOffset, float width, float height) {
		FloatBuffer output = BufferUtils.createFloatBuffer(positionData.length);
		
		for (int i = 0; i < positionData.length; ++i) {
			
			if (i % 2 == 0) {
				System.out.println();
			}
			
			float value = i % 2 == 0 ? (positionData[i] * width) + xOffset : (positionData[i] * height) + yOffset;
			output.put(value);
			
			System.out.print(value + " | ");
			
		}
		
		System.out.println();
		
		output.flip();
		
		return output;
	}
	
	
	//Returns the element Array to draw this shape
	public IntBuffer getElementArray(int offset) {
		
		/*
		 * If the position data of this shape is still at the same position on the gpu
		 * use the already generated indexBuffer!
		 */
		if (offset == this.offset && indexBuffer != null) {
			return indexBuffer;
		}
		
		//generate an indexBuffer with the given offset! 
		this.offset = offset;
		
		indexBuffer = BufferUtils.createIntBuffer(indexArray.length);
		
		for (int i = 0; i < indexArray.length; ++i) {
			indexBuffer.put(offset + indexArray[i]);
		}
		
		indexBuffer.flip();
		
		return indexBuffer;
		
	}
	
	
	public FloatBuffer getTexturePositionData() {
		return CustomBufferUtils.createFloatBuffer(positionData);
	}

}
