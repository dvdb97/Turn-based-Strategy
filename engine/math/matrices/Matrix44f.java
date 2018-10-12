package math.matrices;

import math.matrices.advanced.Determinant;
import math.matrices.advanced.MatrixInversion44f;
import math.vectors.Vector4f;

public class Matrix44f extends Matrixf {
	
	private final static int N = 4;
	private final static int LENGTH = N*N;
	
	
	public static final Matrix44f IDENTITY = new Matrix44f();
		
	
	//----------------------------- constructor ---------------------
	
	/**
	 * 
	 * @param a1 top left
	 * @param a2
	 * @param a3 top right
	 * @param b1
	 * @param b2
	 * @param b3
	 * @param c1 bottom left
	 * @param c2
	 * @param c3 bottom right
	 */
	public Matrix44f(float a1, float a2, float a3, float a4, float b1, float b2, float b3, float b4, float c1, float c2, float c3, float c4, float d1, float d2, float d3, float d4) {
		
		super(LENGTH);
		
		set( 0, a1); set( 4, a2); set(  8, a3);	set( 12, a4);
		set( 1, b1); set( 5, b2); set(  9, b3);	set( 13, b4);
		set( 2, c1); set( 6, c2); set( 10, c3);	set( 14, c4);
		set( 3, d1); set( 7, d2); set( 11, d3);	set( 15, d4);
		
	}
	
	
	/**
	 * 
	 * @param data the data to be stored in this matrix
	 * 
	 */
	public Matrix44f(float[] data) {
		
		super(LENGTH);
		
		if (data.length == LENGTH) {
			
			for (int i = 0; i < data.length; ++i) {
				
				int x = i / N;
				int y = i % N;
				
				
				this.set(x * N + y, data[i]);
				
			}
			
		}
		
	}
	
	
	/**
	 * 
	 * @param col1 first column
	 * @param col2 second column
	 * @param col3 third column
	 * @param col4 fourth column
	 */
	public Matrix44f(Vector4f col1, Vector4f col2, Vector4f col3, Vector4f col4) {
		
		super(LENGTH);
		
		set( 0, col1.getA()); set( 4, col2.getA()); set( 8, col3.getA()); set(12, col4.getA());
		set( 1, col1.getB()); set( 5, col2.getB()); set( 9, col3.getB()); set(13, col4.getB());
		set( 2, col1.getC()); set( 6, col2.getC()); set(10, col3.getC()); set(14, col4.getC());
		set( 3, col1.getD()); set( 7, col2.getD()); set(11, col3.getD()); set(15, col4.getD());
		
	}
	
