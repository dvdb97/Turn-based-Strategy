package assets.material;

import assets.meshes.geometry.Color;
import math.vectors.Vector3f;

public class Material {
	
	public Color color;
	
	public Vector3f emission;
	
	public Vector3f ambient;
	
	public Vector3f diffuse;
	
	public Vector3f specular;
	
	public float shininess;
	
	
	public Material(Color color, Vector3f emission, Vector3f ambient, Vector3f diffuse, Vector3f specular, float shininess) {
		super();
		
		this.color = color;
		this.emission = emission;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.shininess = shininess;
	}
	
	
	public Material(Color color) {
		super();
		
		this.color = color;
		this.emission = new Vector3f(0f, 0f, 0f);
		this.ambient = new Vector3f(0f, 0f, 0f);
		this.diffuse = new Vector3f(0f, 0f, 0f);
		this.specular = new Vector3f(0f, 0f, 0f);
		this.shininess = 0f;
	}
	
}
