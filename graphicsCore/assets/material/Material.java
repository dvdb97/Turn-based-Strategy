package assets.material;

import lwlal.Vector3f;

public class Material {
	
	public Material(Vector3f emission, Vector3f ambient, Vector3f diffuse, Vector3f specular, float shininess) {
		super();
		
		this.emission = emission;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.shininess = shininess;
	
	}

	public Vector3f emission;
	
	public Vector3f ambient;
	
	public Vector3f diffuse;
	
	public Vector3f specular;
	
	public float shininess;

}
