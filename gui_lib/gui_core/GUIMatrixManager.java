package gui_core;

import math.matrices.Matrix44f;

public class GUIMatrixManager {
	
	/**
	 * 
	 * @param x The x position of the element
	 * @param y The y position of the element
	 * @param width The width of the element
	 * @param height The height of the element
	 * @return Returns a transformation matrix based on the parameters
	 */
	public static Matrix44f generateRenderingMatrix(float x, float y, float width, float height) {
		
		Matrix44f scalingMatrix = new Matrix44f(width, 0f, 0f, 0f,
												0f, height, 0f, 0f,
												0f, 0f, 1.0f, 0f,
												0f, 0f, 0f, 1f);
		
		Matrix44f translationMatrix = new Matrix44f(1f, 0f, 0f, x,
													0f, 1f, 0f, y,
													0f, 0f, 1f, -0.02f,
													0f, 0f, 0f, 1f);
		
		
		
		return translationMatrix.times(scalingMatrix);
		
	}

}
