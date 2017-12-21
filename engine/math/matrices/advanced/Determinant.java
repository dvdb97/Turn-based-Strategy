package math.matrices.advanced;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;

public class Determinant {
	
	//TODO:
	public static float getDeterminant(Matrix33f matrix) {
		return 0f;
	}
	
	
	//TODO:
	public static float getDeterminant(Matrix44f matrix) {
		
		LU_Decomposition.generate(matrix);
		
		Matrix44f u_Matrix = LU_Decomposition.getUMatrix();
		
		return u_Matrix.getA1() * u_Matrix.getB2() * u_Matrix.getC3() * u_Matrix.getD4();
		
	}
	

}
