package assets.meshes.specialized;

import assets.material.Material;
import assets.meshes.Mesh2D;
import assets.textures.Texture;
import utils.CustomBufferUtils;

public class Quad extends Mesh2D {
	
	public Quad() {
		
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
		this.setMaterial(Material.standard);
	}
	
	
	public Quad(Texture texture) {
		this();
		
		this.setTexture(texture);
	}
	
	
	public Quad(Material material) {
		this();
		
		this.setMaterial(material);
	}

}
