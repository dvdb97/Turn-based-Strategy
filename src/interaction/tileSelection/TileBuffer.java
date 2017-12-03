package interaction.tileSelection;

import math.MathUtils;
import math.vectors.Vector3f;

public class TileBuffer {
	
	
	private static Vector3f[] vertices;
	
	private static float[] distanceArray;
	
	
	private static Vector3f refPoint;
	
	
	//The highest vertex in the array.
	private static float highestPoint;
	
	//The lowest vertex in the array.
	private static float lowestPoint;
	
	
	public static void init(Vector3f[] inputVertices, Vector3f referencePoint) {
		
		vertices = inputVertices;
		
		refPoint = referencePoint;
		
		generateDistanceArray(refPoint);
		
		quickSort(0, vertices.length - 1);
		
	}
	
	
	public static int getTileIndex(Vector3f rayPosition, Vector3f rayDirection, float tolerance, int steps, int expandingSearchIterations) {
		
		//https://www.youtube.com/watch?v=_r_-kADe2yU
		
		int bestCandidateIndex = 0;
		float bestDistance = Float.MAX_VALUE;
		
		//TODO: Compute where the ray enters and leaves the zone defined by min = (x, y, lowestPoint) and max = (x, y, highestPoint)
		
		int currentIndex = 0;
		float requestedDistance;
		float candidateDistance;
		
		
		for (int i = 0; i < steps; ++i) {
			
			//Compute the distance of the current version of the raySource to the reference point
			requestedDistance = MathUtils.getAbsoluteValue(rayPosition.minus(refPoint).norm());
			
			//Search for points with a similar distance to the reference point
			currentIndex = binarySearch(requestedDistance, 0, distanceArray.length, tolerance);
			
			
			//Among those points, look for the point that has to closest distance to the raySource
			currentIndex = expandingSearch(currentIndex, expandingSearchIterations, rayPosition);
			
			
			//Evaluate the distance of this step's candidate and check if it is actually better than the best candidate
			candidateDistance = MathUtils.getAbsoluteValue(rayPosition.minus(vertices[currentIndex]).norm());
			
			if (candidateDistance < bestDistance) {
				bestDistance = candidateDistance;
				bestCandidateIndex = currentIndex;
			}
			
			
			//TODO: Increase the rayPosition						
			
		}
		
		
		return bestCandidateIndex;
		
	}
	
	
	//Generates a distance to the referencePoint for each vertex
	private static void generateDistanceArray(Vector3f referencePoint) {
		
		lowestPoint = 100.0f;
		highestPoint = -100.0f;
		
		distanceArray = new float[vertices.length];
		
		
		for (int i = 0; i < vertices.length; ++i) {
			
			if (vertices[i].getC() < lowestPoint) {
				lowestPoint = vertices[i].getC();
			}
			
			if (vertices[i].getC() > highestPoint) {
				highestPoint = vertices[i].getC();
			}
			
			
			distanceArray[i] = vertices[i].minus(referencePoint).norm();			
			
		}
		
	}
	
	
	//Sorts the vertices by their distance to the referencePoint
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
	
	
	/*
	 * resquestedDistance: The distance we are looking for.
	 * 
	 * start: The index that defines the lower end of the range of the array we are looking at.
	 * 
	 * end: The index that defines the upper end of the range of the array we are looking at.
	 * 
	 * tolerance: Defines the how similar a distance in the array has to count as a candidate
	 * 
	 */
	private static int binarySearch(float resquestedDistance, int start, int end, float tolerance) {
		
		int pivotIndex = start + (end - start) / 2;
		float pivotDistance = distanceArray[pivotIndex];
		
		
		if (start == end) {
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
	
	
	private static int expandingSearch(int index, int iterations, Vector3f raySource) {
		
		int bestCandidateIndex = index;
		float bestDistance = MathUtils.getAbsoluteValue(vertices[index].minus(raySource).norm());
		
		int a = index - 1;
		int b = index + 1;
		
		float temp;
		
		for (int i = 0; i < iterations; ++i) {
			
			if (a >= 0) {
				temp = MathUtils.getAbsoluteValue(vertices[a].minus(raySource).norm());
			
				if (temp < bestDistance) {
					bestCandidateIndex = a;
					bestDistance = temp;
				}
			
			}
			
			if (b < vertices.length) {
				temp = MathUtils.getAbsoluteValue(vertices[b].minus(raySource).norm());
				
				if (temp < bestDistance) {
					bestCandidateIndex = b;
					bestDistance = temp;
				}
				
			}
			
			
			--a;
			++b;
			
		}
		
		return bestCandidateIndex;
		
	}
	
	
	private static void swap(int a, int b) {
		
		float tempDist = distanceArray[a];
		distanceArray[a] = distanceArray[b];
		distanceArray[b] = tempDist;
		
		Vector3f temp = vertices[a];
		vertices[a] = vertices[b];
		vertices[b] = temp;
		
	}
	
	
	public static float getHighestZValue() {
		return highestPoint;		
	}
	
	
	public static float getLowestZValue() {
		return lowestPoint;
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
