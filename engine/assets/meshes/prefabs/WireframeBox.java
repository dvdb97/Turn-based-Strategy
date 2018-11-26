package assets.meshes.prefabs;

import assets.meshes.Mesh;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINES;

public class WireframeBox extends Mesh {	
	
	public WireframeBox() {
		super(GL_LINES);
		
		float[] positions = {
			-1f, 1f, 1f, 1f, 1f, 1f,
			1f, -1f, 1f, -1f, -1f, 1f,
			-1f, 1f, -1f, 1f, 1f, -1f,
			1f, -1f, -1f, -1f, -1f, -1f
		};
		
		int[] indices = {
			//Front face:
			0, 1, 1, 2, 2, 3, 3, 0,
			
			//Back Face:
			4, 5, 5, 6, 6, 7, 7, 4,
			
			//Connections between faces:
			0, 4, 1, 5, 2, 6, 3, 7
		};
		
		this.setPositionData(CustomBufferUtils.createFloatBuffer(positions));
		this.setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
	}

}
