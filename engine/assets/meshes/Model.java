package assets.meshes;

import assets.buffers.Buffer;
import assets.light.ShadowMap;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.MeshConst.BufferLayout;
import rendering.shaders.ShaderProgram;

public class Model {
	
	public final Transformable transform;
	
	private Material material;
	
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
		this(shader, new StandardMaterial(), mesh, BufferLayout.BLOCKWISE, Buffer.DYNAMIC_STORAGE);
	}
	
	
	public void render() {
		shader.use();
		//shader.setMaterial(material)
		//shader.setMatrices()
		
		
		mesh.render();
		
		
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

}
