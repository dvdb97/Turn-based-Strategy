package math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import math.matrices.Matrix33f;
import math.matrices.advanced.MatrixInversion33f;

/**
 * @author jona
 *
 */
class TestMatrixInversion33f {
	
	double[] randomNumbers;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		randomNumbers = new double[9];
		for (int i=0; i<9; i++) {
			randomNumbers[i] = Math.random()*2 - 1 ;
		}
		
	}

	/**
	 * Test method for {@link math.matrices.advanced.MatrixInversion33f#generateMultiplicativeInverse(math.matrices.Matrix33f)}.
	 */
	@Test
	final void testGenerateMultiplicativeInverse1() {
		
		float[] a = new float[9];
		for (int i=0; i<9; i++) {
			a[i] = (float)randomNumbers[i];
		}
		
		Matrix33f matrix = new Matrix33f(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8]);
		
		Matrix33f inverse = MatrixInversion33f.computeMultiplicativeInverse(matrix);
		
		float[] matrixTimesInverse = matrix.times(inverse).toArray();
		float[] identity = new float[] {1,0,0, 0,1,0, 0,0,1};
		
		assertArrayEquals(identity, matrixTimesInverse, 0.0001f);
		
	}
	
	/**
	 * Test method for {@link math.matrices.advanced.MatrixInversion33f#generateMultiplicativeInverse(math.matrices.Matrix33f)}.
	 */
	@Test
	final void testGenerateMultiplicativeInverse2() {
		
		float[] a = new float[9];
		for (int i=0; i<9; i++) {
			a[i] = (float)( randomNumbers[i]*16000 );
		}
		
		Matrix33f matrix = new Matrix33f(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8]);
		
		Matrix33f inverse = MatrixInversion33f.computeMultiplicativeInverse(matrix);
		
		float[] matrixTimesInverse = matrix.times(inverse).toArray();
		float[] identity = new float[] {1,0,0, 0,1,0, 0,0,1};
		
		assertArrayEquals(identity, matrixTimesInverse, 0.0001f);
		
	}
	
}
