package lwlal;

public class Matrix33f extends Matrixf {
	
	private final static int N = 3;
	private final static int LENGTH = N*N;
	
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
	public Matrix33f(float a1, float a2, float a3, float b1, float b2, float b3, float c1, float c2, float c3) {
		
		super(LENGTH);
		
		set( 0, a1); set( 3, a2); set( 6, a3);
		set( 1, b1); set( 4, b2); set( 7, b3);
		set( 2, c1); set( 5, c2); set( 8, c3);
		
	}
	
	/**
	 * 
	 * @param col1 first column
	 * @param col2 second column
	 * @param col3 thrid column
	 */
	public Matrix33f(Vector3f col1, Vector3f col2, Vector3f col3) {
		
		super(LENGTH);
		
		set( 0, col1.getA()); set( 3, col2.getA()); set( 6, col3.getA());
		set( 1, col1.getB()); set( 4, col2.getB()); set( 7, col3.getB());
		set( 2, col1.getC()); set( 5, col2.getC()); set( 8, col3.getC());
		
	}
	
	/**
	 * creates an 3x3 identity matrix
	 */
	public Matrix33f() {
		
		super(LENGTH);
		
		setIdentity();
	}
	
	
	//------------------------- math methods ------------------------
	
	/**
	 * Matrix addition
	 * 
	 * @param m summand
	 * @return returns itself after a component wise addition
	 */
	public Matrix33f plus(Matrix33f m) {
		super.plus(m);
		return this;
	}
	
	/**
	 * Matrix subtraction
	 * 
	 * @param m subtrahend
	 * @return returns itself after a component subtraction
	 */
	public Matrix33f minus(Matrix33f m) {
		super.minus(m);
		return this;
	}
	
	/**
	 * Scalar Multiplication
	 * 
	 * @param r the scalar, every component is multiplicated with
	 * @return returns itself after the multiplication
	 */
	public Matrix33f times(float r) {
		super.times(r);
		return this;
	}
	
	/**
	 * Vector multiplication
	 * 
	 * @param v
	 * @return 
	 */
	public Vector3f times(Vector3f v) {
		return new Vector3f(	v.scalar(new Vector3f(getA1(), getA2(), getA3())),
								v.scalar(new Vector3f(getB1(), getB2(), getB3())),
								v.scalar(new Vector3f(getC1(), getC2(), getC3()))
							);
	}
	
	/**
	 * Matrix multiplication
	 * 
	 * @param m
	 * @return
	 */
	public Matrix33f times(Matrix33f m) {
		return new Matrix33f(	this.times(m.getCol(0)),	//1st col
								this.times(m.getCol(1)),	//2nd col
								this.times(m.getCol(2))		//3rd col
							);
	}
	
	/**
	 * Dot Product (Inner Product)
	 * 
	 * @param m factor
	 * @return the dot product
	 */
	public float scalar(Matrix33f m) {
		return super.scalar(m);
		
	}
	
	/**
	 * turns the matrix into a identity matrix
	 * (matrix with just zeros, but ones on the diagonal)
	 */
	public void setIdentity() {
		
		set( 0, 1); set( 3, 0); set( 6, 0);
		set( 1, 0); set( 4, 1); set( 7, 0);
		set( 2, 0); set( 5, 0); set( 8, 1);
		
	}
	
	/**
	 * @return returns a new matrix, which is this matrix transposed
	 */
	public Matrix33f transposed() {
		return new Matrix33f(getA1(), getB1(), getC1(), getA2(), getB2(), getC2(), getA3(), getB3(), getC3());
	}
	
	
	/**
	 * Negation
	 * 
	 * @return itself, multiplicated with -1
	 */
	public Matrix33f negated() {
		super.voidNegated();
		return this;
	}
	
	//---------------------- other methods -------------------
	
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
	
	/**
	 * @return returns a deep copy of this matrix
	 */
	public Matrix33f copyOf() {
		
		return new Matrix33f(getA1(), getA2(), getA3(), getB1(), getB2(), getB3(), getC1(), getC2(), getC3());
		
	}
	
	//------------------------------- Get & Set ---------------------
	
	public float getA1() {
		return get(0);
	}
	public void setA1(float a1) {
		set(0, a1);
	}
	
	public float getA2() {
		return get(3);
	}
	public void setA2(float a2) {
		set(3, a2);
	}
	
	public float getA3() {
		return get(6);
	}
	public void setA3(float a3) {
		set(6, a3);
	}
	
	//-----------------------------------
	
	public float getB1() {
		return get(1);
	}
	public void setB1(float b1) {
		set(1, b1);
	}
	
	public float getB2() {
		return get(4);
	}
	public void setB2(float b2) {
		set(4, b2);
	}
	
	public float getB3() {
		return get(7);
	}
	public void setB3(float b3) {
		set(7, b3);
	}
	
	//-----------------------------------
	
	public float getC1() {
		return get(2);
	}
	public void setC1(float c1) {
		set(2, c1);
	}
	
	public float getC2() {
		return get(5);
	}
	public void setC2(float c2) {
		set(5, c2);
	}
	
	public float getC3() {
		return get(8);
	}
	public void setC3(float c3) {
		set(8, c3);
	}
	
	//-----------------------------------
	private Vector3f getCol(int c) {
		return new Vector3f(get(0+c*N), get(1+c*N), get(2+c*N));
	}
	
	
}
