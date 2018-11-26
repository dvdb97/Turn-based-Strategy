package math.matrices.advanced.deprecated;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;

public class LU_Decomposition_Deprecated {	
	
	//TODO: Set the input to references
	public static void generate(Matrix44f input, Matrix44f l_Matrix, Matrix44f u_Matrix) {
		
		l_Matrix.setA1(1f);
		l_Matrix.setB2(1f);
		l_Matrix.setC3(1f);
		l_Matrix.setD4(1f);
		
		lu(input, l_Matrix, u_Matrix);				
		
	}
	
	
	public static void generate(Matrix33f input, Matrix33f l_Matrix, Matrix33f u_Matrix) {
		
		l_Matrix.setA1(1f);
		l_Matrix.setB2(1f);
		l_Matrix.setC3(1f);
		
		lu(input, l_Matrix, u_Matrix);
		
	}
	
	
	private static void lu(Matrix44f matrix, Matrix44f l_Matrix, Matrix44f u_Matrix) {
		
		for (int row = 0; row < 4; ++row) {
			
			for (int col = 0; col < 4; ++col) {
				
				if (col >= row) {
					
					computeU(col, row, matrix, l_Matrix, u_Matrix);
					
				} else {
				
					computeL(col, row, matrix, l_Matrix, u_Matrix);
					
				}
				
			}	
			
		}
		
	}
	
	
	private static void lu(Matrix33f matrix, Matrix33f l_Matrix, Matrix33f u_Matrix) {
		
		for (int row = 0; row < 3; ++row) {
			
			for (int col = 0; col < 3; ++col) {
				
				if (col >= row) {
					
					computeU(col, row, matrix, l_Matrix, u_Matrix);
					
				} else {
				
					computeL(col, row, matrix, l_Matrix, u_Matrix);
					
				}
				
			}	
			
		}
		
	}
	
	
	private static void computeU(int x, int y, Matrix44f matrix, Matrix44f l_Matrix, Matrix44f u_Matrix) {
		
		float u = matrix.get(x, y);
		
		
		for (int i = 0; i < 4; ++i) {
			
			if (i == x) {
				continue;
			}
			
			u -= l_Matrix.get(i, y) * u_Matrix.get(x, i);
			
		}
		
		
		u_Matrix.set(x, y, u);
		
	}
	
	
	private static void computeU(int x, int y, Matrix33f matrix, Matrix33f l_Matrix, Matrix33f u_Matrix) {
		
		float u = matrix.get(x, y);
		
		
		for (int i = 0; i < 3; ++i) {
			
			if (i == x) {
				continue;
			}
			
			u -= l_Matrix.get(i, y) * u_Matrix.get(x, i);
			
		}
		
		
		u_Matrix.set(x, y, u);
		
	}
	
	
	private static void computeL(int x, int y, Matrix44f matrix, Matrix44f l_Matrix, Matrix44f u_Matrix) {
		
		float l = matrix.get(x, y);
		
		
		for (int i = 0; i < 4; ++i) {
			
			if (i == x) {
				continue;
			}
			
			l -= l_Matrix.get(i, y) * u_Matrix.get(x, i);
			
		}
		
		l_Matrix.set(x, y, l / u_Matrix.get(x, x));	
	}
	
	
	private static void computeL(int x, int y, Matrix33f matrix, Matrix33f l_Matrix, Matrix33f u_Matrix) {
		
		float l = matrix.get(x, y);
		
		
		for (int i = 0; i < 3; ++i) {
			
			if (i == x) {
				continue;
			}
			
			l -= l_Matrix.get(i, y) * u_Matrix.get(x, i);
			
		}
		
		l_Matrix.set(x, y, l / u_Matrix.get(x, x));		
	}

}
