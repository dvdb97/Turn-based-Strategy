package interaction.tileSelection;

import assets.meshes.geometry.Vertex;
import math.vectors.Vector3f;

public class TileSorting {
	
	
	private static Vector3f[] vertices;
	
	private static float[] distanceArray;
	
	
	public static Vector3f[] sort(Vector3f[] inputVertices, Vector3f referencePoint) {
		
		vertices = inputVertices;
		
		generateDistanceArray(referencePoint);
		
		quickSort(0, vertices.length - 1);
		
		return vertices;
		
	}
	
	
	private static void generateDistanceArray(Vector3f referencePoint) {
		
		distanceArray = new float[vertices.length];
		
		
		for (int i = 0; i < vertices.length; ++i) {
			
			distanceArray[i] = vertices[i].minus(referencePoint).norm();			
			
		}
		
	}
	
	
	private static void quickSort(int start, int end) {
				
		//Look up the vertex in the middle of the section and compute its distance to the referencePoint
		float pivotDistance = distanceArray[start + (end - start) / 2];
		
		
		int left = start;
		int right = end;
		
		
		while(left <= right) {
			
			while(distanceArray[left] < pivotDistance) {
				++left;
			}
			
			
			while (distanceArray[right] > pivotDistance) {
				--right;
			}
			
			
			if (left <= right) {
				swap(left, right);
				left++;
				right--;
			}
		}
		
		if (start < right) {
			quickSort(start, right);
		}
		if (left < end) {
			quickSort(left, end);
		}
		
	}
	
	
	private static void swap(int a, int b) {
		
		float tempDist = distanceArray[a];
		distanceArray[a] = distanceArray[b];
		distanceArray[b] = tempDist;
		
		Vector3f temp = vertices[a];
		vertices[a] = vertices[b];
		vertices[b] = temp;
		
	}
	
	
	// **************************** Testing ****************************
	
	
	public static Vector3f[] generate(int quantity) {
		
		Vector3f[] output = new Vector3f[quantity];
		
		
		for (int i = 0; i < output.length; ++i) {
			
			float x = (float)Math.random() * 10.0f;
			float y = (float)Math.random() * 10.0f;
			float z = (float)Math.random() * 10.0f;
			
			
			output[i] = new Vector3f(x, y, z);
			
			
		}
		
		return output;
		
	}
	
	
	public static void print(Vector3f[] vertices, Vector3f referencePoint) {
		
		System.out.println();
		
		for (Vector3f vec : vertices) {
			vec.print();
			
			System.out.println("Distance: " + vec.minus(referencePoint).norm());
			
			System.out.println();
			
		}
		
	}

}
