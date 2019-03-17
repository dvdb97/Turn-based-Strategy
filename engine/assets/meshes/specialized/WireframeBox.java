package assets.meshes.specialized;

import assets.material.Material;
import assets.meshes.geometry.Color;
import utils.CustomBufferUtils;

public class WireframeBox extends WireframeMesh {	
	
	
	public WireframeBox() {
		
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
		this.setMaterial(Material.standard);
	}
	
	
	public WireframeBox(Material material) {
		this();
		
		this.setMaterial(material);
	}
	
	
	public void setColor(Color color) {
		this.getMaterial().color = color;
	}

}
