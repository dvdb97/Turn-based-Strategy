package assets.meshes.specialized;

import assets.meshes.Mesh3D;
import assets.scene.Scene;
import assets.shaders.standardShaders.skybox.EnvMappingShader;

public class EnvMappingMesh extends Mesh3D {
	
	private EnvMappingShader shader;
	
	public EnvMappingMesh() {
		shader = EnvMappingShader.createEnvMappingShader();
	}

	/**
	 * Use environment mapping if a skybox is available.
	 */
	@Override
	protected void onDrawStart(Scene scene) {		
		shader.bind();
		shader.setCamera(scene.getCamera());
		shader.setModelMatrix(getTransformable().getTransformationMatrix());
		shader.setMaterial(getMaterial());
		scene.getSkybox().bind();
	}

	/**
	 * Use environment mapping if a skybox is available.
	 */
	@Override
	protected void onDrawEnd(Scene scene) {
		scene.getSkybox().unbind();
		shader.unbind();
	}

}
