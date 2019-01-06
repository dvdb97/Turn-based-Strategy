package gui_core;

import math.matrices.Matrix33f;
import math.matrices.Matrix44f;

public class GUIMatrixManager {
	
	/**
	 * 
	 * @param xPos The x position of the element
	 * @param yPos The y position of the element
	 * @param width The width of the element
	 * @param height The height of the element
	 * @return Returns a transformation matrix based on the parameters
	 */	
	public static Matrix44f generateTransformationMatrix44(float xPos, float yPos, float width, float height) {
		
		Matrix44f scalingMatrix = new Matrix44f(width, 0f, 0f, 0f,
												0f, height, 0f, 0f,
												0f, 0f, 1.0f, 0f,
												0f, 0f, 0f, 1f);

		Matrix44f translationMatrix = new Matrix44f(1f, 0f, 0f, xPos,
													0f, 1f, 0f, yPos,
													0f, 0f, 1f, 0f,
													0f, 0f, 0f, 1f);


		return translationMatrix.times(scalingMatrix); 
		
	}
	
	/**
	 * 
	 * @param xPos The x position of the element
	 * @param yPos The y position of the element
	 * @param width The width of the element
	 * @param height The height of the element
	 * @return Returns a transformation matrix based on the parameters
	 */	
	public static Matrix33f generateTransformationMatrix33(float xPos, float yPos, float width, float height) {
		
		Matrix33f scalingMatrx = new Matrix33f(width, 0, 0,
											   0, height, 0,
											   0, 0, 1f);
		
		Matrix33f translationMatrix = new Matrix33f(1, 0, xPos,
													0, 1, yPos,
													0, 0, 1);
		
		return translationMatrix.times(scalingMatrx);
		
	}

}
