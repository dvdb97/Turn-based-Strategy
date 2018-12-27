package modelTest;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh;
import assets.scene.Scene;
import assets.shaders.ShaderProgram;

public class TestMesh extends Mesh {

	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		super.onDrawStart(camera, light);
		this.getShader().setUniformSubroutine("colorFunc", "materialColor", ShaderProgram.FRAGMENT_SHADER);
		this.getShader().setUniformSubroutine("finalColorFunc", "toonShading", ShaderProgram.FRAGMENT_SHADER);
		this.getShader().setUniformSubroutines();
	}

	@Override
	protected void onDrawEnd(Camera camera, DirectionalLight light) {
		// TODO Auto-generated method stub
		super.onDrawEnd(camera, light);
	}

	@Override
	protected void onDrawStart(Scene scene) {
		// TODO Auto-generated method stub
		super.onDrawStart(scene);
	}

	@Override
	protected void onDrawEnd(Scene scene) {
		// TODO Auto-generated method stub
		super.onDrawEnd(scene);
	}
	
	

}
