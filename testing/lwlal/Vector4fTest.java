package lwlal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Vector4fTest {
	
	private Vector4f vector;
	
	@Before
	public void setUp() throws Exception {
		vector = new Vector4f(1, 2, 3, 4);
	}
	
	@Test
	public final void testEquivalentTo() {
		Vector4f w = new Vector4f(1, 2, 3, 4);
		assertTrue(vector.equivalentTo(w));
		
		Vector4f u = new Vector4f(4, 3, 2, 1);
		assertFalse(vector.equivalentTo(u));
	}
	
	@Test
	public final void testPlusVector4f() {
		Vector4f w = new Vector4f(4, 5, 6, 7);
		Vector4f exp = new Vector4f(5, 7, 9, 11);
		assertArrayEquals(exp.toArray(), vector.plus(w).toArray(), 0);
	}

	@Test
	public final void testMinusVector4f() {
		Vector4f w = new Vector4f(2, 2, 2, 2);
		Vector4f exp = new Vector4f(-1, 0, 1, 2);
		assertTrue(vector.minus(w).equivalentTo(exp));
	}

	@Test
	public final void testTimesFloat() {
		float r = 2;
		Vector4f exp = new Vector4f(2, 4, 6, 8);
		assertTrue(vector.times(r).equivalentTo(exp));
	}

	@Test
	public final void testScalarVector4f() {
		Vector4f w = new Vector4f(-2, 1, 4, -3);
		float exp1 = 0;
		assertEquals(exp1, vector.scalar(w), 0);
		
		Vector4f u = new Vector4f(-2, 1, 1, -2);
		float exp2 = -5;
		assertEquals(exp2, vector.scalar(u), 0);
	}

	@Test
	public final void testNormalize() {
		vector.normalize();
		Vector4f exp = new Vector4f(0.182574f, 0.365148f, 0.547723f, 0.730297f);
		assertArrayEquals(exp.toArray(), vector.toArray(), 0.0001f);
	}

	@Test
	public final void testNegated() {
		Vector4f exp = new Vector4f(-1, -2, -3, -4);
		assertTrue(vector.negated().equivalentTo(exp));
	}

	@Test
	public final void testCopyOf() {
		Vector4f exp = new Vector4f(1, 2, 3, 4);
		Vector4f copy = vector.copyOf();
		assertArrayEquals(exp.toArray(), copy.toArray(), 0);
		assertNotSame(vector, copy);
	}

	@Test
	public final void testToVector3f() {
		Vector3f exp = new Vector3f(1, 2, 3);
		assertTrue(vector.toVector3f().equivalentTo(exp));
	}

	@Test
	public final void testToArray() {
		float[] exp = new float[] {1, 2, 3, 4};
		assertArrayEquals(exp, vector.toArray(), 0);
	}

}