	/**
	 * creates an 4x4 identity matrix
	 */
	public Matrix44f() {
		
		super(LENGTH);
		
		setIdentity();
	}
	
	
	/**
	 * 
	 * @return Returns a zero matrix.
	 */
	public static Matrix44f zero() {
		return new Matrix44f(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
	}
	
	
	//------------------------- math methods ------------------------
	
	/**
	 * Matrix addition
	 * 
	 * @param m summand
	 * @return returns itself after a component wise addition
	 */
	public Matrix44f plus(Matrix44f m) {
		super.plus(m);
		return this;
	}
	
	/**
	 * Matrix subtraction
	 * 
	 * @param m subtrahend
	 * @return returns itself after a component subtraction
	 */
	public Matrix44f minus(Matrix44f m) {
		super.minus(m);
		return this;
	}
	
	/**
	 * Scalar multiplication
	 * 
	 * @param r the scalar, every component is multiplicated with
	 * @return returns itself after the multiplication
	 */
	public Matrix44f times(float r) {
		super.times(r);
		return this;
	}
	
	/**
	 * Vector multiplication
	 * 
	 * @param v
	 * @return 
	 */
	public Vector4f times(Vector4f v) {
		return new Vector4f(	v.scalar(new Vector4f(getA1(), getA2(), getA3(), getA4())),
								v.scalar(new Vector4f(getB1(), getB2(), getB3(), getB4())),
								v.scalar(new Vector4f(getC1(), getC2(), getC3(), getC4())),
								v.scalar(new Vector4f(getD1(), getD2(), getD3(), getD4()))
							);
	}
	
	public Matrix44f times(Matrix44f m) {
		return new Matrix44f(	this.times(m.getCol(0)),	//1st col
								this.times(m.getCol(1)),	//2nd col
								this.times(m.getCol(2)),	//3rd col
								this.times(m.getCol(3))		//4th col
							);
	}
	
	/**
	 * Dot Product (Inner Product)
	 * 
	 * @param m factor
	 * @return the dot product
	 */
	public float scalar(Matrix44f m) {
		return super.scalar(m);
		
	}
	
	/**
	 * turns the matrix into a identity matrix
	 * (matrix with just zeros, but ones on the diagonal)
	 */
	public void setIdentity() {
		
		set( 0, 1); set( 4, 0); set(  8, 0);	set( 12, 0);
		set( 1, 0); set( 5, 1); set(  9, 0);	set( 13, 0);
		set( 2, 0); set( 6, 0); set( 10, 1);	set( 14, 0);
		set( 3, 0); set( 7, 0); set( 11, 0);	set( 15, 1);
		
	}
	
	/**
	 * @return returns a new matrix, which is this matrix transposed
	 */
	public Matrix44f transposed() {
		return new Matrix44f(getA1(), getB1(), getC1(), getD1(), getA2(), getB2(), getC2(), getD2(), getA3(), getB3(), getC3(), getD3(), getA4(), getB4(), getC4(), getD4());
	}
	
	
	/**
	 * Negation
	 * 
	 * @return itself, multiplicated with -1
	 */
	public Matrix44f negated() {
		super.voidNegated();
		return this;
	}
	
	
	/**
	 * 
	 * @return Returns true if the matrix is invertible.
	 */
	public boolean isInvertible() {
		return Determinant.getDeterminant(this) != 0f;
	}
	
	
	/**
	 * 
	 * @return Returns the inverse of this matrix. This function will also 
	 * return something if the matrix isn't invertible.
	 */
	public Matrix44f inverse() {
		return MatrixInversion44f.generateMultiplicativeInverse(this);
	}
	
	//---------------------- other methods -------------------
	
	
	/**
	 * 
	 * Converts this Matrix44f to a Matrix33f.
	 * 
	 * @return Returns a new Matrix33f.
	 */
	public Matrix33f toMatrix33f() {
		return new Matrix33f(this.getA1(), this.getA2(), this.getA3(), 
							 this.getB1(), this.getB2(), this.getB3(), 
							 this.getC1(), this.getC2(), this.getC3());
	}
	
	
	/**
	 * prints out the matrix
	 */
	public void print() {
		
		float[] a = this.toArray();
		
		for (int row=0; row<N; row++) {
			for (int col=0; col<N; col++) {
				
				System.out.print(a[row+N*col] + "   ");
				
			}
			System.out.println();
		}
		
	}
	
	
	@Override
	public String toString() {
		
		float[] a = this.toArray();
		String output = "";
		
		for (int row=0; row<N; row++) {
			for (int col=0; col<N; col++) {
				
				output += a[row+N*col] + "   ";
				
			}
			output += "\n";
		}
		
		return output;
	}


	/**
	 * @return returns a deep copy of this matrix
	 */
	public Matrix44f copyOf() {
		
		return new Matrix44f(getA1(), getA2(), getA3(), getA4(), getB1(), getB2(), getB3(), getB4(), getC1(), getC2(), getC3(), getC4(), getD1(), getD2(), getD3(), getD4());
	}
	
	//------------------------------- Get & Set ---------------------
	
	public float getA1() {
		return get(0);
	}
	public void setA1(float a1) {
		set(0, a1);
	}
	
	public float getA2() {
		return get(4);
	}
	public void setA2(float a2) {
		set(4, a2);
	}
	
	public float getA3() {
		return get(8);
	}
	public void setA3(float a3) {
		set(8, a3);
	}
	
	public float getA4() {
		return get(12);
	}
	public void setA4(float a4) {
		set(12, a4);
	}
	
	//------------------------------------
	
	public float getB1() {
		return get(1);
	}
	public void setB1(float b1) {
		set(1, b1);
	}
	
	public float getB2() {
		return get(5);
	}
	public void setB2(float b2) {
		set(5, b2);
	}
	
	public float getB3() {
		return get(9);
	}
	public void setB3(float b3) {
		set(9, b3);
	}
	
	public float getB4() {
		return get(13);
	}
	public void setB4(float b4) {
		set(13, b4);
	}
	
	//------------------------------------
	
	public float getC1() {
		return get(2);
	}
	public void setC1(float c1) {
		set(2, c1);
	}
	
	public float getC2() {
		return get(6);
	}
	public void setC2(float c2) {
		set(6, c2);
	}
	
	public float getC3() {
		return get(10);
	}
	public void setC3(float c3) {
		set(10, c3);
	}
	
	public float getC4() {
		return get(14);
	}
	public void setC4(float c4) {
		set(14, c4);
	}
	
	//----------------------------------
	
	public float getD1() {
		return get(3);
	}
	public void setD1(float d1) {
		set(3, d1);
	}
	
	public float getD2() {
		return get(7);
	}
	public void setD2(float d2) {
		set(7, d2);
	}
	
	public float getD3() {
		return get(11);
	}
	public void setD3(float d3) {
		set(11, d3);
	}
	
	public float getD4() {
		return get(15);
	}
	public void setD4(float d4) {
		set(15, d4);
	}
	
	
	public float get(int x, int y) {
		return get(x * N + y);
	}
	
	
	public void set(int x, int y, float value) {
		set(x * N + y, value);
	}
	
	
	//-----------------------------------
	private Vector4f getCol(int c) {
		return new Vector4f(get(0+c*N), get(1+c*N), get(2+c*N), get(3+c*N));
	}


	@Override
	public boolean equals(Object obj) {
		if(getClass() != obj.getClass()) {
			return false;
		}
		
		Matrix44f mat = (Matrix44f)obj;
		
		for (int i = 0; i < 16; i++) {
			if(this.get(i) != mat.get(i)) {
				return false;
			}
		}
		
		return true;
		
	}
	
}
