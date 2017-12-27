package math.vectors.advanced;

import math.vectors.Vector3f;

public class Distances {
	
	public static float computeDistance(Vector3f origin, Vector3f direction, Vector3f point) {
		
		float a = direction.getA() * (point.getA() - origin.getA());
		float b = direction.getB() * (point.getB() - origin.getB());
		float c = direction.getC() * (point.getC() - origin.getC());
		
		float d = (direction.getA() * direction.getA()) + (direction.getB() * direction.getB()) + (direction.getC() * direction.getC()); 
		
		float r = (a + b + c) / d;
		
		Vector3f pointOnLine = origin.plus(direction.times(r));
		
		return pointOnLine.minus(point).norm();
		
	}

}
