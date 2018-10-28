package assets.meshes;

import assets.buffers.Buffer;
import assets.cameras.Camera;
import assets.light.DepthBuffer;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.MeshConst.BufferLayout;
import assets.shaders.ShaderProgram;
import assets.textures.Texture;
import assets.textures.Texture2D;
import math.matrices.Matrix44f;

public class Model implements IRenderable {
	
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
	
	
	/**
	 * 
	 * Creates a model with the given specifications.
	 * 
	 * Stores the vertex data on the gpu but adapts it
	 * to the shaders requirements.
	 * 
	 * @param shader The shader that will be used to render this model.
	 * @param mesh The mesh of the model.
	 * @param material The material of this mesh.
	 * @param texture The texture of this Model.
	 * @param layout The layout in which the model will be stored on the gpu
	 */
	public Model(ShaderProgram shader, Mesh mesh, Material material, Texture2D texture, BufferLayout layout) {
		this(shader, material, mesh, layout, Buffer.DYNAMIC_STORAGE);
	}
	
	
	/**
	 * Renders this Model on the screen.
	 */
	public void render() {
		
		//Pass the mvp matrix to the shader.
		shader.setModelMatrix(transform.getTransformationMatrix());
		
		//Pass the material to the shader.
		shader.setMaterial(material);
		
		if (texture != null)
			texture.bind();
		
		mesh.render();
		
		if (texture != null)
			texture.unbind();
	}
	
	
	public void render(Camera camera) {
		this.render();
	}
	
	
	public void passToShadowMap() {
		mesh.render();
	}
	
	
	/**
	 * 
	 * Renders this Model on the screen. This method also offers the option
	 * to display the mesh's bounding box.
	 * 
	 * @param view The viewMatrix of the camera.
	 * @param projection The projectionMatrix.
	 * @param showBoundingBox If set to true the bounding box will displayed.
	 */
	public void render(Matrix44f view, Matrix44f projection, boolean showBoundingBox) {
		render();
		
		if (showBoundingBox)
			mesh.renderBoundingBox(transform, view, projection);
	}
	
	
	/**
	 * 
	 * @return Returns the model's transformable.
	 */
	public Transformable getTransformable() {
		return transform;
	}
	
	
	/**
	 * 
	 * @return Returns the model's material.
	 */
	public Material getMaterial() {
		return material;
	}
	
	
	public void delete() {
		mesh.delete();
	}

}
