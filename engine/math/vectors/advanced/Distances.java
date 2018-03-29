package math.vectors.advanced;

import math.vectors.Vector3f;

public class Distances {
	
	/**
	 * 
	 * @return the distance between the point and the line
	 */
	public static float distanceLinePoint(Vector3f lineStartingPoint, Vector3f lineDirection, Vector3f point) {
		
		float a = lineDirection.scalar(point.minus(lineStartingPoint));
		float b = lineDirection.scalar(lineDirection);
		
		float r = a / b;
		
		Vector3f pointOnLine = lineStartingPoint.plus(lineDirection.times(r));
		
		return pointOnLine.minus(point).norm();
		
	}
	
	/**
	 * calculates the point that is the closest to the line
	 * 
	 * @param lineStartingPoint the origin (or starting point of the line)
	 * @param lineDirection the direction of the line
	 * @param points an array of points to be considered
	 * @return the closest point's index in the give array
	 */
	public static int getClosestPointToLine(Vector3f lineStartingPoint, Vector3f lineDirection, Vector3f[] points) {
		
		float distance;
		
		float bestCandidateDistance = Distances.distanceLinePoint(lineStartingPoint, lineDirection, points[0]);
		int bestCandidateIndex = 0;
		
		for (int i=1; i<points.length; i++) {
			
			distance = distanceLinePoint(lineStartingPoint, lineDirection, points[i]);
			
			if (distance < bestCandidateDistance) {
				bestCandidateDistance = distance;
				bestCandidateIndex = i;
			}
			
		}
		
		return bestCandidateIndex;
		
	}
	
}
