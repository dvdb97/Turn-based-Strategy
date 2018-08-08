package assets.meshes;

import assets.buffers.Buffer;
import assets.light.ShadowMap;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.MeshConst.BufferLayout;
import assets.textures.Texture;
import assets.textures.Texture2D;
import rendering.shaders.ShaderProgram;

public class Model {
	
	private Transformable transform;
	
	private Material material;
	
	private Texture texture;
	
	private ShaderProgram shader;
	
	public Mesh mesh;
	
	
	public Model(ShaderProgram shader, Material material, Mesh mesh, BufferLayout layout, int flag) {
		
		this.transform = new Transformable();
		
		this.shader = shader;
		
		this.material = material;
		
		this.mesh = mesh;
		
		mesh.storeOnGPU(layout, shader.getLayout(), flag);
		
	}
	
	
	public Model(ShaderProgram shader, Mesh mesh) {
		this(shader, new StandardMaterial(), mesh, BufferLayout.INTERLEAVED, Buffer.DYNAMIC_STORAGE);
	}
	
	
	/**
	 * 
	 * Creates a model with the given specifications.
	 * 
	 * Stores the vertex data on the gpu but adapts it
	 * to the shaders requirements.
	 * 
	 * @param shader The shader that will be used to render this model
	 * @param mesh The mesh of the model
	 * @param layout The layout in which the model will be stored on the gpu
	 */
	public Model(ShaderProgram shader, Mesh mesh, BufferLayout layout) {
		this(shader, new StandardMaterial(), mesh, layout, Buffer.DYNAMIC_STORAGE);
	}
	
	
	/**
	 * 
	 * Creates a model with the given specifications.
	 * 
	 * Stores the vertex data on the gpu but adapts it
	 * to the shaders requirements.
	 * 
	 * @param shader The shader that will be used to render this model
	 * @param mesh The mesh of the model
	 * @param layout The layout in which the model will be stored on the gpu
	 */
	public Model(ShaderProgram shader, Mesh mesh, Texture2D texture, BufferLayout layout) {
		this(shader, new StandardMaterial(), mesh, layout, Buffer.DYNAMIC_STORAGE);
		
		this.texture = texture;
	}
	
	
	public void render() {
		shader.use();
		
		shader.setUniformMatrix4fv("mvpMatrix", transform.getTransformationMatrix());
		
		if (texture != null)
			texture.bind();
		
		mesh.render();
		
		if (texture != null)
			texture.unbind();
		
		shader.disable();
	}
	
	
	public void render(ShadowMap shadowMap) {
		shader.use();
		//shader.setMaterial(material)
		//shader.setMatrices()
		
		shadowMap.getShadowMap().bind();
		
		mesh.render();
		
		shadowMap.getShadowMap().unbind();
		
		shader.disable();
	}
	
	
	public Transformable getTransformable() {
		return transform;
	}
	
	
	public void delete() {
		mesh.delete();
	}

}
