package assets.meshes.prefabs;

import assets.meshes.Mesh;
import utils.CustomBufferUtils;

public class Plane extends Mesh {
	
	public Plane() {
		float[] pos = {
			-1f, 1f, 0f, 1f, 1f, 0f,
			-1f, -1f, 0f, 1f, -1f, 0f
		};
		
		float[] texPos = {
			0f, 1f, 1f, 1f,
			0f, 0f, 1f, 0f
		};
		
		int[] indices = {
			0, 1, 3, 3, 2, 0
		};
		
		this.setPositionData(CustomBufferUtils.createFloatBuffer(pos), 3);
		this.setTexCoordData(CustomBufferUtils.createFloatBuffer(texPos), 2);
		this.setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
	}

}
