package assets.meshes.geometry;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class GeometryBuffer {
	
	
	//The data stored in this buffer
	private Vertex[] vertices;
	
	//Points to the next open slot to put a new Vertex in
	private int currentIndex = 0;
	
	
	//Standard capacity is 100 vertices
	public GeometryBuffer() {
		this(100);
	}
	
	
	public GeometryBuffer(int predictedNumberOfVertices) {
		vertices = new Vertex[predictedNumberOfVertices];
	}
	
	
	//Add a new Vertex to the array
	public void put(Vertex vertex) {
		if (currentIndex + 1 > getCapacity()) {
			resize();
		}
		
		vertices[currentIndex++] = vertex;
	}
	
	
	//Add an array of Vertices into this buffer
	public void put(Vertex[] in_vertices) {
		
		//If there isn't enough space in this buffer for the array then resize the buffer
		if (currentIndex + in_vertices.length > getCapacity()) {
			resize();
		}
		
		//Put the data into this buffer
		for (int i = 0; i < in_vertices.length; ++i) {
			
			this.vertices[currentIndex + i] = in_vertices[i];
			
		}
		
		currentIndex += in_vertices.length;
	}
	
	
	//Add a buffer into this buffer 
	public void put(GeometryBuffer geoBuffer) {
		put(geoBuffer.getVertices());
	}
	
	
	//Increase the capacity of this buffer. The buffer will be resized by factor 10.
	public void resize() {
		Vertex[] temp = new Vertex[vertices.length * 10];
		
		for (int i = 0; i < vertices.length; ++i) {
			temp[i] = vertices[i];
		}
		
		vertices = temp;
	}
	
	
	//Returns the actual data stored in this buffer
	public int getNumberOfVertices() {
		int numberOfVertices = 0;
		for (Vertex vertex : vertices) {
			if (vertex == null) {
				return numberOfVertices;
			}
			
			numberOfVertices++;
		}
		return numberOfVertices;
	}
	
	
	//Get the array stored in this buffer
	public Vertex[] getVertices() {
		return vertices;
	}
	
	
	//Returns all position data as a single array
	public float[] getData() {
		float[] output = new float[vertices.length * 3];
		
		int index = 0;
		for (int i = 0; i < vertices.length; ++i) {
			if (vertices[i] == null) {
				return output;
			}
			
			output[index++] = vertices[i].getA();
			output[index++] = vertices[i].getB();
			output[index++] = vertices[i].getC();
		}
		
		return output;
	}
	
	
	public float[] getColData() {
		float[] output = new float[vertices.length * 4];
		
		int index = 0;
		for (int i = 0; i < vertices.length; ++i) {
			if (vertices[i] == null) {
				return output;
			}
			
			output[index++] = vertices[i].getRed();
			output[index++] = vertices[i].getGreen();
			output[index++] = vertices[i].getBlue();
			output[index++] = vertices[i].getAlpha();
		}
		
		return output;
	}
	
	
	//Probably temporary for debugging:
	public void printData() {
		for (Vertex vertex : vertices) {
			if (vertex != null) {
				vertex.print();
			} else {
				break;
			}
		}
	}
	
	
	public void printColorData() {
		for (Vertex vertex : vertices) {
			if (vertex != null) {
				vertex.printColor();
			} else {
				break;
			}
		}
	}
	
	
	public FloatBuffer posDataAsFloatBuffer() {
		FloatBuffer output = BufferUtils.createFloatBuffer(vertices.length * 3);
		
		output.put(getData());
		output.flip();
		
		return output;
	}
	
	
	public FloatBuffer colDataAsFloatBuffer() {
		FloatBuffer output = BufferUtils.createFloatBuffer(vertices.length * 4);
		
		output.put(getColData());
		output.flip();
		
		return output;
	}
	
	
	//Returns the total current capacity of this buffer
	public int getCapacity() {
		return vertices.length;
	}
	
	
	public Vertex get(int number) {
		return vertices[number];
	}

}
