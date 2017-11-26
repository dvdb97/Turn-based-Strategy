package graphics;

import lwlal.Matrix44f;

public class ProjectionMatrix {
	
	private static Matrix44f projectionMatrix;
	
	
	public static void init(/* TODO: Values that are needed for the projection matrix */) {
		
		projectionMatrix = new Matrix44f(1f, 0f, 0f, 0f, 
										 0f, 1f, 0f, 0f, 
										 0f, 0f, 1f, 0f, 
										 0f, 0f, 0f, 1f);
		
	}
	
	
	public static Matrix44f getProjectionMatrix() {
		
		return projectionMatrix;	
		
	}

}
