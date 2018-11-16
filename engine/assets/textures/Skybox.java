package assets.textures;

import assets.cameras.Camera;
import assets.meshes.Mesh;
import assets.shaders.standardShaders.skybox.SkyboxShader;
import utils.CustomBufferUtils;

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
		float[] vertices = {
			-10.0f,  10.0f, -10.0f,
			-10.0f, -10.0f, -10.0f,
			 10.0f, -10.0f, -10.0f,
			 10.0f,  10.0f, -10.0f,
			
			-10.0f,  10.0f,  10.0f,
			-10.0f, -10.0f,  10.0f,
			 10.0f, -10.0f,  10.0f,
			 10.0f,  10.0f,  10.0f
		};
		
		int[] indices = {
			0, 1, 2, 2, 3, 0,
			0, 4, 1, 1, 5, 4,
			4, 5, 6, 6, 7, 4,
			7, 3, 2, 2, 6, 7,
			5, 6, 2, 2, 1, 5,
			0, 3, 7, 7, 4, 0
		};
		
		mesh = new Mesh();
		
		mesh.setPositionData(CustomBufferUtils.createFloatBuffer(vertices), 3);
		mesh.setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
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
