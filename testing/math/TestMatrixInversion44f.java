package math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion44f;

/**
 * @author jona
 *
 */
class TestMatrixInversion44f {
	
	double[] randomNumbers;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		randomNumbers = new double[16];
		for (int i=0; i<16; i++) {
			randomNumbers[i] = Math.random()*2 - 1 ;
		}
		
	}

	/**
	 * Test method for {@link math.matrices.advanced.MatrixInversion44f#generateMultiplicativeInverse(math.matrices.Matrix44f)}.
	 */
	@Test
	final void testGenerateMultiplicativeInverse1() {
		
		float[] a = new float[16];
		for (int i=0; i<16; i++) {
			a[i] = (float)randomNumbers[i];
		}
		
		Matrix44f matrix = new Matrix44f(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14], a[15]);
		
		Matrix44f inverse = MatrixInversion44f.computeMultiplicativeInverse(matrix);
		
		float[] matrixTimesInverse = matrix.times(inverse).toArray();
		float[] identity = new float[] {1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1};
		
		assertArrayEquals(identity, matrixTimesInverse, 0.0001f);
		
	}
	
	/**
	 * Test method for {@link math.matrices.advanced.MatrixInversion44f#generateMultiplicativeInverse(math.matrices.Matrix44f)}.
	 */
	@Test
	final void testGenerateMultiplicativeInverse2() {
		
		float[] a = new float[16];
		for (int i=0; i<16; i++) {
			a[i] = (float)( randomNumbers[i]*16000 );
		}
		
		Matrix44f matrix = new Matrix44f(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[11], a[12], a[13], a[14], a[15]);
		
		Matrix44f inverse = MatrixInversion44f.computeMultiplicativeInverse(matrix);
		
		float[] matrixTimesInverse = matrix.times(inverse).toArray();
		float[] identity = new float[] {1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1};
		
		assertArrayEquals(identity, matrixTimesInverse, 0.0001f);
		
	}
	
}
