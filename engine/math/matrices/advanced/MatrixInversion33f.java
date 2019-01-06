package math.matrices.advanced;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.matrices.advanced.deprecated.LU_Decomposition_Deprecated;

public class MatrixInversion33f {
	
	private static Matrix33f unityMatrix = new Matrix33f();
	
	private static Matrix33f transformationMatrix = new Matrix33f(0f, 0f, 1f,
																  0f, 1f, 0f,
																  1f, 0f, 0f);
	
	public static Matrix33f computeMultiplicativeInverse(Matrix33f matrix) {
		
		Matrix33f l_Matrix = new Matrix33f(0);
		Matrix33f u_Matrix = new Matrix33f(0);
		
		LU_Decomposition_Deprecated.generate(matrix, l_Matrix, u_Matrix);
		
		Matrix33f l_inverted = getLInverted(l_Matrix);
		Matrix33f u_inverted = getUInverted(u_Matrix);
		
		return u_inverted.times(l_inverted);
		
	}
	
	
	private static Matrix33f getLInverted(Matrix33f l) {
		
		Matrix33f inverse = new Matrix33f();
		
		
		for (int row = 0; row < 3; ++row) {
			
			for (int col = 0; col < 3; ++col) {
				
				computeValue(row, col, l, inverse, unityMatrix);
				
			}
			
		}
		
		return inverse;
		
	}
	
	
	private static void computeValue(int row, int col, Matrix33f mat, Matrix33f inverse, Matrix33f unityMatrix) {
		
		float x = unityMatrix.get(col, row);
		
		float y = 1.0f;
		
		for (int i = 0; i < 3; ++i) {
			
			if (i == row) {
				
				y = mat.get(i, row) == 0.0f ? 1.0f : mat.get(i, row);
				
				continue;
			}
			
			x -= mat.get(i, row) * inverse.get(col, i);
			
		}
		
		inverse.set(col, row, x / y);
		
	}
	
	
	private static Matrix33f getUInverted(Matrix33f u_Matrix) {	
		
		Matrix33f u = u_Matrix.copyOf();
		
		u = transformationMatrix.times(u);
		u = u.times(transformationMatrix);		
		
		Matrix33f unity = unityMatrix.copyOf();
		
		unity = transformationMatrix.times(unity);
		unity = unity.times(transformationMatrix);	
		
				
		Matrix33f inverse = new Matrix33f();
		
		
		for (int row = 0; row < 3; ++row) {
			
			for (int col = 0; col < 3; ++col) {
				
				computeValue(row, col, u, inverse, unity);
				
			}
			
		}
		
		inverse = inverse.times(transformationMatrix);
		inverse = transformationMatrix.times(inverse);		
		
		return inverse;
	}

}
