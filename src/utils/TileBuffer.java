package utils;

import math.MathUtils;
import math.vectors.Vector3f;
import math.vectors.advanced.Distances;

//TODO: refactor this class!, move algorithms to a searching algorithms class or to a sorting algorithms class

public class TileBuffer {
	
	//The array of vertices
	private Vector3f[] vertices;
	
	private float distanceBetweenVertices;
	
	/*
	 * An array containing the distance of every vertex to the refPoint. It's generated once and 
	 * used for binary search to save computations.
	 */
	private float[] distanceArray;
	
	//This array stores the actual index of every vertex to point to the old location of the vertex after sorting "vertices"
	private int[] oldLocation;
	
	
	//A point in space that lets us sort the vertices by sorting them by their distance to refPoint
	private Vector3f refPoint;
	
	
	private float highestZ;
	
	private float lowestZ;
	
	
	public TileBuffer(Vector3f[] vertices, Vector3f referencePoint) {	
		
		generateDistanceArray(vertices, referencePoint);
		
		quickSort(0, this.vertices.length-1);
		
	}	
	
	
	// **************************** initialization ****************************
	
	
	/*
	 * Generates a distance to the referencePoint for each vertex
	 * Initializes the array that stores the old indices of the vertices
	 */
	private void generateDistanceArray(Vector3f[] vertices, Vector3f referencePoint) {
		
		this.refPoint = referencePoint;
		
		this.vertices = new Vector3f[vertices.length];
				
		this.distanceArray = new float[vertices.length];
		
		this.oldLocation = new int[vertices.length];
		
		this.highestZ = -Float.MAX_VALUE;
		this.lowestZ = Float.MAX_VALUE;
		
				
		distanceBetweenVertices = 0f;
		
		
		for (int i = 0; i < vertices.length; ++i) {
			
			this.vertices[i] = vertices[i].copyOf();
			
			this.distanceArray[i] = vertices[i].minus(referencePoint).norm();			
			
			this.oldLocation[i] = i;
			
			if(i < vertices.length - 1) {
				distanceBetweenVertices += vertices[i].minus(vertices[i+1]).norm();
			}
			
			
			if (vertices[i].getC() < lowestZ) {
				lowestZ = vertices[i].getC();
			}
			
			if (vertices[i].getC() > highestZ) {
				highestZ = vertices[i].getC();
			}
						
		}
		
		System.out.println("Highest point: " + highestZ + " lowest point: " + lowestZ);
		
		distanceBetweenVertices /= vertices.length - 1;
		
	}
	
	
	/*
	 * Sorts the vertices by their distance to the referencePoint
	 * Aplies the same changes to the arrays "oldLocation" and "distanceArray"
	 */
	private void quickSort(int start, int end) {
				
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
		
		
	//A small utility function for the quick sort algorithm:
	private void swap(int a, int b) {
		
		float tempDist = distanceArray[a];
		distanceArray[a] = distanceArray[b];
		distanceArray[b] = tempDist;
		
		Vector3f temp = vertices[a];
		vertices[a] = vertices[b];
		vertices[b] = temp;
		
		int tempIndex = oldLocation[a];
		oldLocation[a] = oldLocation[b];
		oldLocation[b] = tempIndex;
		
	}
	
	
	// **************************** The core of the algorithm ****************************	
	
	
	/**
	 * This algorithm searches the vertex that is the closest to the ray defined by origin and direction
	 * 
	 * @param origin The origin of the ray
	 *  
	 * @param direction The direction of the ray
	 *
	 * @return Returns the index of the closest vertex to the ray
	 * 
	 */
	
	
	public int getTileIndex(Vector3f origin, Vector3f direction) {
		
		float distance;
		int bestCandidateIndex = 0;
		float bestCandidateDistance = Float.MAX_VALUE;
		
		for (int i=0; i<vertices.length; i++) {
			
			distance = Distances.distanceLinePoint(origin, direction, vertices[i]);
			
			if (distance < bestCandidateDistance) {
				bestCandidateDistance = distance;
				bestCandidateIndex = i;
			}
			
		}
		
		return oldLocation[bestCandidateIndex];
		
	}
	
	
	
	/**
	 * 
	 * Searches for a location to start looking for a candidate. The algorithm assumes that two vertices
	 * with similar distance to a reference point are more likely to be located close to each other.
	 * 
	 * @param resquestedDistance The distance we are looking for.
	 * 
	 * @param start The index that defines the lower end of the range of the array we are looking at.
	 * 
	 * @param end The index that defines the upper end of the range of the array we are looking at.
	 * 
	 * @param tolerance Defines the how similar a distance in the array has to count as a candidate
	 * 
	 */
	private int binarySearch(float resquestedDistance, int start, int end, float tolerance) {
		
		int pivotIndex = start + (end - start) / 2;
		float pivotDistance = distanceArray[pivotIndex];
		
		
		if (end - start < 1) {
			return pivotIndex;
		}
		
		
		//If the pivotDistance is already a close enough to the requested one, return the index
		if (MathUtils.getAbsoluteValue(resquestedDistance - pivotDistance) < tolerance) {
			return pivotIndex;
		}
		
		
		if (resquestedDistance < pivotDistance) {
			return binarySearch(resquestedDistance, start, pivotIndex - 1, tolerance);		
		}
		
		return binarySearch(resquestedDistance, pivotIndex + 1, end, tolerance);
		
	}
	
	
	private int expandingSearch(int index, int iterations, Vector3f origin, Vector3f direction) {
		
		int bestCandidateIndex = index;
		float bestDistance = Distances.distanceLinePoint(origin, direction, vertices[index]);
		
		int a = index - 1;
		int b = index + 1;
		
		float temp;
		
		for (int i = 0; i < iterations; ++i) {
			
			if (a >= 0) {
				temp = Distances.distanceLinePoint(origin, direction, vertices[a]);
				
				if (temp < distanceBetweenVertices / 2) {
					return a;
				}
				
			
				if (temp < bestDistance) {
					bestCandidateIndex = a;
					bestDistance = temp;
				}
				
				--a;
			
			}
			
			if (b < vertices.length) {
				temp = Distances.distanceLinePoint(origin, direction, vertices[b]);
				
				if (temp < distanceBetweenVertices / 2) {
					return a;
				}
				
				if (temp < bestDistance) {
					bestCandidateIndex = b;
					bestDistance = temp;
				}
				
				++b;
				
			}
			
		}
		
		return bestCandidateIndex;
		
	}
	
	
	private float computePoint(Vector3f origin, Vector3f direction, float requestedZ) {
		
	//	return (requestedZ - origin.getC()) / direction.getC();
		return (origin.getC() - requestedZ) / Math.abs(direction.getC());
		
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
