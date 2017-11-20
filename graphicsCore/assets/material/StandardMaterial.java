package assets.material;

import lwlal.Vector3f;

public class StandardMaterial extends Material {

	private static final Vector3f vec = new Vector3f(1.0f, 1.0f, 1.0f);
	
	public StandardMaterial() {
		super(vec, vec, vec, vec, 1.0f);
	}

}
