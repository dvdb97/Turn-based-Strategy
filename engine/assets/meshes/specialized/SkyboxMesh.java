package assets.meshes.specialized;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.material.Material;
import assets.meshes.Mesh;
import assets.scene.Scene;
import assets.shaders.standardShaders.skybox.SkyboxShader;
import assets.textures.Skybox;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.glDepthMask;

public class SkyboxMesh extends Mesh {
	
	private SkyboxShader shader;
	
	public SkyboxMesh(Skybox skybox) {
		super(Material.standard);
		
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
		
		this.setPositionData(CustomBufferUtils.createFloatBuffer(vertices));
		this.setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
		this.setTexture(skybox);
		shader = SkyboxShader.createSkyboxShader();
	}

	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		glDepthMask(false);
		shader.bind();
		shader.setViewMatrix(camera.getViewMatrix());
		shader.setProjectionMatrix(camera.getProjectionMatrix());
		shader.bindTexture("skybox", getTexture());
	}

	@Override
	protected void onDrawEnd(Camera camera, DirectionalLight light) {
		shader.unbind();
		glDepthMask(true);
	}

	@Override
	protected void onDrawStart(Scene scene) {
		this.onDrawStart(scene.getCamera(), scene.getLightSource());
	}

	@Override
	protected void onDrawEnd(Scene scene) {
		this.onDrawEnd(scene.getCamera(), scene.getLightSource());
	}	

}
