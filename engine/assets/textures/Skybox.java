package assets.textures;

import assets.buffers.Buffer;
import assets.cameras.Camera;
import assets.meshes.Mesh;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.geometry.Vertex;
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
import assets.shaders.standardShaders.skybox.SkyboxShader;
import math.matrices.Matrix44f;

import static org.lwjgl.opengl.GL11.*;

public class Skybox extends CubeMap {
	
	private static Mesh mesh;
	
	private static SkyboxShader shader;

	public Skybox(String[] paths) {
		super(paths);
		
		if (shader == null)
			shader = SkyboxShader.createSkyboxShader();
		
		if (mesh == null)
			loadSkyboxMesh();
	}
	
	
	private static void loadSkyboxMesh() {
		Vertex[] vertices = {
			new Vertex(-10.0f,  10.0f, -10.0f),
			new Vertex(-10.0f, -10.0f, -10.0f),
			new Vertex( 10.0f, -10.0f, -10.0f),
			new Vertex( 10.0f,  10.0f, -10.0f),
			
			new Vertex(-10.0f,  10.0f,  10.0f),
			new Vertex(-10.0f, -10.0f,  10.0f),
			new Vertex( 10.0f, -10.0f,  10.0f),
			new Vertex( 10.0f,  10.0f,  10.0f)
		};
		
		int[] indices = {
			0, 1, 2, 2, 3, 0,
			0, 4, 1, 1, 5, 4,
			4, 5, 6, 6, 7, 4,
			7, 3, 2, 2, 6, 7,
			5, 6, 2, 2, 1, 5,
			0, 3, 7, 7, 4, 0
		};
		
		mesh = new Mesh(vertices, indices);
		
		mesh.storeOnGPU(BufferLayout.INTERLEAVED, vertices[0].getDataLayout(), Buffer.DYNAMIC_STORAGE);
	}
	
	
	public void render(Camera camera) {
		glDepthMask(false);
		shader.bind();
		this.bind();
		
		shader.setUniformMatrices(camera.getViewMatrix(), camera.getProjectionMatrix());
		
		mesh.render();
		
		this.unbind();
		shader.unbind();
		glDepthMask(true);
	}
}
