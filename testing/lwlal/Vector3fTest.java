package lwlal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Vector3fTest {
	
	private Vector3f vector;
	
	@Before
	public void setUp() throws Exception {
		vector = new Vector3f(1, 2, 3);
	}
	
	@Test
	public final void testEquivalentTo() {
		Vector3f w = new Vector3f(1, 2, 3);
		assertTrue(vector.equivalentTo(w));
		
		Vector3f u = new Vector3f(3, 2, 1);
		assertFalse(vector.equivalentTo(u));
	}
	
	@Test
	public final void testPlusVector3f() {
		Vector3f w = new Vector3f(4, 5, 6);
		Vector3f exp = new Vector3f(5, 7, 9);
		assertArrayEquals(exp.toArray(), vector.plus(w).toArray(), 0);
	}

	@Test
	public final void testMinusVector3f() {
		Vector3f w = new Vector3f(2, 2, 2);
		Vector3f exp = new Vector3f(-1, 0, 1);
		assertTrue(vector.minus(w).equivalentTo(exp));
	}

	@Test
	public final void testTimesFloat() {
		float r = 2;
		Vector3f exp = new Vector3f(2, 4, 6);
		assertTrue(vector.times(r).equivalentTo(exp));
	}

	@Test
	public final void testScalarVector3f() {
		Vector3f w = new Vector3f(-2, 1, 0);
		float exp1 = 0;
		assertEquals(exp1, vector.scalar(w), 0);
		
		Vector3f u = new Vector3f(-2, 1, 1);
		float exp2 = 3;
		assertEquals(exp2, vector.scalar(u), 0);
	}

	@Test
	public final void testNormalize() {
		vector.normalize();
		Vector3f exp = new Vector3f(0.267261f, 0.534522f, 0.801784f);
		assertArrayEquals(exp.toArray(), vector.toArray(), 0.0001f);
	}

	@Test
	public final void testNegated() {
		Vector3f exp = new Vector3f(-1, -2, -3);
		assertTrue(vector.negated().equivalentTo(exp));
	}

	@Test
	public final void testCross() {
		Vector3f w = new Vector3f(-3, 4, 7);
		Vector3f exp = new Vector3f(2, -16, 10);
		
		assertTrue(vector.cross(w).equivalentTo(exp));
	}

	@Test
	public final void testCopyOf() {
		Vector3f exp = new Vector3f(1, 2, 3);
		Vector3f copy = vector.copyOf();
		assertArrayEquals(exp.toArray(), copy.toArray(), 0);
		assertNotSame(vector, copy);
	}

	@Test
	public final void testToVector4f() {
		Vector4f exp = new Vector4f(1, 2, 3, 4);
		assertTrue(vector.toVector4f(4).equivalentTo(exp));
	}

	@Test
	public final void testToArray() {
		float[] exp = new float[] {1, 2, 3};
		assertArrayEquals(exp, vector.toArray(), 0);
	}

}
