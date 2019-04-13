package assets.material;

import assets.meshes.geometry.Color;
import math.vectors.Vector3f;

public class StandardMaterial extends Material {

	private static final Vector3f vec = new Vector3f(1.0f, 1.0f, 1.0f);
	
	public StandardMaterial() {
		super(new Color(255, 20, 147, 255), vec, vec, vec, vec, 1.0f);
		this.castShadows = true;
	}

}
