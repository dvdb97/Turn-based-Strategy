package assets.meshes;

import assets.material.Material;
import rendering.shaders.ShaderProgram;

public class Model {
	
	//TODO: Bounding box
	
	public final Transformable transform;
	
	public final ShaderProgram shader;
	
	public Material material;
	
	private Mesh mesh;
	
	
	public Model() {
		
	}
	

}
