package lwlal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Matrix44fTest {
	
	Matrix44f matrix;
	
	@Before
	public void setUp() throws Exception {
		matrix = new Matrix44f(	 1,  2,  3,  4,
								 5,  6,  7,  8,
								 9, 10, 11, 12,
								13, 14, 15, 16);
	}
	
	@Test
	public final void testColConstructor() {
		Matrix44f m = new Matrix44f(	new Vector4f(1,5,9,13),  new Vector4f(2,6,10,14),
										new Vector4f(3,7,11,15), new Vector4f(4,8,12,16));
		
		assertArrayEquals(matrix.toArray(), m.toArray(), 0);
	}
	
	/*
	@Test
	public final void testPrint() {
		matrix.print();
	}
	*/
	
	@Test
	public final void testPlusMatrix33f() {
		Matrix44f matrix2 = new Matrix44f(	15, 14, 13, 12,
											11, 10,  9,  8,
											 7,  6,  5,  4,
											 3,  2,  1,  0);
		
		Matrix44f exp = new Matrix44f(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16);
		
		assertArrayEquals(exp.toArray(), matrix.plus(matrix2).toArray(), 0.0f);
	}
	
	@Test
	public final void testMinusMatrix33f() {
		Matrix44f matrix2 = new Matrix44f(	 1,  1,  1,  2,
											 3,  4,  5,  6,
											 7,  8,  9, 10,
											11, 12, 13, 14);
		
		Matrix44f exp = new Matrix44f(	0, 1, 2, 2,
										2, 2, 2, 2,
										2, 2, 2, 2,
										2, 2, 2, 2);
		
		assertArrayEquals(exp.toArray(), matrix.minus(matrix2).toArray(), 0.0f);
	}
	
	@Test
	public final void testTimesFloat() {
		float f = 2.0f;
		Matrix44f exp = new Matrix44f(	 2,  4,  6,  8,
										10, 12, 14, 16,
										18, 20, 22, 24,
										26, 28, 30, 32);
		
		assertArrayEquals(exp.toArray(), matrix.times(f).toArray(), 0.0f);
	}
	
	@Test
	public final void testTimesVector4f() {
		Vector4f v = new Vector4f(2, 0, 1, 1);
		Vector4f exp = new Vector4f(9, 25, 41, 57);
		assertArrayEquals(exp.toArray(), matrix.times(v).toArray(), 0);
	}
	
	@Test
	public final void testTimesMatrix44f() {
		Matrix44f m = new Matrix44f(	1, 0, 1, 0,
										0, 2, 0, 1,
										1, 0, 0, 0,
										0, 1, 1, 0);
		
		Matrix44f exp = new Matrix44f(	 4,  8,  5,  2,
										12, 20, 13,  6,
										20, 32, 21, 10,
										28, 44, 29, 14);
		
		assertArrayEquals(exp.toArray(), matrix.times(m).toArray(), 0);
	}
	
	@Test
	public final void testScalarMatrix44f() {
		Matrix44f matrix2 = new Matrix44f(	1, 1, 1, 1,
											1, 1, 1, 1,
											1, 1, 1, 1,
											1, 1, 1, 1);
		
		float exp = 1+2+3+4+5+6+7+8+9+10+11+12+13+14+15+16;
		assertEquals(exp, matrix.scalar(matrix2), 0);
	}
	
	@Test
	public final void testSetIdentity() {
		matrix.setIdentity();
		
		Matrix44f exp = new Matrix44f(	1, 0, 0, 0,
										0, 1, 0, 0,
										0, 0, 1, 0,
										0, 0, 0, 1);
		
		assertArrayEquals(exp.toArray(), matrix.toArray(), 0.0f);
	}
	
	@Test
	public final void testTransposed() {
		Matrix44f exp= new Matrix44f(	1, 5,  9, 13,
										2, 6, 10, 14,
										3, 7, 11, 15,
										4, 8, 12, 16);
		
		assertArrayEquals(exp.toArray(), matrix.transposed().toArray(), 0.0f);
	}
	
	@Test
	public final void testNegated() {
		Matrix44f exp = new Matrix44f(-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16);
		assertArrayEquals(exp.toArray(), matrix.negated().toArray(), 0.0f);
	}
	
	@Test
	public final void testCopyOf1() {
		Matrix44f copiedM = matrix.copyOf();
		assertNotSame(copiedM, matrix);
	}
	
	@Test
	public final void testCopyOf2() {
		Matrix44f copiedM = matrix.copyOf();
		assertArrayEquals(copiedM.toArray(), matrix.toArray(), 0);
	}
	
	@Test
	public final void testToArray() {
		float[] exp = new float[] {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16};
		assertArrayEquals(exp, matrix.toArray(), 0);
	}

}
