package graphics.matrices;

import interaction.Window;
import graphics.matrices.ProjectionMatrix;

public class Matrices {
	
	private static ProjectionMatrix projectionMatrix;

	public static void initProjectionMatrix(Window window) {
		projectionMatrix = new ProjectionMatrix(-1, 1, -1, 1, 1, -10.0f, window.getProportions());
	}
	
	
	public static ProjectionMatrix getProjectionMatrix() {
		return projectionMatrix;		
	}
			
			
}
