package math.matrices.advanced;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.matrices.advanced.deprecated.LU_Decomposition_Deprecated;

public class Determinant {
	
	//TODO:
	public static float getDeterminant(Matrix44f matrix) {
		
		Matrix44f u_Matrix = new Matrix44f();
		u_Matrix.times(0f);
		
		LU_Decomposition.luDecomposition(matrix, new Matrix44f(), u_Matrix);
		
		return u_Matrix.getA1() * u_Matrix.getB2() * u_Matrix.getC3() * u_Matrix.getD4();
		
	}
	
	
	public static float getDeterminant(Matrix33f matrix) {
		
		Matrix33f u_Matrix = new Matrix33f();
		u_Matrix.times(0f);
		
		LU_Decomposition_Deprecated.generate(matrix, new Matrix33f(), u_Matrix);
		
		return u_Matrix.getA1() * u_Matrix.getB2() * u_Matrix.getC3();
		
	}
	

}
