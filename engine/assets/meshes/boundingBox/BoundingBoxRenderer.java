package assets.meshes.boundingBox;

import assets.meshes.Mesh;
import assets.meshes.standardMeshes.Cube;
import math.matrices.Matrix44f;

public class BoundingBoxRenderer {
	
	//TODO: Shader for rendering the bounding box
	
	private static Mesh boundingBoxMesh;
	
	private static boolean init = false;
	
	public static void init() {
		if (init) {
			return;
		}		
	}
	
	
	public static void render(Matrix44f modelMatrix, Matrix44f viewMatrix, Matrix44f projectionMatrix) {
		if (!init) {
			init();
		}
		
		//Render the bounding box
		
	}

}
