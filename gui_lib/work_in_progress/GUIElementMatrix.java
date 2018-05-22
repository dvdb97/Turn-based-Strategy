package work_in_progress;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;

public class GUIElementMatrix extends Matrix33f {
	
	//************************** constructor ************************
	
	public GUIElementMatrix(float xShift, float yShift, float xStretch, float yStretch) {
		
		super(	xStretch, 0       , xShift,
				0       , yStretch, yShift,
				0       , 0       , 1     );

		if(xStretch == 0 || yStretch == 0) {
			System.out.println("xStretch:  " + xStretch + " , xStretch:  " + yStretch);
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * creates an identity matrix
	 */
	public GUIElementMatrix() {
		super(  1, 0, 0,
				0, 1, 0,
				0, 0, 1);
	}
	
	//****************************************************************
	
	public GUIElementMatrix times(GUIElementMatrix m) {
		return new GUIElementMatrix(this.getXStretch()*m.getXShift() + this.getXShift(),
									this.getYStretch()*m.getYShift() + this.getYShift(),
									this.getXStretch()*m.getXStretch(),
									this.getYStretch()*m.getYStretch());
	}
	
	
	public GUIElementMatrix getInverse() {
		return new GUIElementMatrix(-getXShift()/getXStretch(),  -getYShift()/getYStretch(), 1/getXStretch(), 1/getYStretch());
	}
	
	//****************************************************************
	
	public Matrix44f toMatrix44f() {
		
		return new Matrix44f(	getXStretch(), 0            , 0     , getXShift(),
								0            , getYStretch(), 0     , getYShift(),
								0            , 0            , 1     , 0          ,
								0            , 0            , 0     , 1          );
		
	}
	
	//************************** get & set ***************************
	
	/**
	 * @return the xShift (a3)
	 */
	public float getXShift() {
		return getA3();
	}

	/**
	 * @param xShift the xShift to set
	 */
	public void setXShift(float xShift) {
		setA3(xShift);
	}

	/**
	 * @return the yShift (b3)
	 */
	public float getYShift() {
		return getB3();
	}

	/**
	 * @param yShift the yShift to set
	 */
	public void setYShift(float yShift) {
		setB3(yShift);
	}

	/**
	 * @return the xStretch (a1)
	 */
	public float getXStretch() {
		return getA1();
	}

	/**
	 * @param xStretch the xStretch to set
	 */
	public void setXStretch(float xStretch) {
		setA1(xStretch);
	}

	/**
	 * @return the yStretch (b2)
	 */
	public float getYStretch() {
		return getB2();
	}

	/**
	 * @param yStretch the yStretch to set
	 */
	public void setYStretch(float yStretch) {
		setB2(yStretch);
	}
	
}
