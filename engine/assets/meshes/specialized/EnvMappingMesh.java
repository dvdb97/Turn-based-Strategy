package assets.meshes.specialized;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.scene.Scene;
import assets.shaders.standardShaders.skybox.EnvMappingShader;

public class EnvMappingMesh extends Mesh {
	
	private EnvMappingShader envMappingShader;
	
	public EnvMappingMesh() {
		this.envMappingShader = EnvMappingShader.createEnvMappingShader();
	}
	

	/**
	 * Use the default shader if the shader has no access to a
	 * skybox.
	 */
	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		super.onDrawStart(camera, light);
	}

	/**
	 * Use the default shader if the shader has no access to a
	 * skybox.
	 */
	@Override
	protected void onDrawEnd(Camera camera, DirectionalLight light) {
		super.onDrawEnd(camera, light);
	}

	/**
	 * Use environment mapping if a skybox is available.
	 */
	@Override
	protected void onDrawStart(Scene scene) {		
		this.envMappingShader.bind();
		this.envMappingShader.setCamera(scene.getCamera());
		this.envMappingShader.setModelMatrix(getTransformable().getTransformationMatrix());
		this.envMappingShader.setMaterial(getMaterial());
		scene.getSkybox().bind();
	}

	/**
	 * Use environment mapping if a skybox is available.
	 */
	@Override
	protected void onDrawEnd(Scene scene) {
		scene.getSkybox().unbind();
		this.envMappingShader.unbind();
	}

}
