package rendering.matrices.transformation;

import math.matrices.Matrix44f;
import math.vectors.Vector3f;

public class ScalingMatrix {
	
	/**
	 * 
	 * Generates a scaling matrix with the given value
	 * as the scaling for each dimension.
	 * 
	 * @param value The scaling for all dimensions
	 * @return Returns a scaling matrix
	 */
	public static Matrix44f getScalingMatrix(float value) {
		
		Matrix44f matrix = new Matrix44f();
		
		matrix.setA1(value);
		matrix.setB2(value);
		matrix.setC3(value);
		
		matrix.setC4(1f);

		return matrix;
		
	}
	
	
	/**
	 * 
	 * Generates a scaling matrix with the given vector. Each
	 * value will be used as the scaling for the corresponding
	 * dimension.
	 * 
	 * @param scaling The scaling vector.
	 * @return Returns a scaling matrix.
	 */
	public static Matrix44f getScalingMatrix(Vector3f scaling) {
		Matrix44f matrix = new Matrix44f();
		
		matrix.setA1(scaling.getA());
		matrix.setB2(scaling.getB());
		matrix.setC3(scaling.getC());
		
		matrix.setD4(1f);
		
		return matrix;
	}
	
}
