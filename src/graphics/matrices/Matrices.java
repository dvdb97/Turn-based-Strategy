package graphics.matrices;

import interaction.Window;
import rendering.matrices.projectionMatrices.ProjectionMatrix;

public class Matrices {
	
	private static ProjectionMatrix projectionMatrix;

	public static void initProjectionMatrix(Window window) {
		projectionMatrix = ProjectionMatrix.generatePerspectiveProjectionMatrix(window.getProportions());
	}
	
	
	public static ProjectionMatrix getProjectionMatrix() {
		return projectionMatrix;		
	}
			
			
}
