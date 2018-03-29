package graphics.matrices;

import interaction.Window;
import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion44f;
import rendering.matrices.projectionMatrices.ProjectionMatrix;

public class Matrices {
	
	private static ProjectionMatrix perspProjectionMatrix;
	
	private static Matrix44f invertedPerspProjectionMatrix;
	
	private static ProjectionMatrix orthProjectionMatrix;
	
	private static Matrix44f invertedOrthProjectionMatrix;
	
	private static float windowProportions;
	

	public static void initProjectionMatrix(Window window) {
		
		perspProjectionMatrix = ProjectionMatrix.generatePerspectiveProjectionMatrix(window.getProportions());
		
		invertedPerspProjectionMatrix = MatrixInversion44f.generateMultiplicativeInverse(perspProjectionMatrix);
		
		orthProjectionMatrix = ProjectionMatrix.generateOrthographicProjectionMatrix(window.getProportions(), 10f);
		
		invertedOrthProjectionMatrix = MatrixInversion44f.generateMultiplicativeInverse(orthProjectionMatrix);
		
		windowProportions = window.getProportions();
		
	}
	

	public static Matrix44f getPerspectiveProjectionMatrix() {
		return perspProjectionMatrix;		
	}
	
	
	public static Matrix44f getInvertedPerspectiveProjectionMatrix() {
		return invertedPerspProjectionMatrix;		
	}
	
	
	public static Matrix44f getOrthographicProjectionMatrix() {
		return orthProjectionMatrix;
	}
	
	
	public static Matrix44f getInvertedOrthographicProjectionMatrix() {
		return invertedOrthProjectionMatrix;
	}
	
	
	public static float getWindowProportions() {
		return windowProportions;
	}

			
}
