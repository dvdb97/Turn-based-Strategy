package assets.material;

import assets.meshes.geometry.Color;
import math.vectors.Vector3f;

public class StandardMaterial extends Material {

	private static final Vector3f vec = new Vector3f(1.0f, 1.0f, 1.0f);
	
	public StandardMaterial() {
		super(new Color(1f, 0f, 0f, 1f), vec, vec, vec, vec, 1.0f);
	}

}
