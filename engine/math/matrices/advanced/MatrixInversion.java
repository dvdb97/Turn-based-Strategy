package math.matrices.advanced;

import math.matrices.Matrix44f;

public class MatrixInversion {
	
	private static Matrix44f unityMatrix = new Matrix44f(1f, 0f, 0f, 0f, 
														 0f, 1f, 0f, 0f, 
														 0f, 0f, 1f, 0f,
														 0f, 0f, 0f, 1f);
	
	private static Matrix44f transformationMatrix = new Matrix44f(0f, 0f, 0f, 1f, 
																  0f, 0f, 1f, 0f, 
																  0f, 1f, 0f, 0f, 
																  1f, 0f, 0f, 0f);
	
	
	public static Matrix44f generateMultiplicativeInverse(Matrix44f matrix) {
		
		LU_Decomposition.generate(matrix);
		
		Matrix44f l_Matrix = LU_Decomposition.getLMatrix();
		Matrix44f u_Matrix = LU_Decomposition.getUMatrix();
		
		
		Matrix44f l_inverted = getLInverted(l_Matrix);
		Matrix44f u_inverted = getUInverted(u_Matrix);
		
		
		return l_inverted.times(u_inverted);
		
	}
	
	
	private static Matrix44f getLInverted(Matrix44f l) {
		
		Matrix44f inverse = new Matrix44f();
		
		
		for (int row = 0; row < 4; ++row) {
			
			for (int col = 0; col < 4; ++col) {
				
				computeValue(row, col, l, inverse, unityMatrix);
				
			}
			
		}
		
		return inverse;
		
	}
	
	
	private static void computeValue(int row, int col, Matrix44f mat, Matrix44f inverse, Matrix44f unityMatrix) {
		
		float x = unityMatrix.get(col, row);
		
		float y = 1.0f;
		
		for (int i = 0; i < 4; ++i) {
			
			if (i == row) {
				
				y = mat.get(i, row) == 0.0f ? 1.0f : mat.get(i, row);
				
				continue;
			}
			
			x -= mat.get(i, row) * inverse.get(col, i);
			
		}
		
		inverse.set(col, row, x / y);
		
	}
	
	
	private static Matrix44f getUInverted(Matrix44f u_Matrix) {
		
		Matrix44f u = u_Matrix.copyOf();
		
		u = transformationMatrix.times(u);
		u = u.times(transformationMatrix);
		
		
		Matrix44f unity = unityMatrix.copyOf();
		
		
		unity = transformationMatrix.times(unity);
		unity = unity.times(transformationMatrix);		
		
		Matrix44f inverse = new Matrix44f();
		
		
		for (int row = 0; row < 4; ++row) {
			
			for (int col = 0; col < 4; ++col) {
				
				computeValue(row, col, u, inverse, unity);
				
			}
			
		}
		
		
		inverse = inverse.times(transformationMatrix);
		inverse = transformationMatrix.times(inverse);
		
		return inverse;
	}

}
