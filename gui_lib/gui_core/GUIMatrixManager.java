package gui_core;

import math.matrices.Matrix33f;

public class GUIMatrixManager {
	
	/**
	 * 
	 * @param x The x position of the element
	 * @param y The y position of the element
	 * @param width The width of the element
	 * @param height The height of the element
	 * @return Returns a transformation matrix based on the parameters
	 */
	public static Matrix33f generateRenderingMatrix(float x, float y, float width, float height) {
		
		Matrix33f scalingMatrix = new Matrix33f(width, 0f, 0f,
												0f, height, 0f,
												0f, 0f, 1f);
		
		Matrix33f translationMatrix = new Matrix33f(1f, 0f, x,
													0f, 1f, y,
													0f, 0f, 1f);
		
		
		
		return translationMatrix.times(scalingMatrix);
		
	}

}
