package assets.meshes.specialized;

import assets.meshes.Mesh;
import assets.meshes.Mesh2D;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINES;

public class Axis extends Mesh2D {
	
	public Axis() {
		super(GL_LINES);
		
		float[] positions = {
			0f, 0f, 0f, 1f, 0f, 0f,
			0f, 0f, 0f, 0f, 1f, 0f,
			0f, 0f, 0f, 0f, 0f, 1f
		};
		
		float[] color = {
			1f, 0f, 0f, 1f, 0f, 0f,
			0f, 1f, 0f, 0f, 1f, 0f,
			0f, 0f, 1f, 0f, 0f, 1f
		};
		
		int[] indices = {
			0, 1, 2, 3, 4, 5	
		};
		
		this.setPositionData(CustomBufferUtils.createFloatBuffer(positions));
		this.setColorData(CustomBufferUtils.createFloatBuffer(color));
		this.setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
		this.useAttributeColor();
	}

}
