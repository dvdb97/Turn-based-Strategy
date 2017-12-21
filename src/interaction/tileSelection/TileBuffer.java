package interaction.tileSelection;

import math.MathUtils;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

public class TileBuffer {
	
	//The array of vertices
	private static Vector3f[] vertices;
	
	/*
	 * An array containing the distance of every vertex to the refPoint. It's generated once and 
	 * used for binary search to save computations.
	 */
	private static float[] distanceArray;
	
	//This array stores the actual index of every vertex to point to the old location of the vertex after sorting "vertices"
	private static int[] oldLocation;
	
	
	//A point in space that let's us sort the vertices by sorting them by their distance to refPoint
	private static Vector3f refPoint;
	
	//The highest vertex in the array.
	private static float highestPoint;
	
	//The lowest vertex in the array.
	private static float lowestPoint;
	
	
	// **************************** Preparation ****************************
	
	
	public static void init(Vector3f[] inputVertices, Vector3f referencePoint) {
		
		vertices = inputVertices;
		
		refPoint = referencePoint;
		
		generateDistanceArray(refPoint);
		
		quickSort(0, vertices.length - 1);
		
	}
	
	
	/*
	 * - Generates a distance to the referencePoint for each vertex
	 * - Looks for the highest and lowest point in the vertex cloud
	 * - Initializes the array that stores the old indices of the vertices
	 */
	private static void generateDistanceArray(Vector3f referencePoint) {
		
		lowestPoint = 100.0f;
		highestPoint = -100.0f;
		
		distanceArray = new float[vertices.length];
		oldLocation = new int[vertices.length];
		
		
		for (int i = 0; i < vertices.length; ++i) {
			
			if (vertices[i].getC() < lowestPoint) {
				lowestPoint = vertices[i].getC();
			}
			
			if (vertices[i].getC() > highestPoint) {
				highestPoint = vertices[i].getC();
			}
			
			
			distanceArray[i] = vertices[i].minus(referencePoint).norm();			
			
			oldLocation[i] = i;
			
		}
		
	}
	
	
	/*
	 * - Sorts the vertices by their distance to the referencePoint
	 * - Aplies the same changes to the arrays "oldLocation" and "distanceArray"
	 */
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
		
		
	//A small utility function for the quicksort algorithm:
	private static void swap(int a, int b) {
		
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
	
	
	public static int getTileIndex(Vector4f origin, Vector4f direction, float tolerance, int steps, int expandingSearchIterations) {
		
		Vector3f rayStart = new Vector3f(origin.getA(), origin.getB(), origin.getC());
		Vector3f rayDir = new Vector3f(direction.getA(), direction.getB(), direction.getC());
		
		return getTileIndex(rayStart, rayDir, tolerance, steps, expandingSearchIterations);
		
		
	}
	
	
	
	/**
	 * This algorithm searches the vertex that is the closest to the ray defined by origin and direction
	 * 
	 * @param origin The origin of the ray
	 *  
	 * @param direction The direction of the ray
	 * 
	 * @param tolerance If the distance between two points is smaller than tolerance we assume that they are actually the same point
	 * 
	 * @param steps When we are looking at the part of the ray that interects the cloud of vertices, steps defines the sampling rate
	 * 
	 * @param expandingSearchInterations Defines the range of the array that is checked during expanding search
	 * 
	 * @return Returns the index of the closest vertex to the ray
	 * 
	 */
	public static int getTileIndex(Vector3f origin, Vector3f direction, float tolerance, int steps, int expandingSearchIterations) {
		
		//Find the starting point of the section of the ray between max = (x, y, highestPoint) and min = (x2, y2, lowestPoint)
		Vector3f entryPoint = computePoint(origin, direction, highestPoint);
		
		//Divide the section in steps pieces
		float rPerStep = computeRPerStep(entryPoint, direction, lowestPoint, steps);
		
		
		
		//The index of the vertex that is currently the best candidate
		int bestCandidateIndex = 0;
		
		//The best candidate's distance to 
		float bestDistance = Float.MAX_VALUE;
		
		
		
		int currentIndex = 0;
		float requestedDistance;
		float candidateDistance;
		
		for (int i = 0; i < steps; ++i) {
			
			//Compute the distance of the current version of the raySource to the reference point
			requestedDistance = MathUtils.getAbsoluteValue(entryPoint.minus(refPoint).norm());
			
			//Search for points with a similar distance to the reference point
			currentIndex = binarySearch(requestedDistance, 0, distanceArray.length - 1, tolerance);
			
			
			//Among those points, look for the point that has to closest distance to the raySource
			//currentIndex = expandingSearch(currentIndex, expandingSearchIterations, entryPoint);
			
			
			//Evaluate the distance of this step's candidate to entryPoint. If it is close enough break.
			candidateDistance = MathUtils.getAbsoluteValue(entryPoint.minus(vertices[currentIndex]).norm());
			
			if (candidateDistance < tolerance) {
				break;
			}
			
			entryPoint.plusEQ(direction.times(rPerStep));
			//TODO: Do we need to change the tolerance when going deeper into the vertex cloud?
			
		}
		
		
		return oldLocation[bestCandidateIndex];
		
	}
	
	
	
	
	
	/**
	 * 
	 * Searches for a location to start looking for a candidate. The algorithm assumes that two vertices
	 * with similar distance to a reference point are more likely to be located close to each other.
	 * 
	 * param resquestedDistance The distance we are looking for.
	 * 
	 * param start The index that defines the lower end of the range of the array we are looking at.
	 * 
	 * param end The index that defines the upper end of the range of the array we are looking at.
	 * 
	 * param tolerance Defines the how similar a distance in the array has to count as a candidate
	 * 
	 */
	private static int binarySearch(float resquestedDistance, int start, int end, float tolerance) {
		
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
	
	
	// **************************** Matrix computations ****************************
	
	
	//Assuming the ray is defined by point = origin + r * direction
	public static Vector3f computePoint(Vector3f origin, Vector3f direction, float requestedZ) {
	
		float r = (requestedZ - origin.getC()) / direction.getC();
		
		return origin.plus(direction.timesEQ(r));
		
	}
	
	
	public static float computeRPerStep(Vector3f origin, Vector3f direction, float requestedZ, int steps) {
		
		return ((requestedZ - origin.getC()) / direction.getC()) / steps;
		
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
