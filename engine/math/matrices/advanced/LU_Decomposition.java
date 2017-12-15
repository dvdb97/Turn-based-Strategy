package math.matrices.advanced;

import math.matrices.Matrix44f;

public class LU_Decomposition {
	
	private static Matrix44f matrix;
	
	private static Matrix44f l_Matrix;
	
	private static Matrix44f u_Matrix;
	
	
	public static void generate(Matrix44f input) {
		
		l_Matrix = new Matrix44f(1.0f, 0.0f, 0.0f, 0.0f,
								 0.0f, 1.0f, 0.0f, 0.0f,
								 0.0f, 0.0f, 1.0f, 0.0f,
								 0.0f, 0.0f, 0.0f, 1.0f);		
		
		u_Matrix = new Matrix44f(0.0f, 0.0f, 0.0f, 0.0f,
								 0.0f, 0.0f, 0.0f, 0.0f,
								 0.0f, 0.0f, 0.0f, 0.0f,
								 0.0f, 0.0f, 0.0f, 0.0f);
		
		matrix = input;
		
		lu();				
		
	}
	
	
	private static void lu() {
		
		for (int row = 0; row < 4; ++row) {
			
			for (int col = 0; col < 4; ++col) {
				
				if (col >= row) {
					
					computeU(col, row);
					
				} else {
				
					computeL(col, row);
					
				}
				
			}	
			
		}
		
	}
	
	
	private static void computeU(int x, int y) {
		
		float u = matrix.get(x, y);
		
		
		for (int i = 0; i < 4; ++i) {
			
			if (i == x) {
				continue;
			}
			
			u -= l_Matrix.get(i, y) * u_Matrix.get(x, i);
			
		}
		
		
		u_Matrix.set(x, y, u);
		
	}
	
	
	private static void computeL(int x, int y) {
		
		float l = matrix.get(x, y);
		
		
		for (int i = 0; i < 4; ++i) {
			
			if (i == x) {
				continue;
			}
			
			l -= l_Matrix.get(i, y) * u_Matrix.get(x, i);
			
		}
		
		
		l_Matrix.set(x, y, l / u_Matrix.get(x, x));		
		
	}
	
	
	public static Matrix44f getUMatrix() {
	
		return u_Matrix;
		
	}
	
	
	public static Matrix44f getLMatrix() {
		
		return l_Matrix;
		
	}

}
