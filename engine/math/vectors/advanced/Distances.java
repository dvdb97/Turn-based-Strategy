package math.vectors.advanced;

import math.vectors.Vector3f;

public class Distances {
	
	public static float distanceLinePoint(Vector3f lineOrigin, Vector3f lineDirection, Vector3f point) {
		
		float a = lineDirection.scalar(point.minus(lineOrigin));
		float b = lineDirection.scalar(lineDirection);
		
		float r = a / b;
		
		Vector3f pointOnLine = lineOrigin.plus(lineDirection.times(r));
		
		return pointOnLine.minus(point).norm();
		
	}

}
