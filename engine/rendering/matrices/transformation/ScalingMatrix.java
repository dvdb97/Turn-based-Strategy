package rendering.matrices.transformation;

import math.matrices.Matrix44f;

public class ScalingMatrix {
	
	public static Matrix44f getScalingMatrix(float value) {
		
		Matrix44f matrix = new Matrix44f();
		matrix.setIdentity();
		
		matrix.setA1(value);
		matrix.setB2(value);
		matrix.setC3(value);

		return matrix;
		
	}
	
}
