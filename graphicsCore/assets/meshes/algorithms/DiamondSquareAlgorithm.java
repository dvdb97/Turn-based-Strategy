package assets.meshes.algorithms;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.material.Material;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Illuminated_Model;
import lwlal.Vector3f;



public class DiamondSquareAlgorithm {
	
	
	private static Vertex[] vertices;
	
	private static ArrayList<Integer> elementArray;
	
	private static int dimension;
	
	private static boolean debug = false;
	
	
	
	public static void generate(int iterations, float totalWidth, float totalHeight, float maxDepth) {
		
		int numberOfVertices = generateNumberOfVertices(iterations, 2);
		
		dimension = (int)Math.sqrt(numberOfVertices);
		
		vertices = new Vertex[numberOfVertices];
		
		elementArray = new ArrayList<Integer>();
		
		generateMesh(totalWidth, totalHeight, maxDepth);
		
		diamondSquare(iterations, 0, dimension - 1, (dimension - 1) * dimension, dimension * dimension - 1);
		
	}
	
	
	public static Illuminated_Model generateModel(int iterations, float totalWidth, float totalHeight, float maxDepth, Material material) {
		
		generate(iterations, totalWidth, totalHeight, maxDepth);
		
		return toModel(material);		
		
	}
	
	
	public static Vertex[] generateVertexArray(int iterations, float totalWidth, float totalHeight, float maxDepth) {
		
		generate(iterations, totalWidth, totalHeight, maxDepth);
		
		return vertices;
		
	}
	
	
	//Generate the vertex data for the mesh
	private static void generateMesh(float width, float height, float depth) {
		
		int dimension = (int)Math.sqrt(vertices.length);
		
		float rectWidth = width / dimension;
		
		float rectHeight = height / dimension;
		
		float vertX;
		
		float vertY;
		
		float vertZ;
		
		Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);

		
		//Generate all the vertices needed for the algorithm and initialize the corner values
		for (int y = 0; y < dimension; ++y) {
			
			for (int x = 0; x < dimension; ++x) {
				
				vertX = x * rectWidth;
				
				vertY = y * rectHeight;
				
				vertZ = 0.0f;
				
				//If the current vertex is one of the vertices at the corner of the mesh...
				if ((y == 0 && (x == 0 || x == dimension - 1)) || (x == 0 && (y == 0 || y == dimension - 1))) {
					
					//...generate a random z value:
					vertZ = (float)Math.random();
					
				}
				
				vertices[y * dimension + x] = new Vertex(vertX, vertY, vertZ, color);
				
			}
			
		}
		
	}
	
	
	
	/*
	 * An algorithm that generates the number of vertices needed for the mesh
	 * l has to be set to 2!
	 */
	private static int generateNumberOfVertices(int iterations, int l) {
		
		if (iterations == 0) {
			return l * l;
		}
		
		//3x3, 5x5, 9x9, 17x17, etc...
		l = 2 * l - 1;
		
		return generateNumberOfVertices(iterations - 1, l);
		
	}
	
	
	//************************************* Core functions *************************************
	
	
	//The core algorithm:
	private static void diamondSquare(int iterations, int upperLeft, int upperRight, int lowerLeft, int lowerRight) {
		
		if (iterations == 0) {
			//TODO: Compute the normals etc.
			generateElementArray(upperLeft, upperRight, lowerLeft, lowerRight);
			
			//Compute textures
			
			//Compute color
			
			//etc
			
			return;
			
		}
		
		//Diamond step:
		int center = diamondStep(upperLeft, upperRight, lowerLeft, lowerRight);
		
		
		//Square step:
		int midLeft = squareSubStep(lowerLeft, upperLeft);
		int midRight = squareSubStep(lowerRight, upperRight);
		int topMid = squareSubStep(upperLeft, upperRight);
		int bottomMid = squareSubStep(lowerLeft, lowerRight);
		
		
		//Recursion:
		diamondSquare(iterations - 1, upperLeft, topMid, midLeft, center);
		diamondSquare(iterations - 1, midLeft, center, lowerLeft, bottomMid);
		diamondSquare(iterations - 1, center, midRight, bottomMid, lowerRight);
		diamondSquare(iterations - 1, topMid, upperRight, center, midRight);
		
	}
	
	
	
	private static int diamondStep(int v0, int v1, int v2, int v3) {
		
		int diamondIndex = (v0 + v1 + v2 + v3) / 4;
		
		float meanDepth = getMeanDepth(v0, v1, v2, v3);
		float standardDeviation = getStandardDeviationOfDepth(v0, v1, v2, v3);
		
		//values ranging from -standardDeviation to +standardDeviation
		float displacementValue = 2 * (float)Math.random() * standardDeviation - standardDeviation;
		
		vertices[diamondIndex].setC(meanDepth + displacementValue);
		
		return diamondIndex;
		
	}
	
	
	
	private static int squareSubStep(int v0, int v1) {
		
		int index = (v0 + v1) / 2;
		
		float meanDepth = (vertices[v0].getC() + vertices[v1].getC()) / 2;
		float variance = (square(vertices[v0].getC() - meanDepth) + square(vertices[v1].getC() - meanDepth)) / 2;
		float standardDeviation = (float)Math.sqrt(variance);
		
		//values ranging from -standardDeviation to +standardDeviation
		float displacementValue = 2 * (float)Math.random() * standardDeviation - standardDeviation;
		
		vertices[index].setA(meanDepth + displacementValue);
		
		return index;
		
	}
	
	
	
	private static void generateElementArray(int upperLeft, int upperRight, int lowerLeft, int lowerRight) {
		
		//Add indices to the element array
		elementArray.add(upperLeft);
		elementArray.add(upperRight);
		elementArray.add(lowerLeft);
		
		//Compute normals
		Vector3f vec0 = vertices[upperLeft].minus(vertices[lowerLeft]);
		Vector3f vec1 = vertices[upperLeft].minus(vertices[upperRight]);
		Vector3f normal = vec0.cross(vec1);
		
		//Set the normals
		vertices[upperLeft].addSurfaceNormal(normal);
		vertices[upperRight].addSurfaceNormal(normal);
		vertices[lowerLeft].addSurfaceNormal(normal);
		
		//Add indices to the element array
		elementArray.add(upperRight);
		elementArray.add(lowerLeft);
		elementArray.add(lowerRight);
		
		//Compute normals
		vec0 = vertices[upperRight].minus(vertices[lowerLeft]);
		vec1 = vertices[upperRight].minus(vertices[lowerRight]);
		normal = vec0.cross(vec1);
		
		//Set the normals
		vertices[upperLeft].addSurfaceNormal(normal);
		vertices[upperRight].addSurfaceNormal(normal);
		vertices[lowerRight].addSurfaceNormal(normal);
		
	}
	
	
	private static Illuminated_Model toModel(Material material) {
		
		Illuminated_Model model = new Illuminated_Model(GL_TRIANGLES, material);
		
		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(vertices.length * 3);
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(vertices.length * 3);
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.size());
		
		
		for (Vertex vertex : vertices) {
			
			posBuffer.put(vertex.getPositionData());
			normalBuffer.put(vertex.getNormal().toArray());
			
		}
		
		
		for (int element : elementArray) {
			
			elementBuffer.put(element);
			
		}
		
		
		posBuffer.flip();
		normalBuffer.flip();
		elementBuffer.flip();
		
		
		model.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		model.setVertexNormalData(normalBuffer, GL_STATIC_DRAW);
		model.setElementArrayData(elementBuffer);
		
		
		return model;		
		
	}
	
	
	//************************************* Utility functions *************************************
	
	
	private static float getMeanDepth(int v0, int v1, int v2, int v3) {
		return (vertices[v0].getC() + vertices[v1].getC() + vertices[v2].getC() + vertices[v3].getC()) / 4;
	}
	
	
	
	private static float getStandardDeviationOfDepth(int v0, int v1, int v2, int v3) {
		
		float mean = getMeanDepth(v0, v1, v2, v3);
		
		float sumOfSquares = square(v0 - mean) + square(v1 - mean) + square(v2 - mean) + square(v3 - mean);
		
		float variance = sumOfSquares / 4;
		
		return (float)Math.sqrt(variance);
		
	}
	
	
	
	private static float square(float value) {
		return value * value;
	}

}
