package lwlal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Matrix33fTest {
	
	Matrix33f matrix;
	
	@Before
	public void setUp() throws Exception {
		matrix = new Matrix33f(	1, 2, 3,
								4, 5, 6,
								7, 8, 9);
	}
	
	@Test
	public final void testColConstructor() {
		Matrix33f m = new Matrix33f(new Vector3f(1, 4, 7), new Vector3f(2, 5, 8), new Vector3f(3, 6, 9));
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
		Matrix33f matrix2 = new Matrix33f(8, 7, 6, 5, 4, 3, 2, 1, 0);
		Matrix33f exp = new Matrix33f(9, 9, 9, 9, 9, 9, 9, 9, 9);
		assertArrayEquals(exp.toArray(), matrix.plus(matrix2).toArray(), 0.0f);
	}
	
	@Test
	public final void testMinusMatrix33f() {
		Matrix33f matrix2 = new Matrix33f(1, 1, 2, 2, 3, 4, 5, 6, 7);
		Matrix33f exp = new Matrix33f(0, 1, 1, 2, 2, 2, 2, 2, 2);
		assertArrayEquals(exp.toArray(), matrix.minus(matrix2).toArray(), 0.0f);
	}
	
	@Test
	public final void testTimesFloat() {
		float f = 2.0f;
		Matrix33f exp = new Matrix33f(2, 4, 6, 8, 10, 12, 14, 16, 18);
		assertArrayEquals(exp.toArray(), matrix.times(f).toArray(), 0.0f);
	}
	
	@Test
	public final void testTimesVector3f() {
		Vector3f v = new Vector3f(0, 1, 1);
		Vector3f exp = new Vector3f(5, 11, 17);
		assertArrayEquals(exp.toArray(), matrix.times(v).toArray(), 0);
	}
	
	@Test
	public final void testTimesMatrix33f() {
		Matrix33f m = new Matrix33f(	1, 0, 1,
										0, 2, 0,
										1, 0, 0);
		
		Matrix33f exp = new Matrix33f(	 4,  4, 1,
										10, 10, 4,
										16, 16, 7);
		
		assertArrayEquals(exp.toArray(), matrix.times(m).toArray(), 0);
	}
	
	
	@Test
	public final void testScalarMatrix33f() {
		Matrix33f matrix2 = new Matrix33f(	1, 1, 1,
											1, 1, 1,
											1, 1, 1);
		
		float exp = 1+2+3+4+5+6+7+8+9;
		assertEquals(exp, matrix.scalar(matrix2), 0);
	}
	
	@Test
	public final void testSetIdentity() {
		matrix.setIdentity();
		Matrix33f exp = new Matrix33f(1, 0, 0, 0, 1, 0, 0, 0, 1);
		assertArrayEquals(exp.toArray(), matrix.toArray(), 0.0f);
	}
	
	@Test
	public final void testTransposed() {
		Matrix33f exp= new Matrix33f(1, 4, 7, 2, 5, 8, 3, 6, 9);
		assertArrayEquals(exp.toArray(), matrix.transposed().toArray(), 0.0f);
	}
	
	@Test
	public final void testNegated() {
		Matrix33f exp = new Matrix33f(-1, -2, -3, -4, -5, -6, -7, -8, -9);
		assertArrayEquals(exp.toArray(), matrix.negated().toArray(), 0.0f);
	}
	
	@Test
	public final void testCopyOf() {
		Matrix33f copiedM = matrix.copyOf();
		assertArrayEquals(copiedM.toArray(), matrix.toArray(), 0);
		assertNotSame(copiedM, matrix);
	}
	
	@Test
	public final void testToArray() {
		float[] exp = new float[] {1, 4, 7, 2, 5, 8, 3, 6, 9};
		assertArrayEquals(exp, matrix.toArray(), 0);
	}
	
}
