package math.matrices.advanced;

import math.matrices.Matrix44f;
import rendering.matrices.projectionMatrices.ProjectionMatrix;

public class LU_Decomposition {
	
	public static void luDecomposition(Matrix44f matrix, Matrix44f l_ptr, Matrix44f u_ptr) {
		
		final float N = 4;
		
		for (int i = 0; i < N; ++i) {
			
			//Upper triangular matrix:
			for (int k = i; k < N; ++k) {
				
				float sum = 0f;
				
				for (int j = 0; j < i; ++j) 
					sum += l_ptr.get(j, i) * u_ptr.get(k, j);
				
				u_ptr.set(k, i, matrix.get(k, i) - sum);
			}
			
			//Lower triangular matrix:
			for (int k = i; k < N; ++k) {
				if (i == k) {
					l_ptr.set(i, i, 1f);
				} else {
					float sum = 0f;
					
					for (int j = 0; j < i; ++j) 
						sum += l_ptr.get(j, k) * u_ptr.get(i, j);
					
					l_ptr.set(i, k, (matrix.get(i, k) - sum) / u_ptr.get(i, i));
				}
			}
			
		}
		
	}
	
	
	public static void main(String[] args) {
		Matrix44f matrix = ProjectionMatrix.perspective();
		
		System.out.println(matrix);
		
		Matrix44f u_ptr = Matrix44f.zero();
		Matrix44f l_ptr = Matrix44f.zero();
		
		luDecomposition(matrix, l_ptr, u_ptr);
		
		System.out.println("Lower: \n" + l_ptr);
		System.out.println("Upper: \n" + u_ptr);
		
		System.out.println(l_ptr.times(u_ptr));
	}

}
